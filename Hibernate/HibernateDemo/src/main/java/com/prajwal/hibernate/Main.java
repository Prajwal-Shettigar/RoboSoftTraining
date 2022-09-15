package com.prajwal.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Main {

    public static void main(String[] args) {


        //setting lap variables
        Laptop laptop1 = new Laptop();
        laptop1.setLapId(101);
        laptop1.setBrand("dell");
        Laptop laptop2 = new Laptop();
        laptop2.setLapId(102);
        laptop2.setBrand("hp");


        //setting name variables
        Name name = new Name();
        name.setfName("prajwal");
        name.setmName("__");
        name.setlName("Shettigar");

        //setting student variables
        Student student1 = new Student();
        student1.setId(1);
        student1.setName(name);
        student1.setAddress("udupi");
        Student student2 = new Student();
        student2.setId(2);
        student2.setName(name);
        student2.setAddress("mangalore");


        student1.getLaptops().add(laptop1);
        student1.getLaptops().add(laptop2);

        laptop1.getStudents().add(student1);
        laptop2.getStudents().add(student2);

        Configuration configuration = new Configuration().configure("hibernate.config.xml").addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();


        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);


        Session session = sessionFactory.openSession();


        Transaction transaction = session.beginTransaction();

        session.save(laptop1);
        session.save(laptop2);
        session.save(student1);
        session.save(student2);




        transaction.commit();

        System.out.println("student 1 data:");
        System.out.println((Student)session.get(Student.class, student1.getId()));
        System.out.println("student 2 data:");
        System.out.println((Student)session.get(Student.class, student2.getId()));

        System.out.println("Laptop 1 data :");
        System.out.println((Laptop)session.get(Laptop.class,laptop1.getLapId()));
        System.out.println("Laptop 2 data :");
        System.out.println((Laptop)session.get(Laptop.class,laptop2.getLapId()));

//        session.close();
    }
}
