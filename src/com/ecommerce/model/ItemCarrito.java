package com.ecommerce.model; // Asegúrate que esté en este paquete

import com.ecommerce.exception.CantidadInvalidaException;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;
    private double subtotal; // Agregamos subtotal como atributo

    // El constructor valida y lanza la excepción si algo está mal
    public ItemCarrito(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a 0.");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.actualizarSubtotal();
    }

    public void agregarCantidad(int cantidad) {
        if (cantidad <= 0) throw new CantidadInvalidaException("No puedes agregar 0 o menos.");
        this.cantidad += cantidad;
        this.actualizarSubtotal();
    }

    public void restarCantidad(int cantidad) {
        if (cantidad <= 0) throw new CantidadInvalidaException("No puedes restar 0 o menos.");
        this.cantidad -= cantidad;
        this.actualizarSubtotal();
    }

    private void actualizarSubtotal() {
        this.subtotal = this.producto.getPrecio() * this.cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}