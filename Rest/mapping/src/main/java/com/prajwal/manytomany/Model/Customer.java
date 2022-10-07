package com.prajwal.manytomany.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

/*
{
"name":"customer1",
"address":[{
    "address":"address1"
},{
    "address":"address2"
},{
    "address":"address3"
},{
    "address":"address4"
},{
    "address":"address5"
}]
}
 */

@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custId;

    private String name;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name="customer_address_table"
            , joinColumns = {@JoinColumn(name="customer_id",referencedColumnName = "custId")}
            ,inverseJoinColumns ={@JoinColumn(name = "address_id",referencedColumnName = "addressId")} )
    @JsonManagedReference
    @JsonIgnore
    private Set<Address> address;

    public Customer() {
    }

    public Customer(int custId, String name) {
        this.custId = custId;
        this.name = name;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }
}
