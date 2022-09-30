package com.prajwal.onetomany.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/*

{
    "id":1
    "name":"prajwal",
    "products":[{
        "productName":"mobile"
        "customer_id":1
    }]
}

*/


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {


    @Id
    private Integer id;
    private String name;

    @OneToMany(targetEntity = Product.class,mappedBy = "customer_id",cascade = CascadeType.ALL)
    private List<Product> products;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
