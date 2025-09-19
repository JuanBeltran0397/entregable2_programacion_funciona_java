package com.cooprkc.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Clase Cooperativa para administrar  el registro de socios en una lista
 */
public class Cooperativa {

    //Aplicación del encapsulamiento con los atributos privados en las clases
    private final String nombre;
    private final List<Socio> socios = new ArrayList<>(); //Creación de la lista

    public Cooperativa(String nombre) {

        this.nombre = Objects.requireNonNull(nombre, "nombre");
    }

    public String getNombre() {

        return nombre;
    }

    //Método constructor para agregar socios válidos
    public void registrarSocio(Socio socio) {
        socios.add(Objects.requireNonNull(socio));
    }

    public List<Socio> getSocios() {
        return Collections.unmodifiableList(socios);  //Devolver la lista de socios sin permitir que alguien la modifique desde afuera
    }
}
