package com.cooprkc.excepciones;

/** Excepción de dominio para retiros con saldo insuficiente. */
public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
