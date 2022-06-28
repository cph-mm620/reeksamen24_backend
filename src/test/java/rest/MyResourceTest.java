package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ManySideDTO;
import dtos.OneSideDTO;
import dtos.OtherManySideDTO;
import dtos.OtherOneSideDTO;
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
import static org.junit.jupiter.api.Assertions.*;

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

            if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test"))
                throw new UnsupportedOperationException("You have not changed the passwords");

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");

            ManySide ms = new ManySide(1, "first many side");
            ManySide ms2 = new ManySide(2, "second many side");
            ManySide ms3 = new ManySide(3, "third many side");
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

    @Test
    void create() {
        ManySideDTO ms = new ManySideDTO("new many side");
        OtherManySideDTO oms = new OtherManySideDTO("first other many side");
        OtherManySideDTO oms2 = new OtherManySideDTO("second other many side");
        OneSideDTO os = new OneSideDTO("one side");
        OtherOneSideDTO oos = new OtherOneSideDTO("other one side");

        ms.setOneSide(os);
        os.setOtherOneSide(oos);
        ms.addToOtherManySides(oms);
        ms.addToOtherManySides(oms2);
        String requestBody = GSON.toJson(ms);
        System.out.println(requestBody);

        given()
            .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("myPath/create")
                .then()
                .assertThat()
                .body("name", equalTo("new many side"));
    }

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


    @Test
    void readWhere() {
        given()
                .contentType("application/json")
                .get("myPath/readWhere/another one side")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
    }

    @Test
    void getById() {
        given()
            .contentType("application/json")
            .get("myPath/read/1")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("name", equalTo("first many side"));
    }

    @Test
    void update() {
        ManySideDTO ms = new ManySideDTO("changed many side");
        OtherManySideDTO oms = new OtherManySideDTO("first other many side");
        OtherManySideDTO oms2 = new OtherManySideDTO("second other many side");
        OneSideDTO os = new OneSideDTO("one side");

        ms.setOneSide(os);
        ms.addToOtherManySides(oms);
        ms.addToOtherManySides(oms2);
        String requestBody = GSON.toJson(ms);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("myPath/update/2")
                .then()
                .assertThat()
                .body("name", equalTo("changed many side"));
    }

    @Test
    void delete() {
        given()
            .contentType("application/json")
            .when()
            .delete("myPath/delete/3")
            .then()
            .statusCode(200)
            .body("removedId", equalTo(3));
    }

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