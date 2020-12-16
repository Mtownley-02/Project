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
    @Path("GetTitle")
    public String GetTitle() throws SQLException{
        JSONArray response = new JSONArray();
        PreparedStatement UserId=Main.db.prepareStatement("SELECT UserId FROM Users WHERE SessionToken==1");

        PreparedStatement LogIncrement= Main.db.prepareStatement("SELECT MAX(LogId) FROM Logs");
        ResultSet LogIdset=LogIncrement.executeQuery();
        int LogId = LogIdset.getInt(1);
        try {
            JSONObject row1= new JSONObject();
            ResultSet UserID=UserId.executeQuery();
            int Id =UserID.getInt(1);
            PreparedStatement Titles =Main.db.prepareStatement("SELECT Title FROM Logs WHERE UserId==Id");
            ResultSet results=Titles.executeQuery();
            row1.put("Title",results.getString(1));
            response.add(row1);
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Log error: " + exception.getMessage());
            return null;
        }
    }

    @POST
    @Path("create")
    public String LogsCreate(@FormDataParam("Title") String Title,@FormDataParam("Text") String Text) throws SQLException {
        JSONArray response = new JSONArray();
        PreparedStatement LogIncrement= Main.db.prepareStatement("SELECT MAX(LogId) FROM Logs");
        ResultSet LogIdset=LogIncrement.executeQuery();
        int LogId = LogIdset.getInt(1);
        PreparedStatement Userget=Main.db.prepareStatement("SELECT UserId FROM Users WHERE SessionToken==TRUE ");
        ResultSet UserResults=Userget.executeQuery();
        int UserId=UserResults.getInt(1);
        try{
            PreparedStatement ps =Main.db.prepareStatement("INSERT INTO Logs(LogId, Title, Text, UserId) VALUES (?,?,?,?)");
            ps.setInt(1,LogId+1);
            ps.setString(2,Title);
            ps.setString(3,Text);
            ps.setInt(4,UserId);
            ps.executeUpdate();
            return ("Success");
        }catch (Exception exception){
            System.out.println("Log error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @POST
    @Path("view")
    public Object[] LogsView(@FormDataParam("LogId") String LogId ){
        JSONArray response = new JSONArray();
        try{
            JSONObject row1= new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT Title,Text FROM Logs WHERE LogId==LogId");
            ResultSet results = ps.executeQuery();
            row1.put("Title",results.getString(1));
            row1.put("Text", results.getString( 2));
            response.add(row1);
            System.out.println(response.toString());
            return response.toArray();
        }catch (Exception exception){
            System.out.println("Log error: " + exception.getMessage());
            return null;
        }
    }
    @POST
    @Path("delete")
    public String LogsDelete(@FormDataParam("LogId") String LogId) throws SQLException {
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Logs WHERE LogId==LogId");
            ps.executeUpdate();
            JSONObject row1=new JSONObject();
            row1.put("Type","success");
            response.add(row1);
            return (response.toString());
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete items.  Error code xx.\"}";
        }

    }
    @POST
    @Path("update")
    public String LogsUpdate(@FormDataParam("LogId") String Logid,@FormDataParam("Title") String title,@FormDataParam("Text") String text)throws SQLException{
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Logs SET LogId=Logid,Text=text,Title=title WHERE LogId==Logid");
            ps.executeUpdate();
            return ("Success");
        } catch (Exception exception){
            System.out.println(exception.getMessage());

            return "{\"Error\": \"Unable to update items.  Error code xx.\"}";
        }
    }
}
