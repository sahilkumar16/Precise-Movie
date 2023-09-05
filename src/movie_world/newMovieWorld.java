package movie_world;                                                                         
                                                                                
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import java.awt.Color;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class newMovieWorld extends javax.swing.JFrame {                         
                                                                                
    public newMovieWorld() {                                                    
                                                  
                                                        
        initComponents();  
        setSize(550,550);
        getContentPane().setBackground(new Color(0,0,0));
        ImageIcon ic = new ImageIcon(getClass().getResource("/icons/heading.png"));
        Image img = ic.getImage().getScaledInstance(headingLb.getWidth(), headingLb.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img);
        headingLb.setIcon(ic1);
        setFocusable(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }                                                                           
    @SuppressWarnings("unchecked")                                              
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headingLb = new javax.swing.JLabel();
        loginBt = new javax.swing.JButton();
        signupbt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        headingLb.setBackground(new java.awt.Color(255, 255, 255));
        headingLb.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        headingLb.setForeground(new java.awt.Color(252, 196, 25));
        headingLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(headingLb);
        headingLb.setBounds(0, 10, 560, 240);

        loginBt.setBackground(new java.awt.Color(252, 196, 25));
        loginBt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        loginBt.setText("LOGIN ");
        loginBt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        loginBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtActionPerformed(evt);
            }
        });
        getContentPane().add(loginBt);
        loginBt.setBounds(180, 360, 160, 40);

        signupbt.setBackground(new java.awt.Color(252, 196, 25));
        signupbt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        signupbt.setText("SIGN UP");
        signupbt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        signupbt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupbtMouseEntered(evt);
            }
        });
        signupbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupbtActionPerformed(evt);
            }
        });
        getContentPane().add(signupbt);
        signupbt.setBounds(180, 430, 160, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents
                                                                                 
    private void loginBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtActionPerformed
    try{
        File f = new File("src/dump");
        if(f.exists()){
            System.out.println("kkk");
            
            HttpResponse response = Unirest.get("http://localhost:8000/alreadylogged").asString();
            if(response.getStatus()==200){
            
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            String email = dis.readUTF();
            String username = dis.readUTF();
            
            fis.close();
            dis.close();
            
            dump.email=email;
            dump.user=username;
            
            new Welcome();
            dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "server down");
            }
        }
         else{
        new Login();    
        dispose();
            }                                                                       
    }   
    
    catch(Exception ex){
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "some issue");
    }
        
                                                                    
    }//GEN-LAST:event_loginBtActionPerformed
                                                                                
    private void signupbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupbtActionPerformed
      new userSignup(); 
      
      dispose();
    }//GEN-LAST:event_signupbtActionPerformed

    private void signupbtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupbtMouseEntered
        // TODO add your handling code here:
     
    }//GEN-LAST:event_signupbtMouseEntered
                                                                                
    public static void main(String args[]) {                                    
                                                                                
        java.awt.EventQueue.invokeLater(new Runnable() {                        
            public void run() {                                                  
                new newMovieWorld().setVisible(true);                         
            }                                                                   
        });                                                                             
    }                                                                           
                                                                                                   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headingLb;
    private javax.swing.JButton loginBt;
    private javax.swing.JButton signupbt;
    // End of variables declaration//GEN-END:variables
}                                                                               
