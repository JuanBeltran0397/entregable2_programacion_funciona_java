package com.cooprkc.modelo;

import java.util.Objects;

/**
 * Clase Cuenta.
 * Contiene número de cuenta y saldo, con operaciones genéricas.
 * Pensada para heredar como ejemplo en nuestra clase CuentaAhorros).
 */
public class Cuenta {

    //Aplicación del encapsulamiento con los atributos privados en las clases
    private final String numeroCuenta;
    protected double saldo;  //La misma clase, Cualquier clase del mismo paquete y Cualquier subclase (hija), aunque esté en otro paquete.

    //Método constructor
    public Cuenta(String numeroCuenta) {
        this.numeroCuenta = Objects.requireNonNull(numeroCuenta, "numeroCuenta");
        this.saldo = 0.0;
    }
    //Getter
    public String getNumeroCuenta() {

        return numeroCuenta;
    }
    //Getter
    public double getSaldo() {

        return saldo;
    }

    /** Depósito genérico (validación básica). */
    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El depósito debe ser positivo.");
        saldo += monto;
    }

    //Retiro genérico sin validar saldo
    public void retirar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El retiro debe ser positivo.");
        saldo -= monto;
    }

    @Override  //Método usado en la clase hija CuentaAhorros el cuál sobrescribe al retornar
    public String toString() {
        return "Cuenta{" +
                "numero='" + numeroCuenta +
                ", saldo= " + String.format("%,.2f", saldo) +  // Le damos este formato al valor por consola 150,000.00
                '}';
    }
}
