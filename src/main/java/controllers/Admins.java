package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("Admins/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Admins {
    @GET
    @Path("view/{UserId}")
    public String AdminsView(@PathParam("UserId") String LogId) throws SQLException {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Logs WHERE UserId==UserId");
            return ("Success");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }

    }

    @POST
    @Path("delete")
    public String AdminsDelete(@FormDataParam("LogId") String LogId) throws SQLException {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Logs WHERE LogId= LogId");
            return ("Success");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }

    }

}
