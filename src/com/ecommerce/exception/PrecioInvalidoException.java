package com.ecommerce.exception;

/**
 * Excepción lanzada cuando el precio de un producto es <= 0.
 */
public class PrecioInvalidoException extends Exception {
	public PrecioInvalidoException(String mensaje) {
		super(mensaje);
	}

}
