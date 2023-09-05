/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FavoriteForm extends javax.swing.JFrame {

    boolean photoes = dump.render;

    public FavoriteForm(String source) {

        initComponents();
        getContentPane().setBackground(Color.BLACK);
        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusable(true);
        if(source.equals("movie")){
        movie();
        }
        else if(source.equals("tv")){
            tv();
        }
        else if(source.equals("actor")){
            
        }
       
        setVisible(true);
    }
    
    public void tv(){
         ArrayList<String> arr = new ArrayList<>();
        try {
            HttpResponse<String> res = Unirest.get("http://localhost:8000/getfavoritetv").queryString("email", dump.email).asString();
            if (res.getStatus() == 200) {
                String back = res.getBody();
                StringTokenizer st = new StringTokenizer(back, "~");
                System.out.println(st.countTokens());
                while (st.hasMoreTokens()) {
                    arr.add(st.nextToken());
                }
                int x = 10, y = 10, k = 0;
                for (int i = 0; i < arr.size(); i++) {
                    String api = "980d96176457a6e65b8bc282bcadccd4";
                    String ids = arr.get(i);

                    try {
                        HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/tv/" + ids + "?api_key=" + api + "&language=en-US").asString();
                        if (response.getStatus() == 200) {
                            String body = response.getBody();
                            JSONParser parser = new JSONParser();
                            JSONObject jo = (JSONObject) parser.parse(body);

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

                       mainPannel.add(mp);
                        x = x + 320;
                         mp.delfavorite.addActionListener((e) -> {
                                System.out.println("this");
                          try{
                              HttpResponse<String> response1 = Unirest.get("http://localhost:8000/delfavoritetv").queryString("email",dump.email).queryString("id",id).asString();
                              if(response1.getStatus()==200){
                                  String backf = response1.getBody();
                                  if(backf.contains("ok")){
                                      JOptionPane.showMessageDialog(this, "Deleted From favorites");
                                     
                                      mp.delfavorite.setVisible(false);
                                      mp.favoriteaddBt.setVisible(true);
                                      mp.delfavorite.setVisible(false);
                                      repaint();
                                      mp.repaint();
                                      mainPannel.repaint();
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
                                        mainPannel.repaint();
                                    } else if (backf.equals("fail")) {
                                        JOptionPane.showMessageDialog(this, "something went wrong");
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });

                        try {
                            HttpResponse<String> rest = Unirest.get("http://localhost:8000/favoritestv").queryString("email", dump.email).queryString("id", id).asString();
                            if (rest.getStatus() == 200) {
                                String rev = rest.getBody();
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
                        mainPannel.repaint();
                        mp.repaint();


                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                int size = 320 * k;
                mainPannel.setPreferredSize(new Dimension(size, 320));

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void movie() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            HttpResponse<String> res = Unirest.get("http://localhost:8000/getfavoritemovies").queryString("email", dump.email).asString();
            if (res.getStatus() == 200) {
                String back = res.getBody();
                StringTokenizer st = new StringTokenizer(back, "~");
                while (st.hasMoreTokens()) {
                    arr.add(st.nextToken());
                }
                int x = 10, y = 10, k = 0;
                for (int i = 0; i < arr.size(); i++) {
                    String api = "980d96176457a6e65b8bc282bcadccd4";
                    String ids = arr.get(i);

                    try {
                        HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/movie/" + ids + "?api_key=" + api + "&language=en-US").asString();
                        if (response.getStatus() == 200) {
                            String body = response.getBody();
                            JSONParser parser = new JSONParser();
                            JSONObject jo = (JSONObject) parser.parse(body);

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
                                ImageIcon ic = new ImageIcon(new URL("https://image.tmdb.org/t/p/w200" + photo));
                                Image img = ic.getImage().getScaledInstance(mp.photoLb.getWidth(), mp.photoLb.getHeight(), Image.SCALE_SMOOTH);
                                ImageIcon ic1 = new ImageIcon(img);

                                mp.photoLb.setIcon(ic1);

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
                                            mainPannel.repaint();
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
                                            mainPannel.repaint();
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
                                        mp.favoriteaddBt.setVisible(false);

                                    } else if (rev.contains("new")) {
                                        mp.delfavorite.setVisible(false);

                                    }

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            mainPannel.add(mp);
                            x = x + 320;

                            repaint();
                            mainPannel.repaint();
                            mp.repaint();

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                int size = 320 * k;
                mainPannel.setPreferredSize(new Dimension(size, 320));

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainPannel = new javax.swing.JPanel();
        favoriteLb = new javax.swing.JLabel();
        backBt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPannel.setBackground(new java.awt.Color(0, 0, 0));
        mainPannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        javax.swing.GroupLayout mainPannelLayout = new javax.swing.GroupLayout(mainPannel);
        mainPannel.setLayout(mainPannelLayout);
        mainPannelLayout.setHorizontalGroup(
            mainPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        mainPannelLayout.setVerticalGroup(
            mainPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(mainPannel);

        favoriteLb.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        favoriteLb.setForeground(new java.awt.Color(252, 196, 25));
        favoriteLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        favoriteLb.setText("WISHLIST");

        backBt.setBackground(new java.awt.Color(0, 0, 0));
        backBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/backbt (2).png"))); // NOI18N
        backBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 963, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBt, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(201, 201, 201)
                        .addComponent(favoriteLb, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(favoriteLb, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(backBt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtActionPerformed
        // TODO add your handling code here:
        new UserDetails();
        dispose();
    }//GEN-LAST:event_backBtActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FavoriteForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FavoriteForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FavoriteForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FavoriteForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FavoriteForm("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBt;
    private javax.swing.JLabel favoriteLb;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPannel;
    // End of variables declaration//GEN-END:variables
}
