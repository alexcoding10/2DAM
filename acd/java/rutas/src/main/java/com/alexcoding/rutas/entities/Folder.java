package com.alexcoding.rutas.entities;

import java.util.List;

public class Folder extends Fichero {
    protected List<String> content;

    // constructor
    public Folder(String name, List<String> content) {
        super(name, TypeFichero.FOLDER);
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                " content='" + getContent() + "'" +
                "}";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setContent(Object content) {
        // chequeo que content sea una instancia de List
        if (content instanceof List) {
            this.content = (List<String>) content;

        } else {
            throw new IllegalArgumentException("El contenido de un FOLDER debe ser String[]");
        }
    }

    @Override
    public Object getContent() {
        return this.content;
    }

}
