package com.ecommerce.model;
import com.ecommerce.exception.CantidadInvalidaException;

/**
 * Representa una línea en el carrito de compras (Producto + Cantidad).
 */

public class ItemCarrito {
	private Producto producto;
	private int cantidad;
	
	public ItemCarrito(Producto producto, int cantidad) throws CantidadInvalidaException {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a 0.");
        }
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    
    @Override
    public String toString() {
        return String.format("%s | Cant: %d | Subtotal: $%.2f", producto.getNombre(), cantidad, getSubtotal());
    }
}