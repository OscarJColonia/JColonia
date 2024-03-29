package examenConsolaMCV;

import java.util.ArrayList;
import java.util.Scanner;



/**
 * Gestión de números «decimales»: recogida y visualización de la suma
 * 
 * @versión 2021.2.1
 * @author <a href="dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class ControlSumatorio {
	/**
	 * Texto identificativo de las funciones de la aplicación que aparecerán en el
	 * menú principal.
	 */
	private static final String[] OPCIONES_MENÚ_PRINCIPAL = { "Agregar valor", "Mostrar valores", "Mostrar suma",
			"Restablecer" };

	/**
	 * Título de la aplicación. Se mostrará como encabezado del menú principal.exam
	 */
	private static final String TÍTULO_MENÚ_PRINCIPAL = "Sumatorio";

	/**
	 * Recurso asociado a la entrada estándar de la aplicación. Debe ser un objeto
	 * único a compartir con las diferentes vistas creadas.
	 */
	private Scanner in;

	private String conjunto;

	private VistaMenúGeneral menúPrincipal;
	
	private VistaDatos datos;

	public ControlSumatorio(Scanner in) {
		this.in = in;
	}

	private void buclePrincipal() throws Throwable {
		menúPrincipal = new VistaMenúGeneral(TÍTULO_MENÚ_PRINCIPAL, OPCIONES_MENÚ_PRINCIPAL, in);

		int entrada = -1;

		// Bucle general
		do {
			entrada = menúPrincipal.elegirOpción();
			switch (entrada) {
			case 0: // Salir
				menúPrincipal.mostrarMensaje("¡¡¡A-D-I-O-S!!");
				break;
			case 1: // Opción 1: Entrada datos
				cargarSumando();
				break;
			case 2: // Opción 2: Mostrar sumandos
				mostrarSumandos();
				break;
//			case 3: // Opción 3: Mostrar suma
//				mostrarSuma();
//				break;
//			case 4: // Opción 4: Reset
//				restablecer();
//				break;
			default: // Opción no esperada: abortar
				ejecutarGenérico(entrada);
				System.err.println("Error interno de programa - operación pendiente de desarrollo");
				System.exit(1);
			}
		} while (entrada != 0);
	}

	private void ejecutarGenérico(int id) {
		String mensaje;
		mensaje = String.format("%n  [%03d] Ha elegido la opción %d: «%s»", menúPrincipal.elegirOpción(), id,
				OPCIONES_MENÚ_PRINCIPAL[id - 1]);
		menúPrincipal.mostrarMensaje(mensaje);
	}
	
	   /**
     * Añadir un nuevo sumando
	 * @throws Throwable 
     */
    public void cargarSumando() throws Throwable {
        datos = new VistaDatos("Valor", this.in);
        conjunto = conjunto + datos.getNuevoNúmero();
    }
    
    public void mostrarSumandos() {
      System.out.println(conjunto);
    }
    

	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(System.in);

		ControlSumatorio control = new ControlSumatorio(sc);
		control.buclePrincipal();
		sc.close();
	}
}
