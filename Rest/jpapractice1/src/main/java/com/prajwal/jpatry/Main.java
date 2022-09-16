package com.prajwal.jpatry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");


         EntityManager entityManager = entityManagerFactory.createEntityManager();


         MyEmployee myEmployee = new MyEmployee(101,"prajwal");



         entityManager.getTransaction().begin();

         entityManager.persist(myEmployee);

         entityManager.getTransaction().commit();
    }
}
