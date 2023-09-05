
package movie_world;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.net.URI;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AllCatagory extends javax.swing.JFrame {
    
    boolean photoes = dump.render;
    String search = "";
    boolean noresult = false;

    public AllCatagory(String search) {
        initComponents();
        this.search = search;
        setResizable(false);
        setFocusable(true);
        jScrollPane6.setBounds(10, 370, 980, 315);
        jScrollPane2.setBounds(10, 370, 980, 315);
        jScrollPane6.setVisible(false);
        jScrollPane4.setVisible(false);
        jScrollPane5.setVisible(false);
        setSize(1020, 800);
        go();
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
    }
    public boolean hasResult(){
        return !noresult;
    }

    public void go() {
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/multi?api_key=" + api + "&language=en-US&query=" + search + "&page=1&include_adult=false").asString();
            if (response.getStatus() == 200) {
                String body = response.getBody();
                JSONParser parser = new JSONParser();
                JSONObject mainobj = (JSONObject) parser.parse(body);
                JSONArray array = (JSONArray) mainobj.get("results");
                if (array.size() != 0) {
                    int x = 10, y = 10, k = 0, q = 0;
                    int x1 = 10, y1 = 10;
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = (JSONObject) array.get(i);
                        String type = (String) jo.get("media_type");

                        if (type.equals("movie")) {
                            long id = (long) jo.get("id");
                            String overviewString = (String) jo.get("overview");
                            String overview = "";

                            String[] n = overviewString.split("(?<=\\G.{" + 39 + "})");
                            for (String string : n) {
                                overview += string + " " + "\n" + " ";
                            }

                            String Title = (String) jo.get("title");
                            //long popularity = (long)jo.get("popularity");
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

                            movie_pannel.add(mp);
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
                                try {
                                    HttpResponse<String> response1 = Unirest.get("http://localhost:8000/delfavoritemovie").queryString("email", dump.email).queryString("id", id).asString();
                                    if (response1.getStatus() == 200) {
                                        String backf = response1.getBody();
                                        if (backf.contains("ok")) {
                                            JOptionPane.showMessageDialog(this, "Deleted From favorites");

                                            mp.delfavorite.setVisible(false);
                                            mp.favoriteaddBt.setVisible(true);
                                            mp.delfavorite.setVisible(false);
                                            repaint();
                                            mp.repaint();
                                            movie_pannel.repaint();
                                        } else if (backf.equals("fail")) {
                                            JOptionPane.showMessageDialog(this, "something went wrong");
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            );
                            mp.favoriteaddBt.addActionListener((e) -> {
                                System.out.println("this");
                                try {
                                    HttpResponse<String> response1 = Unirest.get("http://localhost:8000/addfavoritemovie").queryString("email", dump.email).queryString("id", id).asString();
                                    if (response1.getStatus() == 200) {
                                        String backf = response1.getBody();
                                        if (backf.contains("ok")) {
                                            JOptionPane.showMessageDialog(this, "Added to favorites");
                                            mp.favoriteaddBt.setVisible(false);
                                            mp.delfavorite.setVisible(true);
                                            repaint();
                                            mp.repaint();
                                            movie_pannel.repaint();
                                        } else if (backf.equals("fail")) {
                                            JOptionPane.showMessageDialog(this, "something went wrong");
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });

                            try {
                                HttpResponse<String> res = Unirest.get("http://localhost:8000/favoritesmovie").queryString("email", dump.email).queryString("id", id).asString();
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
                            movie_pannel.repaint();
                            mp.repaint();
                        } else if (type.equals("tv")) {
                            long id = (long) jo.get("id");
                            String overviewString = (String) jo.get("overview");
                            String overview = "";

                            String[] n = overviewString.split("(?<=\\G.{" + 39 + "})");
                            for (String string : n) {
                                overview += string + " " + "\n" + " ";
                            }

                            String Title = (String) jo.get("name");
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

                            movie_pannel.add(mp);
                            x = x + 320;
                            mp.delfavorite.addActionListener((e) -> {
                                System.out.println("this");
                                try {
                                    HttpResponse<String> response1 = Unirest.get("http://localhost:8000/delfavoritetv").queryString("email", dump.email).queryString("id", id).asString();
                                    if (response1.getStatus() == 200) {
                                        String backf = response1.getBody();
                                        if (backf.contains("ok")) {
                                            JOptionPane.showMessageDialog(this, "Deleted From favorites");

                                            mp.delfavorite.setVisible(false);
                                            mp.favoriteaddBt.setVisible(true);
                                            mp.delfavorite.setVisible(false);
                                            repaint();
                                            mp.repaint();
                                            movie_pannel.repaint();
                                        } else if (backf.equals("fail")) {
                                            JOptionPane.showMessageDialog(this, "something went wrong");
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
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
                                            movie_pannel.repaint();
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
                            movie_pannel.repaint();
                            mp.repaint();
                        } else if (type.equals("person")) {
                            long id = (long) jo.get("id");
                            String name = (String) jo.get("name");

                            Double popularity = 0.0;
                            try {
                                popularity = (Double) jo.get("popularity");
                            } catch (Exception ex) {
                                popularity = 11.0;
                            }
                            String known_for = (String) jo.get("known_for_department");
                            JSONArray allMovies = (JSONArray) jo.get("known_for");
                            q++;
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
                            mp.setBounds(x1, y1, 300, 300);
                            
                            mp.moviesBt.addActionListener((e) -> {
                                jScrollPane2.setVisible(false);

                                int w = 10, r = 10, l = 0;
                                for (int j = 0; j < allMovies.size(); j++) {
                                    l++;
                                    JSONObject joo = (JSONObject) allMovies.get(j);
                                    String moviename = (String) joo.get("title");
                                    String movieRelease = (String) joo.get("release_date");
                                    String photo = (String) joo.get("poster_path");
                                    String overviewString = (String) joo.get("overview");
                                    String overview = "";
                                    String[] n = overviewString.split("(?<=\\G.{" + 39 + "})");
                                    for (String string : n) {
                                        overview += string + " " + "\n" + " ";
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
                                        jScrollPane6.setVisible(false);
                                        jScrollPane2.setVisible(true);
                                        people_movie_pannel.removeAll();
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
                                            person_pannel.repaint();
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
                                           person_pannel.repaint();
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
                                    people_movie_pannel.add(mv);
                                    mv.repaint();
                                    people_movie_pannel.repaint();

                                }
                                int h = 320 * l;
                                people_movie_pannel.setPreferredSize(new Dimension(h, 320));
                                jScrollPane6.setVisible(true);

                            });

                            person_pannel.add(mp);
                            x1 = x1 + 320;
                            repaint();
                            person_pannel.repaint();
                            mp.repaint();

                        }

                    }
                    int size = 320 * k;

                    movie_pannel.setPreferredSize(new Dimension(size, 320));

                    int size1 = 320 * q;

                    person_pannel.setPreferredSize(new Dimension(size1, 320));
                    System.out.println(k);
                    if (k == 0) {
                        jScrollPane1.setVisible(false);
                        jScrollPane2.setBounds(10, 30, 980, 320);
                        jScrollPane6.setBounds(10, 30, 980, 320);
                        setSize(1020, 400);
                        moviesLb.setText("ACTORS");
                        actorLb.setVisible(false);
                    } else if (q == 0) {
                        jScrollPane2.setVisible(false);
                        setSize(1020, 400);
                        actorLb.setVisible(false);
                    }
                } else {
                    noresult = true;

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        moviesLb = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        movie_pannel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        person_pannel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        people_movie_pannel = new javax.swing.JPanel();
        actorLb = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        moviesLb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        moviesLb.setForeground(new java.awt.Color(255, 196, 25));
        moviesLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        moviesLb.setText("MOVIES & TV SHOWS");
        getContentPane().add(moviesLb);
        moviesLb.setBounds(430, 10, 210, 20);

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/backbt (2).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(0, 0, 80, 30);

        movie_pannel.setBackground(new java.awt.Color(0, 0, 0));
        movie_pannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));

        javax.swing.GroupLayout movie_pannelLayout = new javax.swing.GroupLayout(movie_pannel);
        movie_pannel.setLayout(movie_pannelLayout);
        movie_pannelLayout.setHorizontalGroup(
            movie_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 974, Short.MAX_VALUE)
        );
        movie_pannelLayout.setVerticalGroup(
            movie_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(movie_pannel);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 980, 320);

        person_pannel.setBackground(new java.awt.Color(0, 0, 0));
        person_pannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));

        javax.swing.GroupLayout person_pannelLayout = new javax.swing.GroupLayout(person_pannel);
        person_pannel.setLayout(person_pannelLayout);
        person_pannelLayout.setHorizontalGroup(
            person_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 974, Short.MAX_VALUE)
        );
        person_pannelLayout.setVerticalGroup(
            person_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(person_pannel);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 370, 980, 310);
        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(890, -30, 2, 2);
        getContentPane().add(jScrollPane5);
        jScrollPane5.setBounds(900, -30, 2, 2);

        people_movie_pannel.setBackground(new java.awt.Color(0, 0, 0));
        people_movie_pannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));

        javax.swing.GroupLayout people_movie_pannelLayout = new javax.swing.GroupLayout(people_movie_pannel);
        people_movie_pannel.setLayout(people_movie_pannelLayout);
        people_movie_pannelLayout.setHorizontalGroup(
            people_movie_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );
        people_movie_pannelLayout.setVerticalGroup(
            people_movie_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );

        jScrollPane6.setViewportView(people_movie_pannel);

        getContentPane().add(jScrollPane6);
        jScrollPane6.setBounds(690, 0, 70, 30);

        actorLb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        actorLb.setForeground(new java.awt.Color(255, 196, 25));
        actorLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        actorLb.setText("ACTORS");
        getContentPane().add(actorLb);
        actorLb.setBounds(440, 346, 188, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        new Welcome();
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllCatagory("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel actorLb;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel movie_pannel;
    private javax.swing.JLabel moviesLb;
    private javax.swing.JPanel people_movie_pannel;
    private javax.swing.JPanel person_pannel;
    // End of variables declaration//GEN-END:variables
}
