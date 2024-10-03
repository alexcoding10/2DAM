package com.alexcoding.rutas.entities;

public class ResponseMsg extends Response {
    private String msg;


    public ResponseMsg(){};
    public ResponseMsg(Status status, String msg) {
        super(status);
        this.msg = msg;
    }


    @Override
    public Object getData() {
        return msg;
    }

    @Override
    public void setData(Object msg) {
        this.msg = (String) msg;
    }
    
}
