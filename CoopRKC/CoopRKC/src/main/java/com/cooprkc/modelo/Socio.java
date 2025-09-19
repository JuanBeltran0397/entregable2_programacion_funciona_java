package com.cooprkc.modelo;

import com.cooprkc.excepciones.CuentaDuplicadaException;

import java.util.*;


// Socio de la cooperativa Mantiene una lista de cuentas de ahorro.
//Clase socio
public class Socio {

    //Aplicación del encapsulamiento con los atributos privados en las clases
    private final String nombre; //Atributo privado 1 solo se usa dentro de la clase
    private final String cedula; //Atributo privado 2 solo se usa dentro de la clase
    private final Map<String, Cuenta> cuentas = new LinkedHashMap<>(); // numeroCuenta -> Cuenta

    public Socio(String nombre, String cedula) {
        //Asignación del valor agregado en los atributos de la clase socio
        this.nombre = Objects.requireNonNull(nombre, "nombre");
        this.cedula = Objects.requireNonNull(cedula, "cedula");
    }

    public String getNombre() {

        return nombre;
    }

    public String getCedula() {

        return cedula;
    }
    //Lista para agregar cuentas del socio
    public List<Cuenta> getCuentas() {

        return new ArrayList<>(cuentas.values());
    }

    public Cuenta getCuenta(String numeroCuenta) {

        return cuentas.get(numeroCuenta);
    }

    // Abre una cuenta de ahorros validando que el número no esté repetido para el socio.
    public CuentaAhorros abrirCuentaAhorros(String numeroCuenta, double interesAnual) throws CuentaDuplicadaException {
        if (cuentas.containsKey(numeroCuenta)) {   //Condicional para validar cuenta duplicada
            throw new CuentaDuplicadaException("El socio " + nombre + " ya tiene la cuenta " + numeroCuenta);  // Mensaje de excepción cuando se cumple dato duplicado
        }
        CuentaAhorros nueva = new CuentaAhorros(numeroCuenta, interesAnual); // Cuando todo esta bien en la validación se instancia la clase cuenta ahorros para abrir cuenta
        cuentas.put(numeroCuenta, nueva); //Almacenamos la cuenta en el mapa creado en esta clase de socio línea 14
        return nueva;
    }
}
