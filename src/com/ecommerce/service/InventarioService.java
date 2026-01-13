package com.ecommerce.service;

import com.ecommerce.model.Producto;
import com.ecommerce.exception.PrecioInvalidoException;
import com.ecommerce.exception.ProductoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la gestión del inventario (Lógica del ADMIN).
 * Maneja la lista de productos en memoria y asegura la consistencia de los datos.
 */
public class InventarioService {

    // Simulación de base de datos en memoria
    private List<Producto> productos;
    private int contadorId; // Para autogenerar IDs únicos (1, 2, 3...)

    public InventarioService() {
        this.productos = new ArrayList<>();
        this.contadorId = 1;
        cargarDatosIniciales(); // Opcional: para no empezar con la lista vacía al probar
    }

    /**
     * Crea un nuevo producto y lo agrega al inventario.
     * El ID se genera automáticamente.
     */
    public void crearProducto(String nombre, String categoria, double precio) throws PrecioInvalidoException {
        // La validación del precio ocurre dentro del constructor de Producto
        Producto nuevo = new Producto(contadorId++, nombre, categoria, precio);
        productos.add(nuevo);
    }

    /**
     * Devuelve una COPIA de la lista de todos los productos.
     * (Retornamos una copia para proteger la lista original de modificaciones externas)
     */
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Busca productos cuyo nombre o categoría contengan el término buscado (ignora mayúsculas).
     */
    public List<Producto> buscarProductos(String termino) {
        String terminoMin = termino.toLowerCase();
        // Usamos Streams de Java 8+ para filtrar de manera elegante y eficiente
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(terminoMin) || 
                             p.getCategoria().toLowerCase().contains(terminoMin))
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto específico por su ID exacto.
     * Método auxiliar útil para editar y eliminar.
     */
    public Producto obtenerPorId(int id) throws ProductoNoEncontradoException {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException("No existe producto con ID: " + id));
    }

    /**
     * Actualiza los datos de un producto existente.
     */
    public void editarProducto(int id, String nuevoNombre, String nuevaCategoria, double nuevoPrecio) 
            throws ProductoNoEncontradoException, PrecioInvalidoException {
        
        Producto producto = obtenerPorId(id); // Reutilizamos la búsqueda segura
        
        // Actualizamos los campos
        if (!nuevoNombre.isEmpty()) producto.setNombre(nuevoNombre);
        if (!nuevaCategoria.isEmpty()) producto.setCategoria(nuevaCategoria);
        
        // El setter de Producto valida el precio > 0, si falla lanza la excepción
        producto.setPrecio(nuevoPrecio); 
    }

    /**
     * Elimina un producto del inventario.
     */
    public void eliminarProducto(int id) throws ProductoNoEncontradoException {
        Producto producto = obtenerPorId(id);
        productos.remove(producto);
    }

    // --- Método Privado Auxiliar ---
    
    private void cargarDatosIniciales() {
        try {
            crearProducto("Laptop Gamer", "Electronica", 1500.00);
            crearProducto("Mouse Inalambrico", "Electronica", 25.50);
            crearProducto("Silla Ergonomica", "Muebles", 120.00);
            crearProducto("Escritorio Madera", "Muebles", 200.00);
            crearProducto("Auriculares Noise-Cancel", "Audio", 300.00);
        } catch (PrecioInvalidoException e) {
            // Esto no debería pasar en la carga inicial controlada
            System.err.println("Error cargando datos iniciales: " + e.getMessage());
        }
    }
}