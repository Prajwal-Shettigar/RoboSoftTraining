package com.prajwal.manytomany.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/*
{
"address":"address1"
}
 */

@Entity
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private String address;

    @ManyToMany(mappedBy = "address",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Set<Customer> customers;

    public Address() {
    }

    public Address(int addressId, String address) {
        this.addressId = addressId;
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }


    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", address='" + address + '\'' +
                ", customers=" + customers +
                '}';
    }
}
