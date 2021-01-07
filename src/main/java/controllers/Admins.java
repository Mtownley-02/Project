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
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String UserList() {
        System.out.println("Invoked Users.UserList()");
        JSONArray User = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Users.UserID, Users.Password, Users.Admin , Users.SessionToken FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject row = new JSONObject();
                row.put("UserId", results.getInt(1));
                row.put("Password", results.getString(2));
                row.put("Admin",results.getString(3));
                row.put("SessionToken",results.getString(4));
                User.add(row);
            }
            JSONObject response = new JSONObject();
            response.put("Users", User);
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }


    @DELETE
    @Path("delete/{UserId}")
    public String AdminsDelete(@PathParam("UserId") Integer Userid){
        System.out.println("invoked admins/delete");
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserId==?");
            ps.setInt(1,Userid);
            ps.executeUpdate();
            return ("Success");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete items.  Error code xx.\"}";
        }}

}
