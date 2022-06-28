package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Car;
import facades.Facade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("myPath")
public class MyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Facade facade = Facade.getFacade(EMF);
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON) // only on post and put. (sending infomation)
    public Response create(String jsonContext) {
        System.out.println("------------------");
        System.out.println(jsonContext);
        Car c = GSON.fromJson(jsonContext, Car.class);
        System.out.println(c);
        return Response.ok().entity(facade.create(c)).build();
    }


    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read () {
         return Response.ok().entity(GSON.toJson(facade.read())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("readDriver/{id}") //name of someone id
    public Response getByDriverId (@PathParam("id") int id) {
        System.out.println(id);
        return Response.ok().entity(GSON.toJson(facade.getByDriverId(id))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("read/{id}")
    public Response getById (@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(facade.getById(id))).build();
    }

    @PUT
    @Path("update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update (@PathParam("id") int id, String jsonContext) {
        System.out.println("------------------");
        System.out.println(jsonContext);
            Car c = GSON.fromJson(jsonContext, Car.class);

            c.setId(id);
        System.out.println(c);
            return Response.ok().entity(facade.update(c)).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        facade.delete(id);
        return "{\"removedId\":" + id + "}";
    }

    @GET
    @Path("adminTest")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String adminTest() {
        return "{\"admin\":\"yes\"}";
    }

}
