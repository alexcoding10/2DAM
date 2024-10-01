package com.alexcoding.rutas.entities;

public abstract class Fichero {
    // enum para el tipo de fichero que sea
    protected enum TypeFichero {
        FOLDER, FILE
    }

    protected String name;
    protected TypeFichero type;

    // constructor
    public Fichero(String name, TypeFichero type) {
        this.name = name;
        this.type = type;
    }

    // get and set
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeFichero getType() {
        return this.type;
    }

    public void setType(TypeFichero type) {
        this.type = type;
    }

    public Fichero name(String name) {
        setName(name);
        return this;
    }

    public Fichero type(TypeFichero type) {
        setType(type);
        return this;
    }

    // metodos abstractos para acceder al contenido dependiendo si es Folder o File u alguna respuesta en el futuro
    public abstract Object getContent();

    public abstract void setContent(Object content);

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", type='" + getType() + "'" +
                "}";
    }

}
