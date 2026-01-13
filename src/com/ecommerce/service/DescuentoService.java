package com.ecommerce.service;

import java.util.HashMap;
import java.util.Map;

public class DescuentoService {

    // Mapa para almacenar cupones: CÓDIGO -> Porcentaje (0.10 = 10%)
    private final Map<String, Double> cuponesValidos = new HashMap<>();

    public DescuentoService() {
        // Inicializamos algunos cupones de prueba
        cuponesValidos.put("DESCUENTO10", 0.10);
        cuponesValidos.put("VIP2026", 0.20);
        cuponesValidos.put("CODEMASTER", 0.50);
    }

    /**
     * Calcula el monto a descontar basado en un código de cupón.
     * Nota: Hemos quitado la tilde de 'Cupon' para evitar errores.
     */
    public double aplicarCupon(String codigo, double totalCompra) {
        if (codigo == null || !cuponesValidos.containsKey(codigo.toUpperCase())) {
            return 0.0;
        }
        
        double porcentaje = cuponesValidos.get(codigo.toUpperCase());
        return totalCompra * porcentaje;
    }

    /**
     * Calcula descuentos automáticos basados en volumen de compra.
     */
    public double calcularDescuentoPorVolumen(double totalCompra) {
        double umbralCompraGrande = 1000.0;
        double tasaDescuentoVolumen = 0.05; // 5%

        if (totalCompra > umbralCompraGrande) {
            return totalCompra * tasaDescuentoVolumen;
        }
        return 0.0;
    }
}