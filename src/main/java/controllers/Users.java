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
            while (results.next()) {
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
    public String UsersCreate(@FormDataParam("UserId") Integer UserId, @FormDataParam("Password") String Password, @FormDataParam("Admin") Boolean Admin) throws SQLException {
        System.out.println("Invoked Users.UsersCreate");
        int AdIdInt;

        try{

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (UserId, Password, Admin,SessionToken) VALUES (?,?,?,?)");
            ps.setInt(1,UserId);
            ps.setString(2,Password);
            ps.setBoolean(3,Admin);
            ps.setBoolean(4,false);
            return ("UserId="+UserId);
        } catch (Exception exception){
            System.out.println("Error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }
    @GET
    @Path("hub")
    public String UsersHub(@FormDataParam("UserId") Integer UserId) throws SQLException {
        System.out.println("Invoked Users.UsersHub");
        PreparedStatement SessionToken =Main.db.prepareStatement("SELECT SessionToken FROM Users WHERE UserId==UserId");
        Object Tokenobj;
        Tokenobj=SessionToken;
        boolean BoolToken= (boolean) Tokenobj;
        if (BoolToken){

            return ("Status: Ok");
        } else{
            System.out.println("Database error: Incorrect cookie");
            return "{\"Error\": \"Unable to access hub, please see server console for more info.\"}";
        }

    }
    @POST
    @Path("attemptlogin")
    public String UsersAttemptLogin(@FormDataParam("UserId") Integer UserId, @FormDataParam("Password") String Password) throws SQLException {
        System.out.println("Invoked Users.UsersAttemptLogin");
        PreparedStatement ps = Main.db.prepareStatement("SELECT Password FROM Users WHERE UserId==UserId ");
        Object CorrectPassword;
        System.out.println(ps);
        CorrectPassword=ps;
        System.out.println(CorrectPassword);
        String PassString= CorrectPassword.toString();
        System.out.println(PassString);
        if (Password.equals(PassString)){
            boolean Cookie;
            Cookie=true;
            PreparedStatement cookieupdate= Main.db.prepareStatement("UPDATE Users WHERE SessionToken=Cookie");
            PreparedStatement admin= Main.db.prepareStatement("SELECT Admin FROM Users WHERE UserId==UserId");
            Object Adminobj;
            Adminobj=admin;
            boolean AdminBool=(boolean)Adminobj;
            if(AdminBool){
                return ("Admin status given");
            }
            return ("Success; Cookie created");
        } else{
            System.out.println("Database error: Incorrect Password/Id");
            return "{\"Error\": \"Unable to access hub, please see server console for more info.\"}";
        }

    }
}
