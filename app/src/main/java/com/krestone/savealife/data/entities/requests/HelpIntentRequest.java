package com.krestone.savealife.data.entities.requests;


public class HelpIntentRequest {

    private String number;

    public HelpIntentRequest() { }

    public HelpIntentRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
