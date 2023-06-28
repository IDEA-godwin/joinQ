package com.example.joinq.domain.enumeration;

public enum QueueStatusConstant {

    OPEN("open"),
    CLOSED("closed"),
    WAITING("waiting"),
    SERVING("serving");


    public final String value;
    QueueStatusConstant(String status) {
        this.value = status;
    }

    public String getValue() {
        return this.value;
    }
}
