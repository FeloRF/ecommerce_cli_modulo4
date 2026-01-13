package com.ecommerce.ui;

import com.ecommerce.model.Producto;
import com.ecommerce.service.CarritoService;
import com.ecommerce.service.DescuentoService;
import com.ecommerce.service.InventarioService;
import java.util.Scanner;

public class MenuUsuario {

    private final InventarioService inventario;
    private final CarritoService carrito;
    private final Scanner scanner;

    // ESTE CONSTRUCTOR TAMBIÉN FALTABA
    public MenuUsuario(InventarioService inventario, Scanner scanner) {
        this.inventario = inventario;
        this.scanner = scanner;
        DescuentoService ds = new DescuentoService();
        this.carrito = new CarritoService(inventario, ds);
    }

    public void mostrarMenu() {
        int op;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver Productos");
            System.out.println("2. Agregar al Carrito");
            System.out.println("3. Quitar del Carrito");
            System.out.println("4. Ver Mi Carrito");
            System.out.println("5. Ver Descuentos");
            System.out.println("6. Finalizar Compra");
            System.out.println("0. Volver");
            System.out.print("Cliente > ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { op = -1; }

            switch (op) {
                case 1 -> listarProductos();
                case 2 -> agregarCarrito();
                case 3 -> quitarCarrito();
                case 4 -> verCarrito();
                case 5 -> System.out.println("Cupones: CODEMASTER (50%), VIP2026 (20%)");
                case 6 -> finalizarCompra();
                case 0 -> System.out.println("Saliendo de la tienda...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (op != 0);
    }

    private void listarProductos() {
        System.out.println("\n--- Disponibles ---");
        inventario.listarProductos().forEach(p -> 
            System.out.printf("%s ($%.2f) - Stock: %d%n", 
                p.getNombre(), p.getPrecio(), inventario.obtenerStockActual(String.valueOf(p.getId())))
        );
    }

    private void agregarCarrito() {
        System.out.print("ID del producto: ");
        String id = scanner.nextLine();
        Producto p = inventario.buscarProductoPorId(id);
        if (p == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.print("Cantidad: ");
        try {
            int cant = Integer.parseInt(scanner.nextLine());
            carrito.agregarProducto(p, cant);
            System.out.println("Agregado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void quitarCarrito() {
        System.out.print("ID del producto: ");
        String id = scanner.nextLine();
        carrito.removerProducto(id, 1); 
        System.out.println("Se removió 1 unidad.");
    }

    private void verCarrito() {
        System.out.println("\n--- Tu Carrito ---");
        carrito.obtenerItems().forEach(item -> 
            System.out.printf("%s x%d = $%.2f%n", item.getProducto().getNombre(), item.getCantidad(), item.getSubtotal())
        );
        System.out.printf("SUBTOTAL: $%.2f%n", carrito.obtenerTotal());
    }

    private void finalizarCompra() {
        if(carrito.obtenerItems().isEmpty()) {
            System.out.println("Carrito vacío.");
            return;
        }
        System.out.print("¿Tienes cupón? (Enter si no): ");
        String cupon = scanner.nextLine();
        if(!cupon.isEmpty()) carrito.aplicarCupon(cupon);

        System.out.println("\n--- Resumen Final ---");
        System.out.printf("Subtotal: $%.2f%n", carrito.obtenerTotal());
        System.out.printf("Descuento: -$%.2f%n", carrito.obtenerMontoAhorrado());
        System.out.printf("TOTAL A PAGAR: $%.2f%n", carrito.calcularTotalConDescuentos());

        System.out.print("Confirmar pago (s/n): ");
        if(scanner.nextLine().equalsIgnoreCase("s")) {
            try {
                carrito.finalizarCompra();
                System.out.println("¡Compra exitosa! Gracias.");
            } catch(Exception e) {
                System.out.println("Error en la compra: " + e.getMessage());
            }
        }
    }
}