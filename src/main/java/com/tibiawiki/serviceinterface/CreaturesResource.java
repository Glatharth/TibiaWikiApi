package com.tibiawiki.serviceinterface;

import com.tibiawiki.process.RetrieveCreatures;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class CreaturesResource {

    private RetrieveCreatures retrieveCreatures;

    public CreaturesResource() {
        retrieveCreatures = new RetrieveCreatures();
    }

    @GET
    @Path("/creatures")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreatures(@QueryParam("expand") Boolean expand) {
        return Response.ok()
                .entity(expand != null && expand
                        ? retrieveCreatures.getCreaturesJSON().map(JSONObject::toMap)
                        : retrieveCreatures.getCreaturesList()
                )
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    @GET
    @Path("/creatures/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreatureByName(@PathParam("name") String name) {
        return retrieveCreatures.getCreatureJson(name)
                .map(a -> Response.ok()
                        .entity(a.toString(2))
                        .header("Access-Control-Allow-Origin", "*")
                        .build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .build());
    }
}
