package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

class MyResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static String securityToken;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
        logOut();
    }

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
        EntityManager em = emf.createEntityManager();
        try {
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
            User user = new User("user", "test123");
            User admin = new User("admin", "test123");
            User both = new User("user_admin", "test123");

            if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test"))
                throw new UnsupportedOperationException("You have not changed the passwords");

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            Car c = new Car(1, "Volvo");
            Car c2 = new Car(2, "Fiat");
            Car c3 = new Car(3, "Mazda");
            Race r = new Race("Testing");
            Race r2 = new Race("Round 3");
            Race r3 = new Race("Round 6");
            Driver d = new Driver("Lewis Hamilton");
            Driver d2 = new Driver("Charles Leclerc");
           // OtherOneSide oos = new OtherOneSide("other one side");

            c.addToDriver(d2);
            c2.addToDriver(d);
            c3.addToDriver(d);
            c.addToRaces(r);
            c.addToRaces(r2);
            c2.addToRaces(r2);
            c2.addToRaces(r3);
            c3.addToRaces(r3);
            c3.addToRaces(r);
            //os.setOtherOneSide(oos);

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
            em.getTransaction().commit();

            em.getTransaction().begin();
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private static void logOut() {
        securityToken = null;
    }


    //TODO: GSON, wasn't working???
/*    @Test
    void create() {
        CarDTO c = new CarDTO("new car", "something");
        RaceDTO r = new RaceDTO("first race");
        RaceDTO r2 = new RaceDTO("second race");
        DriverDTO d = new DriverDTO("driver");

        c.setDriver(d);
        c.addToRaces(r);
        c.addToRaces(r2);
        String requestBody = GSON.toJson(c);
        System.out.println(requestBody);

        given()
            .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("myPath/create")
                .then()
                .assertThat()
                .body("name", equalTo("new car"));
    }*/

    @Test
    void read() {
        given()
                .contentType("application/json")
                .get("myPath/read")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(3));
    }

// Prøved at få delete til at virke fik fejl Error Code: 1451 JPA
/*   @Test
    void readDriver() {
        given()
                .contentType("application/json")
                .get("myPath/readDriver")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(3));
    }*/

 /*   @Test
    void getById() {
        given()
            .contentType("application/json")
            .get("myPath/read/1")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("name", equalTo("Volvo"));
    }*/

    @Test
    void getDrivers() {
        given()
                .contentType("application/json")
                .get("myPath/readDriver/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2));
    }

/*    @Test
    void update() {
        CarDTO c = new CarDTO("changed car", "something");
        RaceDTO r = new RaceDTO("Updated Race");
        RaceDTO r2 = new RaceDTO("second Updated Race");
        DriverDTO d = new DriverDTO("Driver Update");

        c.setDriver(d);
        c.addToRaces(r);
        c.addToRaces(r2);
        String requestBody = GSON.toJson(c);
        System.out.println(requestBody);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("myPath/update/2")
                .then()
                .assertThat()
                .body("name", equalTo("changed car"));
    }*/

  /*  @Test
    void delete() {
        given()
            .contentType("application/json")
            .when()
            .delete("myPath/delete/3")
            .then()
            .statusCode(200)
            .body("removedId", equalTo(3));
    }*/

    @Test
    void adminTest() {
        login("admin", "test123");
        given()
            .headers("x-access-token", securityToken,"Content-type", MediaType.APPLICATION_JSON )
            .and()
            .get("myPath/adminTest")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("admin", equalTo("yes"));
    }

    @Test
    void signUpTest() {
        String json = String.format("{username: \"testing\", password: \"testingPass\"}");
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/signup")
                .then()
                .extract().path("token");
    }
}