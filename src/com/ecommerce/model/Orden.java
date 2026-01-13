package com.ecommerce.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa una compra finalizada. Inmutable (snapshot de la venta).
 */
public class Orden {
    private List<ItemCarrito> items;
    private double totalFinal;
    
    public Orden(List<ItemCarrito> items, double totalFinal) {
        // Creamos una copia de la lista para que si el carrito cambia después, la orden no se afecte
        this.items = new ArrayList<>(items);
        this.totalFinal = totalFinal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ORDEN FINALIZADA ---\n");
        for (ItemCarrito item : items) {
            sb.append(item).append("\n");
        }
        sb.append("TOTAL PAGADO: $").append(String.format("%.2f", totalFinal));
        return sb.toString();
    }
}