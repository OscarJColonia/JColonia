package examenConsolaMCV;

/**
 * Excepción personalizada para opciones erróneas en el menú
 * @Author Oscar Fernández Santamaría
 * @Version 13/05/21
 */
public class InvalidChoiceException extends Exception{


    private static final long serialVersionUID = 20210424001L;

    /**
     * Crea una excepción sin ninguna información adicional.
     */
    public InvalidChoiceException() {
        super("Opción no válida");
    }

    /**
     * Crea una excepción con un texto descriptivo.
     *
     * @param message el texto correspondiente
     */
    public InvalidChoiceException(String message) {
        super(message);
    }

    /**
     * Crea una excepción secundaria almacenando otra excepción de referencia.
     *
     * @param cause la excepción –o {@link Throwable}– correspondiente
     */
    public InvalidChoiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Crea una excepción secundaria almacenando otra excepción de referencia y un
     * texto descriptivo.
     *
     * @param message el texto correspondiente
     * @param cause   la excepción –o {@link Throwable}– correspondiente
     */
    public InvalidChoiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

