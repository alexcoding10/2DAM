package com.alexcoding.ficheros.Entities;

public class Jugador {
    protected String name;
    protected int edad;

    public Jugador(String name, int edad){
        this.name = name;
        this.edad = edad;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
}
