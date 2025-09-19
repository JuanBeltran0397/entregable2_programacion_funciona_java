package com.cooprkc.transacciones;

import com.cooprkc.excepciones.SaldoInsuficienteException;
import com.cooprkc.modelo.Cuenta;

// Retiro, resta saldo con control de insuficiencia.
public class Retiro implements Transaccion {    //Implementación de la interfaz Transacción con la devolución del método ejecutar
    private final double monto;

    public Retiro(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El retiro debe ser positivo."); // Excepción de mensaje error datos positivos
        this.monto = monto;
    }

    @Override  // indicamos que sobrescribimos el método de la interface que es pública
    public void ejecutar(Cuenta cuenta) throws SaldoInsuficienteException { //Se insta a la clase Cuenta
        if (cuenta.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para retirar $" + String.format("%,.2f", monto)); // Mensaje y Le damos este formato al valor por consola 150,000.00
        }
        cuenta.retirar(monto);
    }

    @Override   // indicamos que sobrescribimos el método de la interface que es pública
    public double getMonto() {
        return monto;
    }
}
