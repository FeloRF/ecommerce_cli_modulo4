package com.ecommerce.service;

import com.ecommerce.model.Producto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoService {

    private final InventarioService inventarioService;
    private final DescuentoService descuentoService; // ¡Esta es la pieza que faltaba!
    
    private final Map<String, ItemCarrito> items = new HashMap<>();
    private String cupónAplicado = null; 

    // Constructor actualizado para recibir DOS servicios
    public CarritoService(InventarioService inventarioService, DescuentoService descuentoService) {
        this.inventarioService = inventarioService;
        this.descuentoService = descuentoService;
    }

    public static class ItemCarrito {
        private final Producto producto;
        private int cantidad;
        private double subtotal;

        public ItemCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.actualizarSubtotal();
        }

        public void agregarCantidad(int cantidad) {
            this.cantidad += cantidad;
            this.actualizarSubtotal();
        }

        public void restarCantidad(int cantidad) {
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

    public void agregarProducto(Producto producto, int cantidad) {
        if (cantidad <= 0) return;
        
        String id = String.valueOf(producto.getId()); 
        
        int cantidadEnCarrito = items.containsKey(id) ? items.get(id).getCantidad() : 0;
        int cantidadTotalRequerida = cantidadEnCarrito + cantidad;

        if (!inventarioService.tieneStockSuficiente(id, cantidadTotalRequerida)) {
            throw new IllegalArgumentException("Stock insuficiente para: " + producto.getNombre());
        }

        if (items.containsKey(id)) {
            items.get(id).agregarCantidad(cantidad);
        } else {
            items.put(id, new ItemCarrito(producto, cantidad));
        }
    }

    public void removerProducto(String productoId, int cantidad) {
        if (!items.containsKey(productoId)) return;
        ItemCarrito item = items.get(productoId);
        int nuevaCantidad = item.getCantidad() - cantidad;

        if (nuevaCantidad > 0) {
            item.restarCantidad(cantidad);
        } else {
            items.remove(productoId);
        }
    }

    public double obtenerTotal() {
        return items.values().stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    // --- MÉTODOS QUE FALTABAN Y CAUSABAN ERROR EN MAIN ---

    public void aplicarCupon(String codigo) {
        this.cupónAplicado = codigo;
    }

    public double calcularTotalConDescuentos() {
        double totalBase = obtenerTotal();
        
        // CORRECCIÓN AQUÍ: Llamamos a 'aplicarCupon' (sin tilde)
        double descuentoCupon = descuentoService.aplicarCupon(cupónAplicado, totalBase);
        
        double descuentoVolumen = descuentoService.calcularDescuentoPorVolumen(totalBase);
        
        double totalFinal = totalBase - descuentoCupon - descuentoVolumen;
        return Math.max(0, totalFinal);
    }
    
    public double obtenerMontoAhorrado() {
        return obtenerTotal() - calcularTotalConDescuentos();
    }
    // -----------------------------------------------------

    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items.values());
    }
    
    public void finalizarCompra() {
        items.forEach((id, item) -> {
            boolean exito = inventarioService.consumirStock(id, item.getCantidad());
            if (!exito) {
                throw new IllegalStateException("Error de stock al finalizar compra: " + item.getProducto().getNombre());
            }
        });
        limpiarCarrito();
    }

    public void limpiarCarrito() {
        items.clear();
        this.cupónAplicado = null;
    }
}