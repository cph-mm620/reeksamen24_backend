/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM User u").executeUpdate();
        em.createQuery("DELETE FROM Role r").executeUpdate();
        em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
        em.createNativeQuery("ALTER TABLE Driver AUTO_INCREMENT = 1").executeUpdate();
        em.createNamedQuery("Race.deleteAllRows").executeUpdate();
        em.createNativeQuery("ALTER TABLE Race AUTO_INCREMENT = 1").executeUpdate();
        em.createNamedQuery("Car.deleteAllRows").executeUpdate();
        em.createNativeQuery("ALTER TABLE Car AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        Car c = new Car("DCT F8 Tributo","Farrari","Red", "Farrari",2021);
        Car c2 = new Car("Aventador LP","Lamborghini","Black", "Redbull", 2021);
        Car c3 = new Car("GT3","Bentley","White", "n/a", 2021);
        Race r = new Race("Testing","Spain","28-05-22","10 min");
        Race r2 = new Race("First Round", "Spain", "28-05-22", "5 min");
        Race r3 = new Race("Secound Round", "Spain", "28-05-22", "15 min");
        Driver d = new Driver("Lando Norris",1999, "Pro", "Male");
        Driver d1 = new Driver("Pierre Gasly",1996, "Pro", "Male");

        c.addToDriver(d);
        c2.addToDriver(d);
        c3.addToDriver(d1);


        c.addToRaces(r);
        c.addToRaces(r2);
        c2.addToRaces(r2);
        c2.addToRaces(r3);
        c3.addToRaces(r3);
        c3.addToRaces(r);



        em.persist(c);
        em.persist(c2);
        em.persist(c3);

        em.getTransaction().commit();
    }

    public static void populateUsers(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");

        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);

        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.getTransaction().commit();
    }

    public static void main(String[] args) {
        //  populate();
             populateUsers();

    }
}
