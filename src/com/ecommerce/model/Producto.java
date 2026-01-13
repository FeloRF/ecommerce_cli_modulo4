package com.ecommerce.model;
import com.ecommerce.exception.PrecioInvalidoException;

/**
 * Representa un producto dosponible en inventario.
 */

public class Producto {
	private int id;
	private String nombre;
	private String categoria; 
	private double precio;
	
	public Producto(int id,
			String nombre, 
			String categoria, 
			double precio) 
			throws PrecioInvalidoException {
		if (precio <= 0) {
			throw new PrecioInvalidoException("El precio debe ser mayor a 0.");
		}
		
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;	
	}
	
	// Getters y Seteters
	public int getId() { returb id; }
	public String getNombre() {return nombre; }
	public String getCategotias() {return categoria; }
	public double getPrecio() {return precio; }
	
	public void setPrecio(double precio) throws PrecioInvalidoException {
        if (precio <= 0) throw new PrecioInvalidoException("El precio debe ser mayor a 0.");
        this.precio = precio;
    }
	
	// Setters básicos para nombre/categoría si se requieren editar
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s) | $%.2f", id, nombre, categoria, precio);
    }
}
