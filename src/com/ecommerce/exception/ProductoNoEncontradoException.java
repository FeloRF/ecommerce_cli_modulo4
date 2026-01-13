package com.ecommerce.exception;

/**
 * Excepción lanzada cuando se busca un producto por ID y no existe 
 * en el inventario.
 */
public class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}