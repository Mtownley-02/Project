package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Arrays;

@Path("Logs/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Logs {
    @POST
    @Path("GetTitle")
    public String[] GetTitle() throws SQLException{
        String[] array = new String[0];
        PreparedStatement UserId=Main.db.prepareStatement("SELECT UserId FROM Users WHERE SessionToken==1");
        ResultSet UserID=UserId.executeQuery();
        int Userid=UserID.getInt(1);
        PreparedStatement LogIncrement= Main.db.prepareStatement("SELECT MAX(LogId) FROM Logs");
        ResultSet LogIdset=LogIncrement.executeQuery();
        int LogId = LogIdset.getInt(1);
        try {
            PreparedStatement Titles =Main.db.prepareStatement("SELECT Title FROM Logs WHERE UserId==Userid");
            ResultSet results=Titles.executeQuery();
            for(int x=0;x<LogId;x++){
                array[x]=results.getString(x);
            }
            return array;
        } catch (Exception exception) {
            System.out.println("Log error: " + exception.getMessage());
            return["{\"Error\": \"Unable to list items.  Error code xx.\"}"];
        }
    }

    @POST
    @Path("create")
    public String LogsCreate(@FormDataParam("Title") String Title,@FormDataParam("Text") String Text,@FormDataParam("UserId") Integer UserId) throws SQLException {


        PreparedStatement LogIncrement= Main.db.prepareStatement("SELECT MAX(LogId) FROM Logs");
        ResultSet LogIdset=LogIncrement.executeQuery();
        int LogId = LogIdset.getInt(1);
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
    public String[] LogsView(@FormDataParam("LogId") String LogId ){
        String[] response = new;
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT Title,Text FROM Logs WHERE LogId==LogId");
            ResultSet results = ps.executeQuery();
            response[0]=results.getString(1);
            response[1]=results.getString(2);
            System.out.println(Arrays.toString(response));
            return response;
        }catch (Exception exception){
            System.out.println("Log error: " + exception.getMessage());
            return null;
        }
    }
    @POST
    @Path("delete")
    public String LogsDelete(@FormDataParam("LogId") String LogId) throws SQLException {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Logs WHERE LogId==LogId");
            ps.executeUpdate();
            return ("Success");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete items.  Error code xx.\"}";
        }

    }
}
