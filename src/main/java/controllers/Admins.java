package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
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
    @Path("view")
    public String[] AdminsView() throws SQLException {
        String[] resultarray= new String[3];
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Users");
            ResultSet results=ps.executeQuery();
            resultarray[0]=results.getString(1);
            resultarray[1]=results.getString(3);
            resultarray[2]=results.getString(4);
            return (resultarray);
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return null;
        }

    }
    @GET
    @Path("viewLog")
    public String[] AdminsViewLog() throws SQLException {
        String[] resultarray= new String[2];
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Logs");
            ResultSet results=ps.executeQuery();
            resultarray[0]=results.getString(1);
            resultarray[1]=results.getString(2);
            return (resultarray);
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return null;
        }

    }

    @POST
    @Path("delete")
    public String AdminsDelete(@FormDataParam("UserId") String UserId) throws SQLException {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserId==UserId");
            ps.executeUpdate();
            return ("Success");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete items.  Error code xx.\"}";
        }

    }

}
