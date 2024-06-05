package com.example.relational_data_access;

public class Customer {
    private long id;
    private String firstName, lastName;
    
    public Customer(final long id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", this.id
        , this.firstName, this.lastName);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
}
