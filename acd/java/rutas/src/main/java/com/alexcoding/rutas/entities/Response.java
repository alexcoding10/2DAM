package com.alexcoding.rutas.entities;

public abstract class Response {

    public enum Status {
        ok(200), fail(500);

        private final int statusCode;

        // Constructor del enum
        Status(int statusCode) {
            this.statusCode = statusCode;
        }

        // Métodos getter para acceder al código de estado
        public int getStatusCode() {
            return statusCode;
        }

    }

    protected Status status;


    public Response(Status status) {
        this.status = status;
    }


    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public abstract Object getData();
    public abstract void setData(Object data);
}
