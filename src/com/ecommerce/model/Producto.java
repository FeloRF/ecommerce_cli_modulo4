package com.ecommerce.model;

import com.ecommerce.exception.PrecioInvalidoException;

public class Producto {
    private int id;
    private String nombre;
    private String categoria; // ¡Este campo faltaba!
    private double precio;

    // Constructor actualizado con 4 parámetros
    public Producto(int id, String nombre, String categoria, double precio) throws PrecioInvalidoException {
        if (precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo.");
        }
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    // --- GETTERS (Para leer datos) ---
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() { // ¡El método que causaba el error!
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    // --- SETTERS (Para editar datos en el MenuAdmin) ---
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }
    
    // Opcional: Para imprimir bonito en consola si usas System.out.println(producto)
    @Override
    public String toString() {
        return nombre + " (" + categoria + ") - $" + precio;
    }
}