package com.alexcoding.rutas.entities;

public class Mifile extends Fichero {
    protected String content;

    public Mifile(String name, String content) {
        super(name, TypeFichero.FILE);
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(Object content) {
        if (content instanceof String) {
            this.content = (String) content;
        } else {
            throw new IllegalArgumentException("El contenido debe ser un String para los archivos.");
        }
    }

}
