package examenFINAL;

import java.util.Vector;

public class ContactoMóvil {
	private String nombre;
	private String prefijoInternacional;
	private String prefijoNacional;
	private String númeroAbonado;
	private String extensión;
	private final String VACÍO = "";

	private ContactoMóvil(String nombre) {
		this.nombre = nombre;
		prefijoInternacional = VACÍO;
		prefijoNacional = VACÍO;
		númeroAbonado = VACÍO;
		extensión = VACÍO;
	}

	/**
	 * Crea un contacto con un nombre y especificando todos los cuatro posibles
	 * campos que forman el número: prefijo internacional, prefijo local, número de
	 * abonado y extensión. Se pueden crear contactos con menos campos utilizando
	 * otros métodos.
	 * 
	 * <pre>
	 *   N + IPNE → of              (… 1 + 4 parámetros …)
	 *   N + IPN- → of              (… 1 + 3 parámetros …)
	 *   N + -PN- → of              (… 1 + 2 parámetros …)
	 *   N + --N- → of              (… 1 + 1 parámetros …)
	 *   N + I-NE → ofInternacional (… 1 + 3 parámetros …)	
	 *   N + I-N- → ofInternacional (… 1 + 2 parámetros …)	
	 *   N + -PNE → ofExtensión     (… 1 + 3 parámetros …)	
	 *   N + --NE → ofExtensión     (… 1 + 2 parámetros …)
	 * </pre>
	 * 
	 * @param nombre               el nombre del contacto
	 * @param prefijoInternacional el prefijo internacional
	 * @param prefijoNacional      el prefijo local
	 * @param númeroAbonado        el número de abonado
	 * @param extensión            el número de extensión interna
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 */
	public static ContactoMóvil of(String nombre, String prefijoInternacional, String prefijoNacional,
			String númeroAbonado, String extensión) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setPrefijoInternacional(prefijoInternacional);
		contacto.setPrefijoNacional(prefijoNacional);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.setExtensión(extensión);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y especificando el prefijo internacional, el
	 * prefijo local y el número de abonado.
	 * 
	 * @param nombre               el nombre del contacto
	 * @param prefijoInternacional el prefijo internacional
	 * @param prefijoNacional      el prefijo local
	 * @param númeroAbonado        el número de abonado
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil of(String nombre, String prefijoInternacional, String prefijoNacional,
			String númeroAbonado) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setPrefijoInternacional(prefijoInternacional);
		contacto.setPrefijoNacional(prefijoNacional);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y especificando el prefijo local y el número
	 * de abonado.
	 * 
	 * @param nombre          el nombre del contacto
	 * @param prefijoNacional el prefijo local
	 * @param númeroAbonado   el número de abonado
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil of(String nombre, String prefijoNacional, String númeroAbonado) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setPrefijoNacional(prefijoNacional);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y el número de abonado.
	 * 
	 * @param nombre        el nombre del contacto
	 * @param númeroAbonado el número de abonado
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil of(String nombre, String númeroAbonado) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y especificando el prefijo internacional, el
	 * número de abonado y la extensión interna.
	 * 
	 * @param nombre               el nombre del contacto
	 * @param prefijoInternacional el prefijo internacional
	 * @param númeroAbonado        el número de abonado
	 * @param extensión            el número de extensión interna
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil ofInternacional(String nombre, String prefijoInternacional, String prefijoNacional,
			String númeroAbonado) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setPrefijoInternacional(prefijoInternacional);
		contacto.setPrefijoNacional(prefijoNacional);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y especificando el prefijo internacional y el
	 * número de abonado.
	 * 
	 * @param nombre               el nombre del contacto
	 * @param prefijoInternacional el prefijo internacional
	 * @param númeroAbonado        el número de abonado
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil ofInternacional(String nombre, String prefijoInternacional, String númeroAbonado) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setPrefijoInternacional(prefijoInternacional);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y especificando el prefijo internacional el
	 * número de abonado y la extensión.
	 * 
	 * @param nombre               el nombre del contacto
	 * @param prefijoInternacional el prefijo internacional
	 * @param númeroAbonado        el número de abonado
	 * @param extensión            el número de extensión interna
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil ofExtensión(String nombre, String prefijoInternacional, String númeroAbonado,
			String extensión) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setPrefijoInternacional(prefijoInternacional);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.setExtensión(extensión);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Crea un contacto con un nombre y especificando el número de abonado y la
	 * extensión.
	 * 
	 * @param nombre        el nombre del contacto
	 * @param númeroAbonado el número de abonado
	 * @param extensión     el número de extensión interna
	 * 
	 * @return el contacto creado
	 * 
	 * @throws AgendaMóvilException si los datos son incorrectos o insuficientes.
	 * 
	 * @see #of(String nombre, String prefijoInternacional, String prefijoNacional,
	 *      String númeroAbonado, String extensión)
	 */
	public static ContactoMóvil ofExtensión(String nombre, String númeroAbonado, String extensión) {
		ContactoMóvil contacto = new ContactoMóvil(nombre);
		contacto.setnúmeroAbonado(númeroAbonado);
		contacto.setExtensión(extensión);
		contacto.verificarContacto();
		return contacto;
	}

	/**
	 * Comprueba si una cadena de texto está formada solo por dígitos numéricos.
	 * 
	 * @param texto el texto a comprobar
	 * 
	 * @return si es cierto
	 */
	public static boolean esNúmero(String texto) {
		boolean esNúmero = false;
		if (texto != null) {
			String patrónRegex = "\\d+";
			esNúmero = texto.matches(patrónRegex);
		}
		return esNúmero;
	}

	/**
	 * Comprueba que una cadena de texto esté formada solo por dígitos numéricos.
	 * 
	 * @param texto el texto a comprobar
	 * 
	 * @throws AgendaMóvilException si no se cumple
	 */
	public static void verificarNúmero(String texto) {
		if (!esNúmero(texto)) {
			throw new AgendaMóvilException("El contenido solo debe contener números");
		}
	}

	/**
	 * Comprueba que una cadena de texto tenga contenido —que no sea nula ni esté
	 * vacía—.
	 * 
	 * @param texto el texto a comprobar
	 * 
	 * @throws AgendaMóvilException si no se cumple
	 */
	public static void verificarNombre(String texto) {
		if (texto == null || texto.length() == 0) {
			throw new AgendaMóvilException("Es necesario indicar el nombre");
		}
	}

	/**
	 * Comprueba que el contacto sea vaĺido; que tenga al menos un nombre y número
	 * de abonado válidos —que no sean nulos ni estén vacíos— .
	 * 
	 * @throws AgendaMóvilException si no se cumple
	 */
	public void verificarContacto() {
		if (nombre == null || nombre.length() == 0 || númeroAbonado == null || númeroAbonado.length() == 0) {
			throw new AgendaMóvilException("Contacto incompleto");
		}
	}

	/**
	 * Modfica el nombre.
	 * 
	 * @param nombre el nombre indicado.
	 * 
	 * @throws AgendaMóvilException si es nulo o está vacío.
	 */
	public void setNombre(String nombre) {
		verificarNombre(nombre);
		this.nombre = nombre;
	}

	/**
	 * Modifica el prefijo internacional.
	 * 
	 * @param prefijoInternacional la cadena de números
	 * 
	 * @throws AgendaMóvilException si no es solo numérica.
	 */
	public void setPrefijoInternacional(String prefijoInternacional) {
		verificarNúmero(prefijoInternacional);
		this.prefijoInternacional = prefijoInternacional;
	}

	/**
	 * Modifica el prefijo nacional.
	 * 
	 * @param prefijoNacional la cadena de números
	 * 
	 * @throws AgendaMóvilException si no es solo numérica.
	 */
	public void setPrefijoNacional(String prefijoNacional) {
		verificarNúmero(prefijoNacional);
		this.prefijoNacional = prefijoNacional;
	}

	/**
	 * Modifica el número de abonado.
	 * 
	 * @param númeroAbonado la cadena de números
	 * 
	 * @throws AgendaMóvilException si no es solo numérica.
	 */
	public void setnúmeroAbonado(String númeroAbonado) {
		verificarNúmero(númeroAbonado);
		this.númeroAbonado = númeroAbonado;
	}

	/**
	 * Modifica el número de abonado.
	 * 
	 * @param extensión la cadena de números
	 * 
	 * @throws AgendaMóvilException si no es solo numérica.
	 */
	public void setExtensión(String extensión) {
		verificarNúmero(extensión);
		this.extensión = extensión;
	}

	/**
	 * Proporciona el nombre del contacto
	 * 
	 * @return el texto correspondiente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Proporciona el prefijo internacional del contacto
	 * 
	 * @return el texto correspondiente
	 */
	public String getPrefijoInternacional() {
		return prefijoInternacional;
	}

	/**
	 * Proporciona el prefijo nacional del contacto
	 * 
	 * @return el texto correspondiente
	 */
	public String getPrefijoNacional() {
		return prefijoNacional;
	}

	/**
	 * Proporciona el número de abonado del contacto
	 * 
	 * @return el texto correspondiente
	 */
	public String getNúmeroAbonado() {
		return númeroAbonado;
	}

	/**
	 * Proporciona el número de extensión del contacto
	 * 
	 * @return el texto correspondiente
	 */
	public String getExtensión() {
		return extensión;
	}

	/**
	 * Proporciona un texto resumen del contacto
	 * 
	 * @return el texto correspondiente
	 */
	@Override
	public String toString() {
		verificarContacto();
		StringBuffer sbTexto = new StringBuffer();

		sbTexto.append(String.format("«%s» ", nombre));

		if (!VACÍO.equals(prefijoInternacional)) {
			sbTexto.append(String.format("+%s ", prefijoInternacional));
		}

		if (!VACÍO.equals(prefijoNacional)) {
			sbTexto.append(String.format("(%s) ", prefijoNacional));
		}

		char[] caracteres = númeroAbonado.toCharArray();
		int longitud = caracteres.length;
		int espaciado;

		if (longitud < 6) {
			espaciado = 6;
		} else if (longitud < 10 && longitud % 3 == 0) {
			espaciado = 3;
		} else {
			espaciado = 4;
		}

		int i = longitud;
		for (char c : caracteres) {
			sbTexto.append(c);
			i--;
			if (i > 0 && i % espaciado == 0) {
				sbTexto.append(' ');
			}
		}
		if (!VACÍO.equals(extensión)) {
			sbTexto.append(String.format(" #%s", extensión));
		}

		return sbTexto.toString();
	}

	/** Ejemplo demostración mostrando varias combinaciones de números admisibles */
	public static void main(String[] args) {
		Vector<ContactoMóvil> lista = new Vector<ContactoMóvil>();

		lista.add(ContactoMóvil.of("Pepe", "1234567"));
		lista.add(ContactoMóvil.of("Pepe", "123457"));
		lista.add(ContactoMóvil.ofInternacional("John", "44", "1234567"));
		lista.add(ContactoMóvil.of("Mike", "44", "0473", "123456", "001"));

		for (ContactoMóvil contacto : lista) {
			System.out.println(contacto);
		}
	}
}
