package examenFINAL;

/**
 * Excepción usada generalmente en la aplicación «AgendaMóvil» cuando se intenta
 * añadir un campo que no es exclusivamente numérico.
 * 
 * @versión 2021.2.0
 * @author <a href="dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class AgendaMóvilException extends RuntimeException {

	/**
	 * Número de serie, asociado a la versión de la clase.
	 */
	private static final long serialVersionUID = 20210602001L;

	/**
	 * Crea una excepción sin ninguna información adicional.
	 */
	public AgendaMóvilException() {
		super();
	}

	/**
	 * Crea una excepción con un texto descriptivo.
	 * 
	 * @param mensaje el texto correspondiente
	 */
	public AgendaMóvilException(String mensaje) {
		super(mensaje);
	}

	/**
	 * Crea una excepción secundaria almacenando otra excepción de referencia.
	 * 
	 * @param causa la excepción –o {@link Throwable}– correspondiente
	 */
	public AgendaMóvilException(Throwable causa) {
		super(causa);
	}

	/**
	 * Crea una excepción secundaria almacenando otra excepción de referencia y un
	 * texto descriptivo.
	 * 
	 * @param mensaje el texto correspondiente
	 * @param causa   la excepción –o {@link Throwable}– correspondiente
	 */
	public AgendaMóvilException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}