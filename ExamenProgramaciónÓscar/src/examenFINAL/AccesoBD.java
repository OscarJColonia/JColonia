package examenFINAL;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

/**
 * Utilidades de acceso a una base de datos SQLite para gestión de
 * {@link ContactoMóvil Contactos telefónicos}.
 * 
 * @versión 2021.6.3
 * @author <a href="dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class AccesoBD implements AutoCloseable {
	/**
	 * Sentencia SQL para crear la tabla «Contactos» –vacía– si no existe.
	 */
	private static final String SQL_CREAR_TABLA = "CREATE TABLE IF NOT EXISTS Contactos (nombre TEXT NOT NULL, prefijo_internacional TEXT, prefijo_nacional TEXT, número TEXT NOT NULL, extensión TEXT)";

	/**
	 * Prototipo de sentenciaSQL preparada para insertar contactos.
	 */
	private static final String SQL_INSERTAR_CONTACTO = "INSERT INTO Contactos VALUES (?, ?, ?, ?, ?)";

	/**
	 * Sentencia SQL para obtener un volcado completo de los contactos.
	 */
	private static final String SQL_LISTADO_COMPLETO = "SELECT * FROM Contactos";

	/**
	 * Sentencia SQL para vaciar los contactos.
	 */
	private static final String SQL_VACIAR_TABLA = "DELETE FROM Contactos";

	/**
	 * Sentencia SQL para compactar espacio en el archivo de la base de datos.
	 */
	private static final String SQL_COMPACTAR_ESPACIO = "VACUUM";
	/**
	 * Nombre predeterminado del archivo de configuración del acceso a la base de
	 * datos.
	 */
	public static final String ARCHIVO_CONFIG_PREDETERMINADO = "config.xml";

	/**
	 * Nombre predeterminado del archivo de la base de datos.
	 */
	public static final String ARCHIVO_BD_PREDETERMINADO = "agenda.db";

	/**
	 * Configuración del acceso a la base de datos.
	 */
	private Properties configuración;

	/**
	 * Conexión a la base de datos.
	 */
	private Connection conexión;

	/**
	 * Sentencia general SQL.
	 */
	private Statement sentenciaGeneralSQL;

	/**
	 * Sentencia preparada SQL, para inserciones en la base de datos.
	 * 
	 * @see #SQL_INSERTAR_CONTACTO
	 */
	private PreparedStatement preInserciónSQL;

	/**
	 * Carga la configuración desde el archivo de configuración predeterminado.
	 * 
	 * @see #ARCHIVO_CONFIG_PREDETERMINADO
	 */
	public AccesoBD() {
		this(ARCHIVO_CONFIG_PREDETERMINADO, ARCHIVO_BD_PREDETERMINADO);
	}

	/**
	 * Carga la configuración desde un archivo de configuración. En caso de error
	 * genera uno nuevo.
	 * 
	 * @param archivoConfiguración la ruta y nombre del archivo
	 * @param archivoBD
	 * @param archivoBD
	 */
	public AccesoBD(String archivoConfiguración, String archivoBD) {
		try {
			configuración = cargarConfiguración(archivoConfiguración);
		} catch (AccesoBDException e) {
			if (archivoBD == null || archivoBD.isEmpty()) {
				archivoBD = ARCHIVO_BD_PREDETERMINADO;
			}
			System.err.printf("Error cargando configuración de «%s»: %s%n", archivoConfiguración, e.getMessage());
			configuración = crearConfiguración(archivoConfiguración, archivoBD);
		}
	}

	/**
	 * Lee un archivo de configuración.
	 * 
	 * @param archivoConfiguración la ruta del archivo
	 * @return la configuración leída
	 * @throws AccesoBDException si no existe el archivo o se produce alguna
	 *                           incidencia durante la lectura
	 */
	public static Properties cargarConfiguración(String archivoConfiguración) throws AccesoBDException {
		Path rutaConfig;
		rutaConfig = Path.of(archivoConfiguración);

		if (!existeArchivo(rutaConfig)) {
			String mensaje;
			mensaje = String.format("No existe el archivo «%s»", rutaConfig.getFileName());
			throw new AccesoBDException(mensaje);
		}

		Properties configuración = new Properties();
		try (FileInputStream in = new FileInputStream(rutaConfig.toFile())) {
			configuración.loadFromXML(in);
		} catch (IOException e) {
			throw new AccesoBDException("Error al cargar configuración", e);
		}

		return configuración;
	}

	/**
	 * Crea un archivo de configuración con los datos de acceso a la base de datos
	 * en formato SQLite. El único aspecto relevante que contiene es el nombre del
	 * archivo.
	 * 
	 * @param archivoConfiguración el nombre, ruta del archivo de configuración
	 * @param archivoBD            el nombre, ruta del archivo de la base de datos
	 * @return la configuración creada
	 */
	public static Properties crearConfiguración(String archivoConfiguración, String archivoBD) {
		Path rutaConfig;
		rutaConfig = Path.of(archivoConfiguración);

		Properties configuración = new Properties();
		configuración.setProperty("jdbc.url", "jdbc:sqlite:" + archivoBD);
		configuración.setProperty("jdbc.user", "");
		configuración.setProperty("jdbc.password", "");
		configuración.setProperty("jdbc.codificación", "UTF-8");

		try (FileOutputStream out = new FileOutputStream(rutaConfig.toFile())) {
			configuración.storeToXML(out, "Configuración BD", "UTF-8");
			System.err.printf("Creado nuevo archivo de configuración «%s» para «%s»%n", archivoConfiguración,
					archivoBD);
		} catch (IOException e) {
			System.err.printf("Error al guardar configuración en «%s»: %s%n", archivoConfiguración,
					e.getLocalizedMessage());
		}
		return configuración;
	}

	/**
	 * Comprueba la existencia de un archivo.
	 * 
	 * @param ruta el nombre, ruta del archivo a comprobar
	 * @return si existe o no
	 * @throws AccesoBDException si el archivo existe pero no se puede leer
	 */
	private static boolean existeArchivo(Path ruta) throws AccesoBDException {
		boolean existe;

		existe = Files.exists(ruta);

		if (existe && !Files.isReadable(ruta)) {
			String mensaje;
			mensaje = String.format("No se puede leer el archivo «%s»", ruta.getFileName());
			throw new AccesoBDException(mensaje);
		}
		return existe;
	}

	/**
	 * Abre la conexión a la base de datos si no ha sido abierta previamente. Crea
	 * también una sentencia SQL genérica –disponible para ejecutar consultas no
	 * preparadas– y la tabla principal en caso de no existir.
	 * 
	 * @return la conexión existente o creada
	 * @throws AccesoBDException si no se completa o se produce alguna incidencia
	 *                           durante la conexión
	 */
	public Connection abrirConexión() throws AccesoBDException {
		if (conexión == null) {
			String jdbcURL = configuración.getProperty("jdbc.url");
			String jdbcUser = configuración.getProperty("jdbc.user");
			String jdbcPassword = configuración.getProperty("jdbc.password");

			try {
				conexión = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);

				if (conexión == null) { // Conexión fallida
					String mensaje = String.format("%s — Conexión fallida 😕%n", jdbcURL);
					throw new AccesoBDException(mensaje);
				}

				sentenciaGeneralSQL = conexión.createStatement();
				sentenciaGeneralSQL.setQueryTimeout(5);
				sentenciaGeneralSQL.execute(SQL_CREAR_TABLA);
			} catch (SQLException e) {
				String mensaje = String.format("%s — Conexión fallida: %s", jdbcURL, e.getLocalizedMessage());
				throw new AccesoBDException(mensaje, e);
			}
		}
		return conexión;
	}

	/**
	 * Lee el contenido completo de la base de datos y crea todos los contactos. Los
	 * datos creados se depositan en la lista VACÍA facilitada. En caso de que la
	 * lista no esté vacía se borrará su contenido.
	 * 
	 * @param lista una lista de contactos vacía
	 * @return el número de contactos
	 * @throws AccesoBDException si se produce alguna incidencia
	 */
	public int leer(Vector<ContactoMóvil> lista) throws AccesoBDException {
		ResultSet resultado;
		ContactoMóvil nuevoContacto;

		String nombre, prefijoInternacional, prefijoNacional, número, extensión;

		if (lista == null) {
			throw new AccesoBDException("Lista nula");
		}

		lista.clear();

		try {
			resultado = sentenciaGeneralSQL.executeQuery(SQL_LISTADO_COMPLETO);
			while (resultado.next()) {
				nombre = resultado.getString("nombre");
				prefijoInternacional = resultado.getString("prefijo_internacional");
				prefijoNacional = resultado.getString("prefijo_nacional");
				número = resultado.getString("número");
				extensión = resultado.getString("extensión");

				nuevoContacto = ContactoMóvil.of(nombre, prefijoInternacional, prefijoNacional, número, extensión);
				lista.add(nuevoContacto);
			}
		} catch (SQLException e) {
			String mensaje = String.format("Error al leer contactos: %s", e.getLocalizedMessage());
			throw new AccesoBDException(mensaje, e);
		}

		return lista.size();
	}

	/**
	 * Inserta un contacto en la base de datos. En caso de no existir la sentencia
	 * preparada se crea -permitiendo así que se pueda compartir en caso de realizar
	 * varias inserciones consecutivas-.
	 * 
	 * @param datos el contacto a grabar
	 * @return el número de filas afectadas –cero o una…–
	 * @throws AccesoBDException si se produce alguna incidencia
	 */
	public int insertar(ContactoMóvil datos) throws AccesoBDException {
		int númFilas = 0;
		try {
			if (preInserciónSQL == null) {
				preInserciónSQL = conexión.prepareStatement(SQL_INSERTAR_CONTACTO);
				preInserciónSQL.setQueryTimeout(5);
			}

			preInserciónSQL.setString(1, datos.getNombre());
			preInserciónSQL.setString(2, datos.getPrefijoInternacional());
			preInserciónSQL.setString(3, datos.getPrefijoNacional());
			preInserciónSQL.setString(4, datos.getNúmeroAbonado());
			preInserciónSQL.setString(5, datos.getExtensión());

			númFilas = preInserciónSQL.executeUpdate();
		} catch (SQLException e) {
			String mensaje = String.format("Error al insertar contacto: %s", e.getLocalizedMessage());
			throw new AccesoBDException(mensaje, e);
		}
		return númFilas;
	}

	/**
	 * Inserta una colección de contactos en la base de datos.
	 * 
	 * @param lista los contactos a grabar
	 * @return el número de filas afectadas, debería coincidir con el tamaño de la
	 *         colección original
	 * @throws AccesoBDException si se produce alguna incidencia
	 */
	public int escribir(Vector<ContactoMóvil> lista) throws AccesoBDException {
		if (lista == null) {
			throw new AccesoBDException("Lista nula");
		}

		int númFilas = 0;

		for (ContactoMóvil contacto : lista) {
			númFilas += insertar(contacto);
		}

		return númFilas;
	}


	/**
	 * Descarta la sentencias SQL inicializadas y deja cerrada la conexión.
	 * 
	 * @throws AccesoBDException si se produce alguna incidencia
	 */
	@Override
	public void close() throws AccesoBDException {
		if (conexión != null) {
			try {
				Connection conexiónCerrada = conexión;
				conexión = null;
				sentenciaGeneralSQL = null;
				preInserciónSQL = null;
				conexiónCerrada.close();
			} catch (SQLException e) {
				String mensaje = String.format("Error en cierre de conexión: %s", e.getLocalizedMessage());
				throw new AccesoBDException(mensaje, e);
			}
		}
	}

	/**
	 * Inserta datos de ejemplo aleatorios en la base de datos.
	 * 
	 * @throws AccesoBDException si se produce alguna incidencia
	 */
	private void generarBD() throws AccesoBDException {
		Vector<ContactoMóvil> lista;
		ContactoMóvil nuevo;

		String[] nombres = { "Alfredo", "Berta", "Carlos", "Daniela", "Ernesto", "Fina", "Germán", "Heidi", "Ignacio",
				"Jimena", "Koldo", "Luz", "Manuel" };
		String nombre, pInternacional, pNacional, número, extensión;
		int númElementos;

		Random rnd = new Random();
		númElementos = 90 + rnd.nextInt(20);
		lista = new Vector<ContactoMóvil>(númElementos);

		for (int i = 0; i < númElementos; i++) {
			nombre = String.format("%s_%03d", nombres[rnd.nextInt(nombres.length)], rnd.nextInt(1000));
			pInternacional = String.format("%03d", rnd.nextInt(1000));
			pNacional = String.format("%03d", rnd.nextInt(1000));
			número = String.format("%06d", rnd.nextInt(1000000));
			extensión = String.format("%06d", rnd.nextInt(1000000));

			nuevo = ContactoMóvil.of(nombre, pInternacional, pNacional, número, extensión);

			lista.add(nuevo);
		}
		abrirConexión();
		escribir(lista);
	}

	public static void main(String[] args) {
		try (AccesoBD acceso = new AccesoBD()) {
			// Cierre implícito con close() –try_with_resources–
			acceso.generarBD();
		} catch (AccesoBDException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
}
