package facades;

import dtos.ManySideDTO;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {

    private static Facade facade;
    private static EntityManagerFactory emf;
    private static int manySideSize;

    //Private Constructor to ensure Singleton
    private FacadeTest() {}

    @BeforeAll
    public static void setUpClass() {
        System.out.println("set up class");
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = Facade.getFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
        manySideSize = 3;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User u").executeUpdate();
            em.createQuery("DELETE FROM Role r").executeUpdate();
            em.createNamedQuery("ManySide.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE ManySide AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("OneSide.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE OneSide AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("OtherManySide.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE OtherManySide AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("OtherOneSide.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE OtherOneSide AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            User user = new User("user", "test123");
            User admin = new User("admin", "test123");
            User both = new User("user_admin", "test123");

            if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
                throw new UnsupportedOperationException("You have not changed the passwords");

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            ManySide ms = new ManySide("first many side");
            ManySide ms2 = new ManySide("second many side");
            ManySide ms3 = new ManySide("third many side");
            OtherManySide oms = new OtherManySide("first other many side");
            OtherManySide oms2 = new OtherManySide("second other many side");
            OtherManySide oms3 = new OtherManySide("third other many side");
            OneSide os = new OneSide("one side");
            OneSide os2 = new OneSide("another one side");
            OtherOneSide oos = new OtherOneSide("other one side");

            ms.setOneSide(os2);
            ms2.setOneSide(os);
            ms3.setOneSide(os);
            ms.addToOtherManySides(oms);
            ms.addToOtherManySides(oms2);
            ms2.addToOtherManySides(oms2);
            ms2.addToOtherManySides(oms3);
            ms3.addToOtherManySides(oms3);
            ms3.addToOtherManySides(oms);
            os.setOtherOneSide(oos);

            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(ms);
            em.persist(ms2);
            em.persist(ms3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        ManySide ms = new ManySide("create added this");
        OtherManySide oms = new OtherManySide("create added this oms");
        OtherManySide oms2 = new OtherManySide("create added this oms2");
        ms.addToOtherManySides(oms);
        ms.addToOtherManySides(oms2);
        OneSide os = new OneSide("create added this os");
        ms.setOneSide(os);

        String actual = facade.create(ms).getName();
        String expected = "create added this";
        assertEquals(actual, expected);
    }

    @Test
    void readWhere() {
        List<ManySideDTO> msdto = facade.readWhere("another one side");
        for (ManySideDTO msd: msdto) {
            System.out.println(msd);
        }
        int actual = msdto.size();
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test
    void read() {
        List<ManySideDTO> msdto = facade.read();
        for (ManySideDTO msd: msdto) {
            System.out.println(msd);
        }
        int actual = msdto.size();
        int expected = manySideSize;
        assertEquals(actual, expected);
    }

    @Test
    void update() {
        ManySide ms = new ManySide("update changed this");
        ms.setId(1);
        OtherManySide oms = new OtherManySide("first other many side");
        OtherManySide oms2 = new OtherManySide("second other many side");
        OneSide os = new OneSide("one side");

        ms.setOneSide(os);
        ms.addToOtherManySides(oms);
        ms.addToOtherManySides(oms2);
        System.out.println("ms");
        System.out.println(ms);

        String actual = facade.update(ms).getName();
        String expected = "update changed this";
        assertEquals(actual, expected);
    }

    @Test
    void delete() {
        facade.delete(2);
        int actual = facade.read().size();
        int expected = manySideSize - 1;
        assertEquals(expected, actual);
    }

    @Test
    void getById() {
        ManySideDTO msdto = facade.getById(3);
        String actual = msdto.getName();
        String expected = "third many side";
        assertEquals(expected, actual);
    }
}