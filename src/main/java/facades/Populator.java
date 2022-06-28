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
        Car c = new Car("Fararri");
        Car c2 = new Car("Lamborghini");
        Car c3 = new Car("Bentley");
        Race r = new Race("Testing");
        Race r2 = new Race("Firts Round ");
        Race r3 = new Race("Secound Round");
        Driver d = new Driver("Lando Norris",1997, "Pro", "Male");

        c.setDriver(d);
        c2.setDriver(d);
        c3.setDriver(d);

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
            populate();
              // populateUsers();

    }
}
