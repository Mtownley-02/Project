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
    public String UsersCreate(@FormDataParam("Password") String Password, @FormDataParam("Admin") Boolean Admin) throws SQLException {
        System.out.println("Invoked Users.UsersCreate");
        int AdIdInt;

        try{
            PreparedStatement UserIncrement= Main.db.prepareStatement("SELECT MAX(UserId) FROM Users");
            ResultSet UserIdset=UserIncrement.executeQuery();
            int UserId = UserIdset.getInt(1);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (UserId, Password, Admin,SessionToken) VALUES (?,?,?,?)");
            ps.setInt(1,UserId+1);
            ps.setString(2,Password);
            ps.setBoolean(3,Admin);
            ps.setBoolean(4,false);
            ps.executeUpdate();
            String UserID=Integer.toString(UserId);
            return (UserID);
        } catch (Exception exception){
            System.out.println("Error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }
    @GET
    @Path("hub/{UserId}")
    public String UsersHub(@PathParam("UserId") Integer UserId) throws SQLException {
        System.out.println("Invoked Users.UsersHub");
        PreparedStatement SessionToken =Main.db.prepareStatement("SELECT SessionToken FROM Users WHERE UserId==UserId");
        SessionToken.executeQuery();
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
        ResultSet passkey=ps.executeQuery();
        String PassString=passkey.toString();
        System.out.println(PassString);
        if (Password.equals(PassString)){
            boolean Cookie;
            Cookie=true;
            PreparedStatement cookieupdate= Main.db.prepareStatement("UPDATE Users SET SessionToken=Cookie");
            PreparedStatement admin= Main.db.prepareStatement("SELECT Admin FROM Users WHERE UserId==UserId");
            ResultSet results = admin.executeQuery();
            cookieupdate.executeUpdate();
            JSONObject row1 = new JSONObject();
            row1.put("Admin", results.getString(1));
            String adstring=results.toString();
            Object Adminobj;
            Adminobj=admin;
            boolean AdminBool=(boolean)Adminobj;
            if(AdminBool){
                return (adstring);
            }
            return ("Success; Cookie created");

        } else{
            System.out.println("Database error: Incorrect Password/Id");
            return PassString;
        }

    }
}
