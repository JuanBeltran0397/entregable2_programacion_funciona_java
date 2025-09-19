package com.cooprkc.modelo;

//Clase publica Cuenta de ahorros llamada en la clase socio para agregar cuentas y asignar interés agregado
public class CuentaAhorros extends Cuenta { //En esta clase heredamos los atributos de la clase cuenta

    //Aplicación del encapsulamiento con los atributos privados en las clases
    private double interesAnual;   //Tiene un atributo propio que en este caso es el interés anual para aplicar

    //Método constructor con validación de datos negativos en la condicional
    public CuentaAhorros(String numeroCuenta, double interesAnual) {
        super(numeroCuenta);
        if (interesAnual < 0) throw new IllegalArgumentException("El interés no puede ser negativo.");// Mensaje de excepción cuando se cumple la condición no aceptada
        this.interesAnual = interesAnual;
    }
    //Getter
    public double getInteresAnual() {

        return interesAnual;
    }
    //Setter
    public void setInteresAnual(double interesAnual) {
        if (interesAnual < 0) throw new IllegalArgumentException("El interés no puede ser negativo.");
        this.interesAnual = interesAnual;
    }

    // Aplicamos interés simple sobre el saldo actual de la cuenta del socio.
    public void aplicarInteres() {
        this.saldo += this.saldo * this.interesAnual;
    }

    @Override   // indicamos que sobrescribimos el método de la clase padre (Cuenta)
    public String toString() {
        return "CuentaAhorros{" +
                "numero='" + getNumeroCuenta() +
                ", saldo=" + String.format("%,.2f", getSaldo()) +  // Le damos este formato al valor por consola 150,000.00
                ", interesAnual= " + (interesAnual * 100) + "%}";
    }
}
