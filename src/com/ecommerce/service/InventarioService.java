package com.ecommerce.service;

import com.ecommerce.model.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InventarioService {

    // "Base de Datos" de productos (ID -> Objeto Producto)
    private final Map<String, Producto> catalogo = new ConcurrentHashMap<>();
    // Control de Stock (ID -> Cantidad)
    private final Map<String, Integer> stock = new ConcurrentHashMap<>();

    public void guardarProducto(Producto producto, int cantidadInicial) {
        String id = String.valueOf(producto.getId());
        catalogo.put(id, producto);
        stock.put(id, cantidadInicial);
    }

    public Producto buscarProductoPorId(String id) {
        return catalogo.get(id);
    }

    public List<Producto> listarProductos() {
        return new ArrayList<>(catalogo.values());
    }

    public List<Producto> buscarPorNombreOCategoria(String criterio) {
        String filtro = criterio.toLowerCase();
        return catalogo.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(filtro) || 
                             p.getCategoria().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
    }

    public void eliminarProducto(String id) {
        catalogo.remove(id);
        stock.remove(id);
    }

    // --- Métodos de Stock existentes ---

    public boolean tieneStockSuficiente(String productoId, int cantidadRequerida) {
        return stock.getOrDefault(productoId, 0) >= cantidadRequerida;
    }

    public boolean consumirStock(String productoId, int cantidad) {
        return stock.compute(productoId, (id, stockActual) -> {
            if (stockActual == null || stockActual < cantidad) return stockActual;
            return stockActual - cantidad;
        }) != null && stock.get(productoId) >= 0;
    }
    
    public int obtenerStockActual(String productoId) {
        return stock.getOrDefault(productoId, 0);
    }
}