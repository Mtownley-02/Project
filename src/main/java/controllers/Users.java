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
    public String UsersCreate(@FormDataParam("UserId") Integer UserId, @FormDataParam("Password") String Password, @FormDataParam("Admin") Boolean Admin, @FormDataParam("AdminId") Integer AdminId) throws SQLException {
        System.out.println("Invoked Users.UsersCreate");
        int AdIdint = 0;

        try{
            PreparedStatement AdminINCREMENT = Main.db.prepareStatement("SELECT MAX(AdminId) FROM Users");
            if(Admin){
                Object AdIdobj;
                AdIdobj=AdminINCREMENT;
                 AdIdint= (int) AdIdobj;


            }else{
                 AdIdint= Integer.parseInt(null);
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (UserId, Password, Admin, AdminId,SessionToken) VALUES (?,?,?,?,?)");
            ps.setInt(1,UserId);
            ps.setString(2,Password);
            ps.setBoolean(3,Admin);
            ps.setInt(4,AdIdint);
            ps.setBoolean(5,false);
            return ("UserId="+UserId);
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }
    @GET
    @Path("Hub")
    public String UsersHub(@FormDataParam("UserId") Integer UserId, @CookieParam("SessionToken") Boolean SessionToken){
        System.out.println("Invoked Users.UsersHub");
        if (SessionToken==Boolean.TRUE){

            return ("Status: Ok");
        } else{
            System.out.println("Database error: Incorrect cookie");
            return "{\"Error\": \"Unable to access hub, please see server console for more info.\"}";
        }

    }
    @POST
    @Path("AttemptLogin")
    public String UsersAttemptLogin(@FormDataParam("UserId") Integer UserId, @FormDataParam("Password") String Password) throws SQLException {
        System.out.println("Invoked Users.UsersAttemptLogin");
        PreparedStatement ps = Main.db.prepareStatement("SELECT Password FROM Users WHERE UserId==UserId ");
        Object CorrectPassword;
        CorrectPassword=ps;
        if (Password==CorrectPassword){
            boolean Cookie;
            Cookie=true;
            PreparedStatement cookieupdate= Main.db.prepareStatement("UPDATE Users SET SessionToken=Cookie");
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
