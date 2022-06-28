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
        ManySide ms = new ManySide("first many side");
        ManySide ms2 = new ManySide("second many side");
        ManySide ms3 = new ManySide("third many side");
        OtherManySide oms = new OtherManySide("first other many side");
        OtherManySide oms2 = new OtherManySide("second other many side");
        OtherManySide oms3 = new OtherManySide("third other many side");
        OneSide os = new OneSide("one side");
        OtherOneSide oos = new OtherOneSide("other one side");

        ms.setOneSide(os);
        ms2.setOneSide(os);
        ms3.setOneSide(os);

        ms.addToOtherManySides(oms);
        ms.addToOtherManySides(oms2);
        ms2.addToOtherManySides(oms2);
        ms2.addToOtherManySides(oms3);
        ms3.addToOtherManySides(oms3);
        ms3.addToOtherManySides(oms);

        os.setOtherOneSide(oos);

        em.persist(ms);
        em.persist(ms2);
        em.persist(ms3);

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
        //     populate();
               populateUsers();

    }
}
