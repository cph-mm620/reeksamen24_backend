package facades;

import dtos.CarDTO;
import dtos.DriverDTO;
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
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Car AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Driver AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE Race AUTO_INCREMENT = 1").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            User user = new User("user", "test123");
            User admin = new User("admin", "test123");
            User both = new User("user_admin", "test123");

            if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
                throw new UnsupportedOperationException("You have not changed the passwords");

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            Car c = new Car("Volvo");
            Car c2 = new Car("BMW");
            Car c3 = new Car("Ford");
            Race r = new Race("Testing");
            Race r2 = new Race("Round 1");
            Race r3 = new Race("Round 2");
            Driver d = new Driver("Max Verstappen");
            Driver d2 = new Driver("Sergio Perez");
            Driver d3 = new Driver("Louis Hamilton");
    //        OtherOneSide oos = new OtherOneSide("other one side");

            c.addToDriver(d2);
            c2.addToDriver(d);
            c3.addToDriver(d3);
            c.addToRaces(r);
            c.addToRaces(r2);
            c2.addToRaces(r2);
            c2.addToRaces(r3);
            c3.addToRaces(r3);
            c3.addToRaces(r);
          //  os.setOtherOneSide(oos);

            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(c);
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        Car c = new Car("create added this");
        Race r = new Race("create added this race");
        Race r2 = new Race("create added this race nr 2");
        c.addToRaces(r);
        c.addToRaces(r2);
        Driver d = new Driver("create added this driver");
        c.addToDriver(d);

        String actual = facade.create(c).getName();
        String expected = "create added this";
        assertEquals(actual, expected);
    }

    @Test
    void readWhere() {
        List<DriverDTO> ddto = facade.readWhere();
        for (DriverDTO cd: ddto) {
            System.out.println(cd);
        }
        int actual = ddto.size();
        int expected = manySideSize;
        assertEquals(actual, expected);

    }

    @Test
    void read() {
        List<CarDTO> cdto = facade.read();
        for (CarDTO cd: cdto) {
            System.out.println(cd);
        }
        int actual = cdto.size();
        int expected = manySideSize;
        assertEquals(actual, expected);
    }

    @Test
    void update() {
        Car c = new Car("update changed this");
        c.setId(1);
        Race r = new Race("first updated race");
        Race r2 = new Race("second updated race");
        Driver d = new Driver("Driver updated");

        c.addToDriver(d);
        c.addToRaces(r);
        c.addToRaces(r2);
        System.out.println("c as in car c");
        System.out.println(c);

        String actual = facade.update(c).getName();
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
        CarDTO cdto = facade.getById(3);
        String actual = cdto.getName();
        String expected = "Volvo";
        assertEquals(expected, actual);
    }

    @Test
    void getByDriverId() {
        DriverDTO ddto = facade.getByDriverId(3);
        String actual = ddto.getName();
        String expected = "Sergio Perez";
        assertEquals(expected, actual);
    }
}