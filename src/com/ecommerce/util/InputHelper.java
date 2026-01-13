package com.ecommerce.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase utilitaria para gestionar la entrada de datos por consola.
 * Centraliza el manejo de excepciones (InputMismatchException) y la limpieza del buffer.
 * Todos los métodos son estáticos para usarlos directamente sin instanciar la clase.
 */

public class InputHelper {
	
	// Scanner único para toda la aplicación (evita problemas de apertura/cierre múltiples)
    private static final Scanner scanner = new Scanner(System.in);
    
	/**
     * Solicita un número entero al usuario. Repite la solicitud hasta obtener un valor válido.
     * * @param mensaje El texto que se mostrará al usuario pidiendo el dato.
     * @return Un número entero válido.
     */
    public static int leerEntero(String mensaje) {
    	int valor = 0;
    	boolean entradaValida = false;
    	
    	while (!entradaValida) {
            try {
                System.out.print(mensaje + ": ");
                valor = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea residual (buffer)
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println(">> Error: Debe ingresar un número entero. Intente de nuevo.");
                scanner.nextLine(); // Limpiar el buffer de la entrada incorrecta
            }
    	}
    	return valor;
    	/**
         * Solicita una cadena de texto al usuario.
         * * @param mensaje El texto que se mostrará al usuario.
         * @return El texto ingresado.
         */
    	public static String leerTexto(String mensaje) {
    		System.out.print(mensaje + ": ");
            // Usamos nextLine para permitir frases con espacios (ej. "Bicicleta Roja")
            return scanner.nextLine(); 
        }	
    
}
