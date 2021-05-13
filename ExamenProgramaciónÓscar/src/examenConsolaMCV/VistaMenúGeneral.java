package examenConsolaMCV;

import java.io.PrintStream;
import java.util.*;


public class VistaMenúGeneral extends VistaGeneral{

		private final String título;
	    private final String[] options;
	    private final PrintStream out;

	    /**
	     * Constructor
	     *
	     * @param title Título que aparece en el menú
	     * @param options Opciones del menú
	     * @param scanner , título y options
	     */
	    public VistaMenúGeneral(String título, String[] options, Scanner scanner) {
	        super(título,scanner);
	        this.título = título;
	        this.options = options;
	        this.out = System.out;
	    }
	    
	    public static String fill(int length, char c) {
	        char[] fill = new char[length];
	        Arrays.fill(fill, c);
	        return new String(fill);
	    }

	    /**
	     * Mostrar el menú
	     */
	    public void showMenu() {
	        final String FORMATO_OPCIONES = " %d) %s%n";

	        out.println(fill(this.título.length(), '-'));
	        out.println(this.título);
	        out.println(fill(this.título.length(), '-'));

	        for (int i = 1; i <= this.options.length; i++) {
	            out.printf(FORMATO_OPCIONES, i, options[i - 1]);
	        }

	        out.printf(FORMATO_OPCIONES, 0, "SALIR");
	        out.printf("%n  Escriba la opción elegida → ");
	    }

	    /**
	     * Pedir al usuario la opción del menú
	     *
	     * @return Opción escogida
	     */
	    public int elegirOpción() {
	        boolean exit = false;
	        int option = -1;
	        do  {
	            try{
	                showMenu();
	                option = this.getNumber();
	                if (option < 0 || option > this.options.length) {
	                    throw new InvalidChoiceException();
	                }
	                exit = true;
	            } catch (NumberFormatException e) {
	                showAlert("¡El sistema solo admite números enteros!");
	            } catch (InvalidChoiceException e) {
	                showAlert(e.getMessage());
	                showAlert(String.format(
	                        "¡Hay que introducir una opción de menú válida (1-%d, 0 para salir)!",
	                        this.options.length));
	            }
	        }while (!exit);
	        return option;
	    }

	
	
}
