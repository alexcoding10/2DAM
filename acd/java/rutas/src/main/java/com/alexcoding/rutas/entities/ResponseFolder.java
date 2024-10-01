package com.alexcoding.rutas.entities;

import java.util.List;

public class ResponseFolder extends Response {
    
    protected List<Fichero> data;
    
    public ResponseFolder(Status status , List<Fichero> data) {
        super(status);
        this.data = data;
    }
    @Override
    public Object getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(Object data) {
        if (data instanceof Fichero) {
            this.data = (List<Fichero>) data;
            
        }else{
            throw new IllegalArgumentException("El dato no es una instancia de Fichero");
        }
    }
    
}
