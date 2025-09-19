package com.cooprkc.transacciones;

import com.cooprkc.excepciones.SaldoInsuficienteException;
import com.cooprkc.modelo.Cuenta;

//Interfaz Transaccion

public interface Transaccion {
    //Método ejecutar validando que tenga saldo suficiente
    void ejecutar(Cuenta cuenta) throws SaldoInsuficienteException;
    double getMonto();  //Método que no recibe parámetros sino que devuelve monto del saldo
}
