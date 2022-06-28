package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.ManySide;
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
        ManySide m = GSON.fromJson(jsonContext, ManySide.class);
        System.out.println(m);
        return Response.ok().entity(facade.create(m)).build();
    }


    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read () {
         return Response.ok().entity(GSON.toJson(facade.read())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("readWhere/{nameOfOneSide}") //name of some side
    public Response readWhere (@PathParam("nameOfOneSide") String nameOfOneSide) {
        return Response.ok().entity(GSON.toJson(facade.readWhere(nameOfOneSide))).build();
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
            ManySide ms = GSON.fromJson(jsonContext, ManySide.class);
            ms.setId(id);
            return Response.ok().entity(facade.update(ms)).build();
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
