
package movie_world;

import com.mashape.unirest.http.*;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setSize(500,500);
        getContentPane().setBackground(Color.BLACK);
        setFocusable(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        emailLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        emailtf = new javax.swing.JTextField();
        passwordtf = new javax.swing.JPasswordField();
        loginbt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        forgotPasswordBt = new javax.swing.JButton();
        backbt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        emailLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emailLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email.png"))); // NOI18N
        getContentPane().add(emailLabel);
        emailLabel.setBounds(20, 100, 50, 50);

        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/password (2).png"))); // NOI18N
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(20, 160, 50, 50);

        emailtf.setBackground(new java.awt.Color(0, 0, 0));
        emailtf.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        emailtf.setForeground(new java.awt.Color(255, 255, 255));
        emailtf.setToolTipText("fcfcc");
        emailtf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        emailtf.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(emailtf);
        emailtf.setBounds(80, 110, 340, 30);

        passwordtf.setBackground(new java.awt.Color(0, 0, 0));
        passwordtf.setForeground(new java.awt.Color(255, 255, 255));
        passwordtf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        passwordtf.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(passwordtf);
        passwordtf.setBounds(80, 170, 340, 30);

        loginbt.setBackground(new java.awt.Color(252, 196, 25));
        loginbt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        loginbt.setText("LOGIN");
        loginbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginbtActionPerformed(evt);
            }
        });
        getContentPane().add(loginbt);
        loginbt.setBounds(190, 280, 90, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 196, 25));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LOGIN");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(130, 10, 210, 50);

        forgotPasswordBt.setBackground(new java.awt.Color(0, 0, 0));
        forgotPasswordBt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        forgotPasswordBt.setForeground(new java.awt.Color(252, 196, 25));
        forgotPasswordBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/forgotpassword.png"))); // NOI18N
        forgotPasswordBt.setText("Forgot password ?");
        forgotPasswordBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotPasswordBtActionPerformed(evt);
            }
        });
        getContentPane().add(forgotPasswordBt);
        forgotPasswordBt.setBounds(130, 340, 210, 40);

        backbt.setBackground(new java.awt.Color(0, 0, 0));
        backbt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/backbt (2).png"))); // NOI18N
        backbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backbtActionPerformed(evt);
            }
        });
        getContentPane().add(backbt);
        backbt.setBounds(10, 10, 50, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginbtActionPerformed
        // TODO add your handling code here:
        
       String email = emailtf.getText();
       String password = passwordtf.getText();
       
       if(email.equals("")||password.equals("")){
           JOptionPane.showMessageDialog(this, "Kindly Enter Both Fields");
       }
       else{
           try{
            HttpResponse<String> res = Unirest.get("http://localhost:8000/checklogin").queryString("email",email).queryString("password",password).asString();
            
           
            
            if(res.getStatus()==200){
                 String back = res.getBody();
                 
                 if(back.contains("Login Sucess")){
                     JOptionPane.showMessageDialog(this, "Welcome");
                     String username="";
                     String emaillString="";
                    
                    StringTokenizer st = new StringTokenizer(back);
                    int stSize = st.countTokens();
                     for (int i = 0; i<=stSize; i++) {
                         
                         if(i==0||i==1) {st.nextToken();continue;}
                         if (i==2){ username = st.nextToken();}
                         if(i==3) {emaillString = st.nextToken();}
                         
                         
                     }
                         dump.email=emaillString;
                         dump.user =username;
                         
                         try{
                             FileOutputStream fos = new FileOutputStream("src/dump");
                             DataOutputStream dos = new DataOutputStream(fos);
                             dos.writeUTF(emaillString);
                             dos.writeUTF(username);
                             fos.close();
                             dos.close();
                         }
                         catch(Exception ex){
                             ex.printStackTrace();
                         }
                         
                         
                         
                         
                    dispose();
                    new Welcome();
                     
                    
                 }
                 else if(back.contains("Login Failed")){
                            JOptionPane.showMessageDialog(this, "Login Failed");
                 }
                 else{
                     JOptionPane.showMessageDialog(this, "Something went wrong");
                 }
            }
            else{
                JOptionPane.showMessageDialog(this, "Something went wrong");
            }
           }
           catch(Exception ex){
             //  ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "server is not started");
           }
       }
    }//GEN-LAST:event_loginbtActionPerformed

    private void forgotPasswordBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forgotPasswordBtActionPerformed
        // TODO add your handling code here:
        new changeFrame("password");
    }//GEN-LAST:event_forgotPasswordBtActionPerformed

    private void backbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backbtActionPerformed
        // TODO add your handling code here:
        new newMovieWorld();
        dispose();
        
    }//GEN-LAST:event_backbtActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backbt;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailtf;
    private javax.swing.JButton forgotPasswordBt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginbt;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordtf;
    // End of variables declaration//GEN-END:variables
}
