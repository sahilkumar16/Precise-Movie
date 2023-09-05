
package movie_world;

import com.mashape.unirest.http.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Actor_Search extends javax.swing.JFrame implements ActionListener {

    boolean photoes = dump.render;
    String source = "";
    public Actor_Search( String source) {
        
        this.source = source;
        initComponents();
        setHeadinglb();
                  
        setSize(1000, 500);
        jScrollPane1.setBounds(30, 100, 940, 350);
        jScrollPane3.setBounds(30, 100, 940, 350);
        jScrollPane1.setVisible(false);
        jScrollPane3.setVisible(false);
        getContentPane().setBackground(Color.BLACK);
        setFocusable(true);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
       
    }
    
    public void setHeadinglb(){
        if(source.equals("movies")){
            headingLb.setText("ACTOR");
        }
        else if(source.equals("tv")){
            headingLb.setText("TV SHOWS");
        }
        else if(source.equals("movie_name")){
            headingLb.setText("MOVIES");
        }
        else {
            headingLb.setText("MOVIE HOUSES");
        }
    }
    

    public void movie(String actorname) {
      headingLb.setText("ACTOR");
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/person?api_key=" + api + "&language=en-US&query=" + actorname + "&page=1&include_adult=false").asString();

            if (response.getStatus() == 200) {
                String body = response.getBody();

                JSONParser parser = new JSONParser();
                JSONObject mainobj = (JSONObject) parser.parse(body);
                JSONArray array = (JSONArray) mainobj.get("results");
                mainPanel.removeAll();
                mainPanel.setVisible(true);
                if (array.size() != 0) {
                    int x = 10, y = 10, k = 0;
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = (JSONObject) array.get(i);
                        long id = (long) jo.get("id");
                        String name = (String) jo.get("name");
                        Double popularity = (Double) jo.get("popularity");
                        String known_for = (String) jo.get("known_for_department");
                        JSONArray allMovies = (JSONArray) jo.get("known_for");
                        k++;
                        Actor_pannel mp = new Actor_pannel(i, allMovies);
                        mp.nameLb.setText(name);
                        mp.dobLb.setText(known_for);
                        mp.popularity_progress.setValue((int) (Math.round(popularity)));
                        mp.popularity_progress.setString("Popularity" + popularity);
                        try{
                           HttpResponse<String> actordetails = Unirest.get("https://api.themoviedb.org/3/person/" + id + "?api_key=" + api + "&language=en-US").asString(); 
                           if(actordetails.getStatus()==200){
                               String actorr = actordetails.getBody();
                               
                                JSONObject actor = (JSONObject) parser.parse(actorr);
                                String biographystring = (String)actor.get("biography");
                                String biography="";
                                String [] kl =biographystring.split("(?<=\\G.{" + 40 + "})");
                    for (String string : kl) {
                        biography+=string+" "+"\n"+" ";
                    }
                                String dob =(String)actor.get("birthday");
                                String death =(String)actor.get("deathday");
                                String pic =(String)actor.get("profile_path");
                                if(dob==null){
                                    mp.dobLb.setVisible(false);
                                    mp.deathLb.setVisible(false);
                                }
                                mp.dobLb.setText(dob);
                                mp.bioTf.setText(biography);
                                if(death!=null){
                                    mp.deathLb.setText(death);
                                }
                                else{
                                    mp.deathLb.setText("Alive");
                                }
                                if (photoes) {
                                    if (pic != null) {
                                        try {
                                            ImageIcon ic = new ImageIcon(new URL("https://image.tmdb.org/t/p/w200" + pic));
                                            Image img = ic.getImage().getScaledInstance(mp.photoLb.getWidth(), mp.photoLb.getHeight(), Image.SCALE_SMOOTH);
                                            ImageIcon ic1 = new ImageIcon(img);

                                            mp.photoLb.setIcon(ic1);

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                } else {
                                    mp.photoLb.setText("Disabled");
                                }

                           }
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                        mp.setBounds(x, y, 300, 300);
                        
                        mp.moviesBt.addActionListener((e) -> {
                            jScrollPane1.setVisible(false);

                            int w = 10, r = 10, l = 0;
                            for (int j = 0; j < allMovies.size(); j++) {
                                l++;
                                JSONObject joo = (JSONObject) allMovies.get(j);
                                String moviename = (String) joo.get("title");
                                String movieRelease = (String) joo.get("release_date");
                                String photo = (String) joo.get("poster_path");
                                String overviewString = (String) joo.get("overview");
                                String overview = "";
                                String [] n =overviewString.split("(?<=\\G.{" + 39 + "})");
                    for (String string : n) {
                        overview+=string+" "+"\n"+" ";
                    }
                    

                                long vote = (long) joo.get("vote_count");
                                Movie_Pannel mv = new Movie_Pannel();
                                mv.repaint();
                                mv.movieName.setText(moviename);
                                mv.OverviewLb.setText(overview);

                                if (photoes) {
                                    if (photo != null) {
                                        try {
                                            ImageIcon ic = new ImageIcon(new URL("https://image.tmdb.org/t/p/w200" + photo));
                                            Image img = ic.getImage().getScaledInstance(mv.photoLb.getWidth(), mv.photoLb.getHeight(), Image.SCALE_SMOOTH);
                                            ImageIcon ic1 = new ImageIcon(img);

                                            mv.photoLb.setIcon(ic1);

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                } else {
                                    mv.photoLb.setText("Disabled");
                                }

                                mv.popularity_progress.setValue((int) vote);
                                mv.popularity_progress.setString("Votes : " + vote);
                                mv.setBounds(w, r, 300, 300);
                                w = w + 300;
                                mv.backBt.addActionListener((ex) -> {
                                    jScrollPane3.setVisible(false);
                                    jScrollPane1.setVisible(true);
                                    Actor_movies_pannel.removeAll();
                                });
                                
                                 mv.delfavorite.addActionListener((exx) -> {
                                System.out.println("this");
                                try {
                                    HttpResponse<String> response1 = Unirest.get("http://localhost:8000/delfavoritemovie").queryString("email", dump.email).queryString("id", id).asString();
                                    if (response1.getStatus() == 200) {
                                        String backf = response1.getBody();
                                        if (backf.contains("ok")) {
                                            JOptionPane.showMessageDialog(this, "Deleted From favorites");

                                            mv.delfavorite.setVisible(false);
                                            mv.favoriteaddBt.setVisible(true);
                                            mv.delfavorite.setVisible(false);
                                            repaint();
                                            mv.repaint();
                                            Actor_movies_pannel.repaint();
                                        } else if (backf.equals("fail")) {
                                            JOptionPane.showMessageDialog(this, "something went wrong");
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            );
                            mv.favoriteaddBt.addActionListener((exx) -> {
                                System.out.println("this");
                                try {
                                    HttpResponse<String> response1 = Unirest.get("http://localhost:8000/addfavoritemovie").queryString("email", dump.email).queryString("id", id).asString();
                                    if (response1.getStatus() == 200) {
                                        String backf = response1.getBody();
                                        if (backf.contains("ok")) {
                                            JOptionPane.showMessageDialog(this, "Added to favorites");
                                            mv.favoriteaddBt.setVisible(false);
                                            mv.delfavorite.setVisible(true);
                                            repaint();
                                            mv.repaint();
                                            Actor_movies_pannel.repaint();
                                        } else if (backf.equals("fail")) {
                                            JOptionPane.showMessageDialog(this, "something went wrong");
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });

                            try {
                                HttpResponse<String> rest = Unirest.get("http://localhost:8000/favoritesmovie").queryString("email", dump.email).queryString("id", id).asString();
                                if (rest.getStatus() == 200) {
                                    String rev = rest.getBody();
                                    if (rev.contains("old")) {
                                        mv.favoriteaddBt.setVisible(false);

                                    } else if (rev.contains("new")) {
                                        mv.delfavorite.setVisible(false);

                                    }

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                                Actor_movies_pannel.add(mv);
                                mv.repaint();
                                Actor_movies_pannel.repaint();

                            }
                            int h = 320 * l;
                            Actor_movies_pannel.setPreferredSize(new Dimension(h, 320));
                            jScrollPane3.setVisible(true);

                        });

                        mainPanel.add(mp);
                        x = x + 320;
                        repaint();
                        mainPanel.repaint();
                        mp.repaint();
                    }
                    int size = 320 * k;

                    mainPanel.setPreferredSize(new Dimension(size, 300));

                } else {
                    JOptionPane.showMessageDialog(this, "Not found");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tv(String search) {
        headingLb.setText("TV SHOW");
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/tv?api_key=" + api + "&language=en-US&page=1&query=" + search + "&include_adult=false").asString();

            if (response.getStatus() == 200) {
                String body = response.getBody();

                JSONParser parser = new JSONParser();
                JSONObject mainobj = (JSONObject) parser.parse(body);
                JSONArray array = (JSONArray) mainobj.get("results");
                if (array.size() != 0) {
                    int x = 10, y = 10, k = 0;
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = (JSONObject) array.get(i);
                        long id = (long) jo.get("id");
                        String overviewString = (String) jo.get("overview");
                        String overview = "";

                       String [] n =overviewString.split("(?<=\\G.{" + 39 + "})");
                    for (String string : n) {
                        overview+=string+" "+"\n"+" ";
                    }
                    
                        String Title = (String) jo.get("name");
                        //long popularity = (long)jo.get("popularity");
                        long vote_count = (long) jo.get("vote_count");
                        String photo = (String) jo.get("poster_path");
                        String release_date = (String) jo.get("first_air_date");
                        k++;
                        Movie_Pannel mp = new Movie_Pannel();
                        mp.aboutlb.setText("About Show");
                        if (photoes) {
                            if (photo != null) {
                                ImageIcon ic = new ImageIcon(new URL("https://image.tmdb.org/t/p/w200" + photo));
                                Image img = ic.getImage().getScaledInstance(mp.photoLb.getWidth(), mp.photoLb.getHeight(), Image.SCALE_SMOOTH);
                                ImageIcon ic1 = new ImageIcon(img);

                                mp.photoLb.setIcon(ic1);
                            }
                        } else {
                            mp.photoLb.setText("Disabled");
                        }
                        mp.movieName.setText(Title);
                        mp.releaseDate.setText(release_date);
                        mp.OverviewLb.setText(overview);

                        mp.jScrollPane1.getViewport().setViewPosition(new Point(0, 0));

                        mp.popularity_progress.setString("Votes: " + vote_count);
                        mp.popularity_progress.setValue((int) vote_count);
                        mp.setBounds(x, y, 300, 330);
                        mp.backBt.setVisible(false);

                        Actor_movies_pannel.add(mp);
                        x = x + 320;
                        
                         mp.delfavorite.addActionListener((e) -> {
                                System.out.println("this");
                          try{
                              HttpResponse<String> response1 = Unirest.get("http://localhost:8000/delfavoritetv").queryString("email",dump.email).queryString("id",id).asString();
                              if(response1.getStatus()==200){
                                  String backf = response1.getBody();
                                  if(backf.contains("ok")){
                                      JOptionPane.showMessageDialog(this, "Deleted From favorites");
                                     
   
                                      mp.favoriteaddBt.setVisible(true);
                                      mp.delfavorite.setVisible(false);
                                      repaint();
                                      mp.repaint();
                                      Actor_movies_pannel.repaint();
                                  }
                                  else if(backf.equals("fail")){
                                      JOptionPane.showMessageDialog(this, "something went wrong");
                                  }
                              }
                          }
                          catch (Exception ex){
                              ex.printStackTrace();                          }
                            }
                            );
                        mp.favoriteaddBt.addActionListener((e) -> {
                            System.out.println("this");
                            try {
                                HttpResponse<String> response1 = Unirest.get("http://localhost:8000/addfavoritetv").queryString("email", dump.email).queryString("id", id).asString();
                                if (response1.getStatus() == 200) {
                                    String backf = response1.getBody();
                                    if (backf.contains("ok")) {
                                        JOptionPane.showMessageDialog(this, "Added to favorites");
                                        mp.favoriteaddBt.setVisible(false);
                                        mp.delfavorite.setVisible(true);
                                        repaint();
                                        mp.repaint();
                                        Actor_movies_pannel.repaint();
                                    } else if (backf.equals("fail")) {
                                        JOptionPane.showMessageDialog(this, "something went wrong");
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });

                        try {
                            HttpResponse<String> res = Unirest.get("http://localhost:8000/favoritestv").queryString("email", dump.email).queryString("id", id).asString();
                            if (res.getStatus() == 200) {
                                String rev = res.getBody();
                                if (rev.contains("old")) {
                                    mp.favoriteaddBt.setVisible(false);

                                } else if (rev.contains("new")) {
                                    mp.delfavorite.setVisible(false);

                                }

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        repaint();
                        Actor_movies_pannel.repaint();
                        mp.repaint();
                    }
                    int size = 320 * k;
                    Actor_movies_pannel.setPreferredSize(new Dimension(size, 320));
                } else {
                    JOptionPane.showMessageDialog(this, "No result found");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void company(String search) {
        headingLb.setText("MOVIE HOUSE");
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/company?api_key=" + api + "&query=" + search + "&page=1").asString();

            if (response.getStatus() == 200) {
                String body = response.getBody();

                JSONParser parser = new JSONParser();
                JSONObject mainobj = (JSONObject) parser.parse(body);
                JSONArray array = (JSONArray) mainobj.get("results");
                if (array.size() != 0) {
                    int x = 10, y = 10, k = 0;
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = (JSONObject) array.get(i);
                        long id = (long) jo.get("id");

                        String Title = (String) jo.get("name");
                        String photo = (String) jo.get("logo_path");
                        System.out.println(photo);
                        k++;
                        company_pannel mp = new company_pannel();
                        if (photoes) {
                            if (photo != null) {
                                ImageIcon ic = new ImageIcon(new URL("https://image.tmdb.org/t/p/w200" + photo));
                                Image img = ic.getImage().getScaledInstance(mp.photoLb.getWidth(), mp.photoLb.getHeight(), Image.SCALE_SMOOTH);
                                ImageIcon ic1 = new ImageIcon(img);

                                mp.photoLb.setIcon(ic1);
                            }

                        } else {
                            mp.photoLb.setText("Disabled");
                        }
                        mp.name_lb.setText(Title);

                        mp.setBounds(x, y, 300, 300);

                        Actor_movies_pannel.add(mp);
                        x = x + 320;

                        repaint();
                        Actor_movies_pannel.repaint();
                        mp.repaint();
                    }
                    int size = 320 * k;
                    Actor_movies_pannel.setPreferredSize(new Dimension(size, 300));

                } else {
                    JOptionPane.showMessageDialog(this, "NO RESULT FOUND");

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    public void movie_name(String search){
        headingLb.setText("MOVIE");
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/movie?api_key="+api+"&language=en-US&query="+search+"&page=1&include_adult=false").asString();

            if (response.getStatus() == 200) {
                String body = response.getBody();

                JSONParser parser = new JSONParser();
                JSONObject mainobj = (JSONObject) parser.parse(body);
                JSONArray array = (JSONArray) mainobj.get("results");
                if (array.size() != 0) {
                    int x = 10, y = 10, k = 0;
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = (JSONObject) array.get(i);
                        long id = (long) jo.get("id");
                        String overviewString = (String) jo.get("overview");
                        String overview = "";

                        String [] n =overviewString.split("(?<=\\G.{" + 39 + "})");
                    for (String string : n) {
                        overview+=string+" "+"\n"+" ";
                    }
                    
                        String Title = (String) jo.get("title");
                        long vote_count = (long) jo.get("vote_count");
                        String photo = (String) jo.get("poster_path");
                        String release_date = (String) jo.get("release_date");
                        k++;
                        Movie_Pannel mp = new Movie_Pannel();
                        
                        if (photoes) {
                            if (photo != null) {
                                ImageIcon ic = new ImageIcon(new URL("https://image.tmdb.org/t/p/w200" + photo));
                                Image img = ic.getImage().getScaledInstance(mp.photoLb.getWidth(), mp.photoLb.getHeight(), Image.SCALE_SMOOTH);
                                ImageIcon ic1 = new ImageIcon(img);

                                mp.photoLb.setIcon(ic1);
                            }
                        } else {
                            mp.photoLb.setText("Disabled");
                        }
                        mp.movieName.setText(Title);
                        mp.releaseDate.setText(release_date);
                        mp.OverviewLb.setText(overview);

                        mp.jScrollPane1.getViewport().setViewPosition(new Point(0, 0));

                        mp.popularity_progress.setString("Votes: " + vote_count);
                        mp.popularity_progress.setValue((int) vote_count);
                        mp.setBounds(x, y, 300, 330);
                        mp.backBt.setVisible(false);

                        Actor_movies_pannel.add(mp);
                        x = x + 320;
                         mp.trailerlb.setVisible(true);
                    mp.trailerbt.setVisible(true);
                    
                    mp.trailerbt.addActionListener((e) -> {
                        try{
                       HttpResponse<String> vidres = Unirest.get("https://api.themoviedb.org/3/movie/"+id+"?api_key=980d96176457a6e65b8bc282bcadccd4&append_to_response=videos").asString();
                            if(vidres.getStatus()==200){
                            String vidbody = vidres.getBody();
                            JSONParser vidparser = new JSONParser();
                            JSONObject vidjo = (JSONObject) vidparser.parse(vidbody);
                            
                            JSONObject revid =(JSONObject) vidjo.get("videos");
                            JSONArray  videos =(JSONArray) revid.get("results");
                            if(videos.size()!=0){
                                String vediokey ="";
                                for (int j = 0; j < videos.size(); j++) {
                                    JSONObject vedio = (JSONObject)videos.get(j);
                                    String tname =(String)vedio.get("name");
                                    String trailer = tname.toLowerCase();
                                    if(trailer.contains("trailer")){
                                        vediokey = (String) vedio.get("key");
                                        break;
                                        
                                    }
                                    if(j==videos.size()-1){
                                        vediokey = (String) vedio.get("key");
                                        break;
                                    }
                                    
                                }
                                
                                
                              //  String vediokey =(String) vedio.get("key");
                             
                                  URI u = new URI("https://www.youtube.com/watch?v="+vediokey);
                                            Desktop d = Desktop.getDesktop();
                                            d.browse(u);
                            }
                            else{
                                JOptionPane.showMessageDialog(this, "Trailer currently not availaible");
                            }
                                
                            }
                         
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    });
                        mp.delfavorite.addActionListener((e) -> {
                                System.out.println("this");
                          try{
                              HttpResponse<String> response1 = Unirest.get("http://localhost:8000/delfavoritemovie").queryString("email",dump.email).queryString("id",id).asString();
                              if(response1.getStatus()==200){
                                  String backf = response1.getBody();
                                  if(backf.contains("ok")){
                                      JOptionPane.showMessageDialog(this, "Deleted From favorites");
                                     
                                      mp.delfavorite.setVisible(false);
                                      mp.favoriteaddBt.setVisible(true);
                                      mp.delfavorite.setVisible(false);
                                      repaint();
                                      mp.repaint();
                                      Actor_movies_pannel.repaint();
                                  }
                                  else if(backf.equals("fail")){
                                      JOptionPane.showMessageDialog(this, "something went wrong");
                                  }
                              }
                          }
                          catch (Exception ex){
                              ex.printStackTrace();                          }
                            }
                            );
                     mp.favoriteaddBt.addActionListener((e) -> {
                                System.out.println("this");
                          try{
                              HttpResponse<String> response1 = Unirest.get("http://localhost:8000/addfavoritemovie").queryString("email",dump.email).queryString("id",id).asString();
                              if(response1.getStatus()==200){
                                  String backf = response1.getBody();
                                  if(backf.contains("ok")){
                                      JOptionPane.showMessageDialog(this, "Added to favorites");
                                     mp.favoriteaddBt.setVisible(false);
                                     mp.delfavorite.setVisible(true);
                                     repaint();
                                     mp.repaint();
                                     Actor_movies_pannel.repaint();
                                  }
                                  else if(backf.equals("fail")){
                                      JOptionPane.showMessageDialog(this, "something went wrong");
                                  }
                              }
                          }
                          catch (Exception ex){
                              ex.printStackTrace(); 
                          }
                            });
                     
                    try{
                    HttpResponse<String> res = Unirest.get("http://localhost:8000/favoritesmovie").queryString("email",dump.email).queryString("id",id).asString();
                    if(res.getStatus()==200){
                        String rev = res.getBody();
                        if(rev.contains("old")){
                            mp.favoriteaddBt.setVisible(false);
                   
                           
                        }
                        else if(rev.contains("new")){
                            mp.delfavorite.setVisible(false);
                         
                        }
                        
                        
                    }
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                        repaint();
                        Actor_movies_pannel.repaint();
                        mp.repaint();
                    }
                    int size = 320 * k;
                    Actor_movies_pannel.setPreferredSize(new Dimension(size, 320));
                } else {
                    JOptionPane.showMessageDialog(this, "No result found");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchBar = new javax.swing.JTextField();
        searchBt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Actor_movies_pannel = new javax.swing.JPanel();
        backBt = new javax.swing.JButton();
        headingLb = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        searchBar.setBackground(new java.awt.Color(0, 0, 0));
        searchBar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searchBar.setForeground(new java.awt.Color(255, 255, 255));
        searchBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        searchBar.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(searchBar);
        searchBar.setBounds(300, 30, 280, 30);

        searchBt.setBackground(new java.awt.Color(0, 0, 0));
        searchBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-next-page-30.png"))); // NOI18N
        searchBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtActionPerformed(evt);
            }
        });
        getContentPane().add(searchBt);
        searchBt.setBounds(580, 30, 80, 30);

        mainPanel.setBackground(new java.awt.Color(0, 0, 0));
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 968, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(mainPanel);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 250, 960, 170);

        Actor_movies_pannel.setBackground(new java.awt.Color(0, 0, 0));
        Actor_movies_pannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        javax.swing.GroupLayout Actor_movies_pannelLayout = new javax.swing.GroupLayout(Actor_movies_pannel);
        Actor_movies_pannel.setLayout(Actor_movies_pannelLayout);
        Actor_movies_pannelLayout.setHorizontalGroup(
            Actor_movies_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        Actor_movies_pannelLayout.setVerticalGroup(
            Actor_movies_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 257, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(Actor_movies_pannel);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(30, 90, 940, 150);

        backBt.setBackground(new java.awt.Color(0, 0, 0));
        backBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/backbt (2).png"))); // NOI18N
        backBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtActionPerformed(evt);
            }
        });
        getContentPane().add(backBt);
        backBt.setBounds(10, 10, 75, 30);

        headingLb.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        headingLb.setForeground(new java.awt.Color(252, 196, 25));
        headingLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headingLb.setText("HEADING");
        getContentPane().add(headingLb);
        headingLb.setBounds(300, 0, 360, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtActionPerformed


        if (source.equals("movies")) {
            searchBt.setEnabled(false);
            jScrollPane1.setVisible(true);
            jScrollPane3.setVisible(false);
            String actorSearch = searchBar.getText().replace(" ", "%20");
            movie(actorSearch);
            searchBt.setEnabled(true);

        } else if (source.equals("tv")) {
            jScrollPane3.setVisible(true);
            searchBt.setEnabled(false);
            Actor_movies_pannel.removeAll();
            String movieSearch = searchBar.getText().replace(" ", "%20");
            tv(movieSearch);
            searchBt.setEnabled(true);

        } else if (source.equals("company")) {
            jScrollPane3.setVisible(true);
            Actor_movies_pannel.removeAll();
            searchBt.setEnabled(false);
            String companySearch = searchBar.getText().replace(" ", "%20");
            company(companySearch);
            searchBt.setEnabled(true);
        }
        else if(source.equals("movie_name")){
            searchBt.setEnabled(false);
            jScrollPane3.setVisible(true);
            Actor_movies_pannel.removeAll();
            String moviename = searchBar.getText().replace(" ", "%20");
            movie_name(moviename);
            searchBt.setEnabled(true);
            
        }

    }//GEN-LAST:event_searchBtActionPerformed

    private void backBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtActionPerformed

        new Welcome();
        dispose();
    }//GEN-LAST:event_backBtActionPerformed

    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Actor_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Actor_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Actor_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Actor_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Actor_Search( "tv").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Actor_movies_pannel;
    private javax.swing.JButton backBt;
    private javax.swing.JLabel headingLb;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchBt;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        jScrollPane1.setVisible(false);
        jScrollPane3.setVisible(true);

    }
}
