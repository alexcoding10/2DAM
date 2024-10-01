package com.alexcoding.rutas.entities;

public class ResponseFile  extends Response {
    protected String data;


    public ResponseFile(Status status, String data) {
        super(status);
        this.data = data;
    }


    public String getData() {
        return this.data;
    }


    public ResponseFile data(String data) {
        setData(data);
        return this;
    }


    @Override
    public void setData(Object data) {
        this.data = (String) data;
    }

    

}
