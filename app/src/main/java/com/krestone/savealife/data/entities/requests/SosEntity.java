package com.krestone.savealife.data.entities.requests;



public class SosEntity {

    private String message = "";

    private Double area;

    public SosEntity(String message, Double area) {
        this.message = message;
        this.area = area;
    }

    public SosEntity() { }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
