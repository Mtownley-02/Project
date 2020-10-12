package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Users/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Users{
    @GET
    @Path("list")
    public String UsersList() {
        System.out.println("Invoked Users.UsersList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Admin FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("Admin", results.getString(2));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @POST
    @Path("create")
    public String UsersCreate(@FormDataParam("UserId") Integer UserId, @FormDataParam("Password") String Password,@FormDataParam("Admin") Boolean Admin,@FormDataParam("AdminId") Integer AdminId, @FormDataParam("SessionToken") Boolean SessionToken){
        System.out.println("Invoked Users.UsersCreate");
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (UserId, Password, Admin, AdminId, SessionToken) VALUES (?,?,?,?,?)");
            ps.setInt(1,UserId);
            ps.setString(2,Password);
            ps.setBoolean(3,Admin);
            ps.setInt(4,AdminId);
            ps.setBoolean(5,SessionToken);
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }
    @GET
    @Path("hub")
    public String UsersHub(){

    }
}
