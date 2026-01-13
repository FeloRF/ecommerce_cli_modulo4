package com.ecommerce.ui;

import com.ecommerce.model.Producto;
import com.ecommerce.service.InventarioService;
import java.util.Scanner;

public class MenuAdmin {

    private final InventarioService inventario;
    private final Scanner scanner;

    // ESTE ES EL CONSTRUCTOR QUE FALTA Y CAUSA EL ERROR
    public MenuAdmin(InventarioService inventario, Scanner scanner) {
        this.inventario = inventario;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int op;
        do {
            System.out.println("\n--- PANEL ADMINISTRADOR ---");
            System.out.println("1. Listar Productos");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Crear Producto");
            System.out.println("4. Editar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Admin > ");
            
            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { op = -1; }

            switch (op) {
                case 1 -> listarProductos();
                case 2 -> buscarProductos();
                case 3 -> crearProducto();
                case 4 -> editarProducto();
                case 5 -> eliminarProducto();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (op != 0);
    }

    private void listarProductos() {
        System.out.println("\n--- Catálogo Actual ---");
        var lista = inventario.listarProductos();
        if(lista.isEmpty()) System.out.println("No hay productos.");
        
        for (Producto p : lista) {
            int stock = inventario.obtenerStockActual(String.valueOf(p.getId()));
            System.out.printf("ID: %d | %s | %s | $%.2f | Stock: %d%n",
                    p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), stock);
        }
    }

    private void buscarProductos() {
        System.out.print("Buscar por nombre o categoría: ");
        String busqueda = scanner.nextLine();
        inventario.buscarPorNombreOCategoria(busqueda)
                  .forEach(p -> System.out.println(p.getNombre() + " - " + p.getPrecio()));
    }

    private void crearProducto() {
        try {
            System.out.print("ID Único: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Categoría: ");
            String cat = scanner.nextLine();
            System.out.print("Precio: ");
            double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock Inicial: ");
            int stock = Integer.parseInt(scanner.nextLine());

            Producto p = new Producto(id, nombre, cat, precio);
            inventario.guardarProducto(p, stock);
            System.out.println("Producto guardado.");
        } catch (Exception e) {
            System.out.println("Error: Datos inválidos. " + e.getMessage());
        }
    }

    private void editarProducto() {
        System.out.print("ID del producto a editar: ");
        String id = scanner.nextLine();
        Producto p = inventario.buscarProductoPorId(id);
        
        if(p == null) {
            System.out.println("No encontrado."); 
            return;
        }

        System.out.println("Editando: " + p.getNombre());
        System.out.print("Nuevo Nombre (Enter para saltar): ");
        String nombre = scanner.nextLine();
        if(!nombre.isEmpty()) p.setNombre(nombre);

        System.out.print("Nuevo Precio (Enter o -1 para saltar): ");
        String precioStr = scanner.nextLine();
        if(!precioStr.isEmpty()) {
            double precio = Double.parseDouble(precioStr);
            if(precio > 0) p.setPrecio(precio);
        }
        System.out.println("Actualizado.");
    }

    private void eliminarProducto() {
        System.out.print("ID a eliminar: ");
        String id = scanner.nextLine();
        System.out.print("Confirma eliminar (s/n): ");
        if(scanner.nextLine().equalsIgnoreCase("s")) {
            inventario.eliminarProducto(id);
            System.out.println("Producto eliminado.");
        }
    }
}