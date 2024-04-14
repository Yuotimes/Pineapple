package com.example.pineapple.ticketbeans;

import java.io.Serializable;

public class FlightInfo implements Serializable {
    private String name;
    private boolean select;

    public FlightInfo(String name, boolean select) {
        this.name = name;
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
