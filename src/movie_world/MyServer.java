package movie_world;

import com.vmm.JHTTPServer;
import java.security.interfaces.RSAKey;

import java.util.*;
import java.sql.*;

public class MyServer extends JHTTPServer {

    public MyServer(int portno) throws Exception {
        super(portno);
    }

    @Override
    public Response serve(String uri, String method, Properties header,
            Properties parms, Properties files) {
        Response res = new Response(HTTP_OK, "text/plain", "Invalid entry");
        if (uri.equals("/signup")) {
            String username = parms.getProperty("username");
            String email = parms.getProperty("email");
            String password = parms.getProperty("password");
            String phone = parms.getProperty("phone");
            String address = parms.getProperty("address");
            String photo = saveFileOnServerWithRandomName(files, parms, "photo", "src/pictures");
            try {

                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\'");
                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "Email Already Registered");
                } else {

                    rs.moveToInsertRow();
                    rs.updateString("username", username);
                    rs.updateString("email", email);
                    rs.updateString("password", password);
                    rs.updateString("mobile", phone);
                    rs.updateString("address", address);
                    rs.updateString("photo", photo);
                    rs.insertRow();

                    res = new Response(HTTP_OK, "text/plain", "Signed Up Successfully !!!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (uri.equals("/checklogin")) {
            System.out.println("Entered");
            String email = parms.getProperty("email");
            String password = parms.getProperty("password");

            try {

                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\' and password=\'" + password + "\'");

                if (rs.next()) {
                    String username = rs.getString("username");
                    res = new Response(HTTP_OK, "text/plain", "Login Sucess" + " " + username + " " + email);
                } else {
                    res = new Response(HTTP_OK, "text/plain", "Login Failed");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (uri.equals("/changepassword")) {
            String email = parms.getProperty("email");
            String currpass = parms.getProperty("currpassword");
            String newpassword = parms.getProperty("newpassword");

            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\' and password=\'" + currpass + "\'");

                if (rs.next()) {
                    rs.updateString("password", newpassword);
                    rs.updateRow();

                    res = new Response(HTTP_OK, "text/plain", "sucess");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "unsucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.equals("/changeusername")) {
            String email = parms.getProperty("email");
            String currpass = parms.getProperty("password");
            String newusername = parms.getProperty("newusername");

            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\' and password=\'" + currpass + "\'");

                if (rs.next()) {
                    rs.updateString("username", newusername);
                    rs.updateRow();

                    res = new Response(HTTP_OK, "text/plain", "sucess");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "unsucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.equals("/changeemail")) {
            String email = parms.getProperty("email");
            String currpass = parms.getProperty("password");
            String newemail = parms.getProperty("newemail");

            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\' and password=\'" + currpass + "\'");

                if (rs.next()) {
                    rs.updateString("email", newemail);
                    rs.updateRow();

                    res = new Response(HTTP_OK, "text/plain", "sucess");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "unsucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (uri.equals("/changephoto")) {
            String email = parms.getProperty("email");
            String photo = saveFileOnServerWithRandomName(files, parms, "photo", "src/pictures");

            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\'");
                System.out.println("");
                if (rs.next()) {
                    rs.updateString("photo", photo);
                    rs.updateRow();

                    res = new Response(HTTP_OK, "text/plain", "sucess");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "unsucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.equals("/changephone")) {
            String email = parms.getProperty("email");
            String currpass = parms.getProperty("password");
            String newphone = parms.getProperty("newphone");
            System.out.println(newphone);
            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\' and password=\'" + currpass + "\'");
                System.out.println("");
                if (rs.next()) {
                    rs.updateString("mobile", newphone);
                    rs.updateRow();

                    res = new Response(HTTP_OK, "text/plain", "sucess");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "unsucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.equals("/changeaddress")) {
            String email = parms.getProperty("email");
            String currpass = parms.getProperty("password");
            String newaddress = parms.getProperty("newaddress");

            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\' and password=\'" + currpass + "\'");

                if (rs.next()) {
                    rs.updateString("address", newaddress);
                    rs.updateRow();

                    res = new Response(HTTP_OK, "text/plain", "sucess");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "unsucess");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.equals("/userdetails")) {
            String email = parms.getProperty("email");
            try {
                ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\'");
                if (rs.next()) {
                    String user = rs.getString("username");
                    String emailid = rs.getString("email");
                    //  String password = rs.getString("password");
                    String mobile = rs.getString("mobile");
                    String address = rs.getString("address");
                    String photo = rs.getString("photo");

                    res = new Response(HTTP_OK, "text/plain", user + "~" + emailid + "~" + mobile + "~" + address + "~" + photo);
                } else {
                    res = new Response(HTTP_OK, "text/plain", "error");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (uri.equals("/alreadylogged")) {
            res = new Response(HTTP_OK, "text/plain", "ok");
            
        } else if (uri.equals("/favoritesmovie")) {
            String email = parms.getProperty("email");
            String id = parms.getProperty("id");
            try {
                ResultSet rs = DBloader.executeSQL("select * from favorite_movies where email=\'" + email + "\' and id=\'" + id + "\'");

                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "old");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "new");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (uri.equals("/addfavoritemovie")) {
            System.out.println("enter");
            String email = parms.getProperty("email");
            String id = parms.getProperty("id");
            try {
                ResultSet rs = DBloader.executeSQL("select * from favorite_movies where email=\'" + email + "\' and id=\'" + id + "\'");
                rs.moveToInsertRow();
                rs.updateString("id",id );
                rs.updateString("email", email);
                rs.insertRow();
               res = new Response(HTTP_OK, "text/plain", "ok");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (uri.equals("/delfavoritemovie")) {
            System.out.println("enter");
            String email = parms.getProperty("email");
            String id = parms.getProperty("id");
            try {
             
                ResultSet rs = DBloader.executeSQL("select * from favorite_movies where email=\'" + email + "\' and id=\'" + id + "\'");
                if(rs.next()){
                rs.deleteRow();
               res = new Response(HTTP_OK, "text/plain", "ok");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (uri.equals("/favoritestv")) {
            System.out.println("enter");
            String email = parms.getProperty("email");
            String id = parms.getProperty("id");
            try {
                ResultSet rs = DBloader.executeSQL("select * from favorite_tv where email=\'" + email + "\' and id=\'" + id + "\'");

                if (rs.next()) {
                    res = new Response(HTTP_OK, "text/plain", "old");
                } else {
                    res = new Response(HTTP_OK, "text/plain", "new");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (uri.equals("/addfavoritetv")) {
            
            String email = parms.getProperty("email");
            String id = parms.getProperty("id");
            try {
                ResultSet rs = DBloader.executeSQL("select * from favorite_tv where email=\'" + email + "\' and id=\'" + id + "\'");
                rs.moveToInsertRow();
                rs.updateString("id",id );
                rs.updateString("email", email);
                rs.insertRow();
               res = new Response(HTTP_OK, "text/plain", "ok");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (uri.equals("/delfavoritetv")) {
          
            String email = parms.getProperty("email");
            String id = parms.getProperty("id");
            try {
             
                ResultSet rs = DBloader.executeSQL("select * from favorite_tv where email=\'" + email + "\' and id=\'" + id + "\'");
                if(rs.next()){
                rs.deleteRow();
               res = new Response(HTTP_OK, "text/plain", "ok");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if(uri.equals("/deleteaccount")){
           String email = parms.getProperty("email");
           
           try{
               ResultSet rs = DBloader.executeSQL("select * from user_signup where email=\'" + email + "\'");
                if (rs.next()) {
                    rs.deleteRow();
                    res = new Response(HTTP_OK, "text/plain", "ok");
                }
           }
           catch (Exception ex){
               ex.printStackTrace();
           }
        }
        else if(uri.equals("/getfavoritemovies")){
            String email = parms.getProperty("email");
            try{
                ResultSet rs = DBloader.executeSQL("select * from favorite_movies where email =\'"+email+"\'");
                String ids ="";
                while(rs.next()){
                   ids+="~"+rs.getString("id");
                }
                res = new Response(HTTP_OK, "text/plain", ids);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
         else if(uri.equals("/getfavoritetv")){
            String email = parms.getProperty("email");
            try{
                ResultSet rs = DBloader.executeSQL("select * from favorite_tv where email =\'"+email+"\'");
                String ids ="";
                while(rs.next()){
                   ids+="~"+rs.getString("id");
                }
                res = new Response(HTTP_OK, "text/plain", ids);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return res;
    }

}
