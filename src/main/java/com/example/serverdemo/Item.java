package com.example.serverdemo;


public class Item {
    private String itemNumber;
    private String procedureCode;

    public Item(String itemNumber, String procedureCode) {
        this.itemNumber = itemNumber;
        this.procedureCode = procedureCode;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }
}
