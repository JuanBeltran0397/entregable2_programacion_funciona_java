package com.cooprkc.transacciones;

import com.cooprkc.modelo.Cuenta;

/** Depósito: suma saldo. */
public class Deposito implements Transaccion {  //Implementación de la interfaz Transacción con la devolución del método ejecutar
    private final double monto;

    //Método constructor para agregar el monto a la cuenta nueva agregada
    public Deposito(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El depósito debe ser positivo.");  //condicional de números positivos
        this.monto = monto; // Asignación del valor al atributo privado en esta clase
    }

    @Override   // indicamos que sobrescribimos el método de la interface que es pública
    public void ejecutar(Cuenta cuenta) {
        cuenta.depositar(monto);
    }

    @Override  // indicamos que sobrescribimos el método de la interface que es pública
    public double getMonto() {
        return monto;
    }
}
