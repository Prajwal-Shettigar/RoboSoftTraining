package com.prajwal.onetomany.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/*

{
    "name":"prajwal",
    "products":[{
        "productName":"mobile"
    }]
}

*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {


    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "cust_id",referencedColumnName = "id")
    private List<Product> products;
}
