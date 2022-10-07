package com.prajwal.tryaop.controller;


import com.prajwal.tryaop.model.AModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {


    @Autowired
    AModel aModel;



    @GetMapping("/A")
    public int getA(){
        return aModel.getA();
    }
    @GetMapping("/B")
    public int getB(){
        return aModel.getB();
    }

    @GetMapping("/C")
    public String getC(){
        return aModel.getC();
    }

    @GetMapping("/D")
    public double getD(){
        return aModel.getD();
    }

    @GetMapping("/E")
    public boolean getE()
    {
        return aModel.isE();
    }

    @GetMapping("/F")
    public String getString(){
        return aModel.getString("prajwal");
    }


    @GetMapping("/G")
    public boolean getG(){
        return aModel.isG();
    }


}
