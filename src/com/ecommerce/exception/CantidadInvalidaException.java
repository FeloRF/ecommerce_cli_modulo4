package com.ecommerce.exception;

// Extendemos de RuntimeException para que no nos obligue a poner try-catch en todos lados
public class CantidadInvalidaException extends RuntimeException {
    
    public CantidadInvalidaException(String mensaje) {
        super(mensaje);
    }
}