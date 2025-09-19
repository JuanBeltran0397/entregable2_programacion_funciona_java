package com.cooprkc.app;

import com.cooprkc.excepciones.CuentaDuplicadaException;
import com.cooprkc.excepciones.SaldoInsuficienteException;
import com.cooprkc.modelo.*;
import com.cooprkc.transacciones.Deposito;
import com.cooprkc.transacciones.Retiro;

import java.util.*;
import java.util.stream.Collectors;

/**
Cooperativa CoopRKC
 */

/*Main ejecutable del sistema de la cooperativa*/
public class App {

    private static final Scanner sc = new Scanner(System.in);  //Lector de entrada por consola
    private static final Cooperativa coop = new Cooperativa("CoopRKC"); //Instancia a clase Cooperativa

    public static void main(String[] args) {
        int opcion;
        do {
            //Procedemos a crear un menú para controlar nuestro sistema y estas son las acciones de cada número
            mostrarMenu();
            opcion = leerEntero("Seleccione opción: ");
            switch (opcion) {                   // Utilizamos un switche para seleccionar e identificar la opción numérica ingresada
                case 1 -> registrarSocio();     //Llamamos el método para ingresar socios a nuestra cooperativa
                case 2 -> abrirCuenta();        // Método para crear cuentas en el sistema a cada socio
                case 3 -> depositar();          // Método para Agregar valor a la cuenta creada
                case 4 -> retirar();            //Llamamos el método que nos permitirá hacer el descuento del valor retirado al saldo de la cuenta
                case 5 -> aplicarInteres();     //Aplicamos el ínterés agregado al momento de abrir la cuenta
                case 6 -> listarSocios();      //Método pedido en la actividad para mostrar socios existentes en nuestra cooperativa
                case 7 -> reportes();          //En este método mostramos las cuentas con valor mayor a 500.000 y el total del dinero que tiene la cooperativa.
                case 0 -> System.out.println("Saliendo del sistema..."); // Impresión que se muestra al ingresar 0 por consola para salir
                default -> System.out.println("Opción inválida."); //Mensaje de error cuando no se encuentra la opción en el menú
            }
        } while (opcion != 0); //Ciclo mientras para repetir varias veces el menú cuando no se ingresa el número 0
    }
    //Impresión del menú por consola
    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ COOPRKC =====");
        System.out.println("1. Registrar socio");
        System.out.println("2. Abrir cuenta de ahorros");
        System.out.println("3. Depositar");
        System.out.println("4. Retirar");
        System.out.println("5. Aplicar interés");
        System.out.println("6. Listar socios y cuentas");
        System.out.println("7. Reportes");
        System.out.println("0. Salir");
    }
//Método usado en la opción 1 del menú ingresando nombre y cédula del socio por consola
    private static void registrarSocio() {
        String nombre = leerTexto("Nombre del socio: ");
        String cedula = leerTexto("Cédula: ");
        Socio socio = new Socio(nombre, cedula); // Instanciamos la clase socio para alimentar el método constructor de esta clase
        coop.registrarSocio(socio);
        System.out.println("Socio registrado correctamente."); // Mensaje de satisfacción al momento de registrar
    }
    //Método don
    private static void abrirCuenta() {
        Socio socio = buscarSocio();
        if (socio == null) return;
        String numCuenta = leerTexto("Número de cuenta: ");
        double interes = leerDouble("Interés anual (ej: 0.02): ");
        try {
            socio.abrirCuentaAhorros(numCuenta, interes);
            System.out.println("Cuenta abierta.");
        } catch (CuentaDuplicadaException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void depositar() {
        Socio socio = buscarSocio();
        if (socio == null) return;
        String numCuenta = leerTexto("Número de cuenta: ");
        Cuenta cuenta = socio.getCuenta(numCuenta);
        if (cuenta == null) {
            System.out.println("No existe esa cuenta.");
            return;
        }
        double monto = leerDouble("Monto a depositar: ");
        new Deposito(monto).ejecutar(cuenta);
        System.out.println("Depósito realizado. Nuevo saldo: " + String.format("%,.2f", cuenta.getSaldo()));
    }

    private static void retirar() {
        Socio socio = buscarSocio();
        if (socio == null) return;
        String numCuenta = leerTexto("Número de cuenta: ");
        Cuenta cuenta = socio.getCuenta(numCuenta);
        if (cuenta == null) {
            System.out.println("No existe esa cuenta.");
            return;
        }
        double monto = leerDouble("Monto a retirar: ");
        try {
            new Retiro(monto).ejecutar(cuenta);
            System.out.println("Retiro realizado. Nuevo saldo: " + String.format("%,.2f", cuenta.getSaldo()));
        } catch (SaldoInsuficienteException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // Método aplicarInteres()
    // Usamos forEach porque es una forma moderna y compacta de recorrer colecciones o flujos.
    // - Primero recorremos todos los socios de la cooperativa.
    // - Por cada socio, recorremos sus cuentas.
    // - Usamos pattern matching (c instanceof CuentaAhorros ca) para identificar solo las cuentas de ahorro.
    // - A esas cuentas les aplicamos el método aplicarInteres().

    private static void aplicarInteres() {
        coop.getSocios().forEach(s ->
                s.getCuentas().forEach(c -> {
                    if (c instanceof CuentaAhorros ca) {
                        ca.aplicarInteres();
                    }
                })
        );
        System.out.println("Interés aplicado a todas las cuentas de ahorro.");
    }

    private static void listarSocios() {
        System.out.println("\n*** SOCIOS Y CUENTAS ***");
        coop.getSocios().forEach(s -> {
            System.out.println("* " + s.getNombre() + " (" + s.getCedula() + ")");
            s.getCuentas().forEach(c ->
                    System.out.println("   - " + c)
            );
        });
    }

    //Método de aplicación directa de programación funcional con Streams
    private static void reportes() {
        System.out.println("\n***** REPORTES *****");

        // 1) Cuentas con saldo > 500.000
        var ricas = coop.getSocios().stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(c -> c.getSaldo() > 500_000)
                .sorted(Comparator.comparingDouble(Cuenta::getSaldo).reversed())
                .collect(Collectors.toList());
        System.out.println("Cuentas con saldo > 500.000:");
        ricas.forEach(c ->
                System.out.println(" " + c.getNumeroCuenta() + " -> $" + String.format("%,.2f", c.getSaldo()))
        );

        // 2) Total en la cooperativa
        double total = coop.getSocios().stream()
                .flatMap(s -> s.getCuentas().stream())
                .map(Cuenta::getSaldo)
                .reduce(0.0, Double::sum);
        System.out.println("Total en la cooperativa: $" + String.format("%,.2f", total));
    }

    // =================== utilidades ===================

    private static Socio buscarSocio() {
        String cedula = leerTexto("Ingrese cédula del socio: ");
        return coop.getSocios().stream()
                .filter(s -> s.getCedula().equals(cedula))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Socio no encontrado.");
                    return null;
                });
    }

    private static String leerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    private static int leerEntero(String msg) {
        System.out.print(msg);
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private static double leerDouble(String msg) {
        System.out.print(msg);
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.print("Ingrese un número decimal válido: ");
            }
        }
    }
}
