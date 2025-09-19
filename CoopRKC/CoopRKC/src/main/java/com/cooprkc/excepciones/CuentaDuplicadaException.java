package com.cooprkc.excepciones;

/** Excepción para impedir abrir cuentas con número repetido por socio. */
public class CuentaDuplicadaException extends Exception {
    public CuentaDuplicadaException(String message) {
        super(message);
    }
}
