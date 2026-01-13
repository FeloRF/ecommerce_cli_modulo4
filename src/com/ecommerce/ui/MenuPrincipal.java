package com.ecommerce.ui;

import com.ecommerce.model.Producto;
import com.ecommerce.service.InventarioService;
import java.util.Scanner;

public class MenuPrincipal {

    private final InventarioService inventarioService;
    private final Scanner scanner;

    public MenuPrincipal() {
        this.inventarioService = new InventarioService();
        this.scanner = new Scanner(System.in);
        cargarDatosIniciales(); 
    }

    // --- ESTE ES EL MÉTODO QUE TE FALTA O NO SE VE ---
    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=== E-COMMERCE: MENÚ PRINCIPAL ===");
            System.out.println("1. Ingresar como ADMINISTRADOR");
            System.out.println("2. Ingresar como USUARIO");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    new MenuAdmin(inventarioService, scanner).mostrarMenu();
                    break;
                case 2:
                    new MenuUsuario(inventarioService, scanner).mostrarMenu();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar nuestro sistema!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        
        scanner.close(); 
    }
    // ------------------------------------------------

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void cargarDatosIniciales() {
        try {
            inventarioService.guardarProducto(new Producto(1, "Laptop Gamer", "Electrónica", 1500.0), 10);
            inventarioService.guardarProducto(new Producto(2, "Mouse RGB", "Accesorios", 25.0), 50);
            inventarioService.guardarProducto(new Producto(3, "Teclado Mecánico", "Accesorios", 80.0), 20);
        } catch (Exception e) { }
    }
}