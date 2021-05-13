package examenConsolaMCV;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Vista de registro de jugadores
 *
 * @Author Oscar Fernández Santamaría
 * @Version 13/05/21
 */
public class VistaDatos extends VistaGeneral{

    private PrintStream out; 
    private final String título;


    /**
     * Controlador
     * @param scanner Scanner
     */
    public VistaDatos(String título, Scanner scanner) {
    	super(título,scanner);
        out = System.out;
        this.título = título;
    }

    /**
     * Formulario de registro de nuevo jugador
     * @return Jugador creado en el formulario
     * @throws Throwable 
     */
    public String getNuevoNúmero() throws Throwable {
    	ListaNúmeros num = new ListaNúmeros();
        System.out.println("Introduzca un nuevo número: ");
        System.out.println("Numero:");
        num.add(in.nextDouble());
        return num.getLista();
    }
    

    /**
     * Confirmación de creación de nuevo jugador
     *
     * @param player Jugador a confirmar
     * @return Confirmación del usuario (true/false)
     */
//    public boolean confirmPlayer(Player player) {
//        this.showText("Datos del jugador:");
//        this.showText(player.toString());
//        return this.confirm("¿Dar de alta el jugador?");
//    }
}