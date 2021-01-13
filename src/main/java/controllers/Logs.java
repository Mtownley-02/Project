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
    public JSONArray GetTitle() throws SQLException{
        JSONArray response = new JSONArray();
        PreparedStatement UserId=Main.db.prepareStatement("SELECT UserId FROM Users WHERE SessionToken==1");
        ResultSet UserID=UserId.executeQuery();
        PreparedStatement LogIncrement= Main.db.prepareStatement("SELECT MAX(LogId) FROM Logs");
        ResultSet LogIdset=LogIncrement.executeQuery();
        int LogId = LogIdset.getInt(1);
        try {
            String row1= new String();
           // ResultSet UserID=UserId.executeQuery();
            int id =UserID.getInt(1);
            PreparedStatement Titles =Main.db.prepareStatement("SELECT Title FROM Logs WHERE UserId==?");
            Titles.setInt(1,id);
            ResultSet results=Titles.executeQuery();
            for(int i=0;i<results.getRow();i++) {
                row1= results.getString(1);
                response.set(i, (row1));
            }
            return response;
        } catch (Exception exception) {
            System.out.println("Log error: " + exception.getMessage());
            response.set(0, exception.getMessage());
            return response;
        }
    }
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String LogList() {
        System.out.println("Invoked Logs.LogList()");
        JSONArray Log = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Logs.LogID, Logs.Title, Logs.Text , Logs.UserId FROM Logs");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject row = new JSONObject();
                row.put("LogId", results.getInt(1));
                row.put("Title", results.getString(2));
                row.put("Text",results.getString(3));
                row.put("UserId",results.getString(4));
                Log.add(row);
            }
            JSONObject response = new JSONObject();
            response.put("Logs", Log);
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
    @POST
    @Path("create")
    public String LogsCreate(@FormDataParam("Title") String Title,@FormDataParam("Text") String Text,@FormDataParam("UserId") int UserId) throws SQLException {
        int response;
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
            response=(LogId+1);
            System.out.println("success "+response);
            return Integer.toString(response);
        }catch (Exception exception){
            String responseerror;
            System.out.println("Log error: " + exception.getMessage());
            responseerror="{\"Error\": \"Unable to save log.\"}";
            return responseerror;
        }
    }
    @GET
    @Path("GetText/{LogId}")
    public String LogsView(@PathParam("LogId") Integer LogId ){
        String[] response = new String[66];
        JSONArray Log = new JSONArray();
        try{
            JSONObject row1= new JSONObject();
            PreparedStatement ps = Main.db.prepareStatement("SELECT Text FROM Logs WHERE LogId==?");
            ps.setInt(1,LogId);
            ResultSet results = ps.executeQuery();
            row1.put("Text", results.getString( 1));
            System.out.println(row1);
            Log.add(results.getString( 1));
            System.out.println(Log.toString());
            return Log.toString();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return ("Error Log error: " + exception.getMessage());

        }
    }
    @DELETE
    @Path("delete/{LogId}")
    public String LogsDelete(@PathParam("LogId") Integer LogId){
        System.out.println("invoked logs/delete");
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Logs WHERE LogId==?");
            ps.setInt(1,LogId);
            ps.executeUpdate();
            return ("success");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to delete items.  Error code xx.\"}";
        }

    }
    @POST
    @Path("update")
    public String LogsUpdate(@FormDataParam("LogId") String Logid,@FormDataParam("Text") String text)throws SQLException{
        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Logs SET Text=? WHERE LogId==?");
            ps.setString(1,text);

            ps.setInt(2, Integer.parseInt(Logid));
            ps.executeUpdate();
            return ("Success");
        } catch (Exception exception){
            System.out.println(exception.getMessage());

            return "{\"Error\": \"Unable to update items.  Error code xx.\"}";
        }
    }
}
