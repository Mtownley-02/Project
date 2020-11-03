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

@Path("Logs/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Logs {
    @POST
    @Path("create")
    public String LogsCreate(@FormDataParam("LogId") Integer LogId ,@FormDataParam("Title") String Title,@FormDataParam("Text") String Text,@FormDataParam("UserId") Integer UserId, @FormDataParam("DateAdded") String DateAdded, @FormDataParam("LogURL") String LogURL ){
        try{
            PreparedStatement ps =Main.db.prepareStatement("INSERT INTO Logs(LogId, Title, Text, UserId, DateAdded, LogURL  VALUES (?,?,?,?,?,?))");
            ps.setInt(1,LogId);
            ps.setString(2,Title);
            ps.setString(3,Text);
            ps.setInt(4,UserId);
            ps.setString(5,DateAdded);
            ps.setString(6,LogURL);
            return ("Success");
        }catch (Exception exception){
            System.out.println("Log error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @POST
    @Path("view")
    public String LogsView(@FormDataParam("LogId") String LogId ){
        JSONArray response = new JSONArray();
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT Title,Text FROM Logs WHERE LogId=Lognum");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject row = new JSONObject();
                row.put("Title", results.getInt(1));
                row.put("Text", results.getString(2));
                response.add(row);
            }
            return response.toString();
        }catch (Exception exception){
            System.out.println("Log error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @POST
    @Path("delete")
    public String LogsDelete(@FormDataParam("LogId") String LogId ){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Logs WHERE LogId=Lognum");
            ResultSet results = ps.executeQuery();
            return ("Success");

        }catch (Exception exception){
            System.out.println("Log error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}
