package movie_world;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class userSignup extends javax.swing.JFrame {

    JFileChooser jfc;
    String path;

    public userSignup() {
        initComponents();
        setSize(580, 530);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        usernameLabel = new javax.swing.JLabel();
        mobile = new javax.swing.JTextField();
        mobileLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        address = new javax.swing.JTextArea();
        photoLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        filechooser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        backBt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        usernameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(252, 196, 25));
        usernameLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/username.png"))); // NOI18N
        usernameLabel.setText("USERNAME");
        getContentPane().add(usernameLabel);
        usernameLabel.setBounds(40, 80, 130, 40);

        mobile.setBackground(new java.awt.Color(0, 0, 0));
        mobile.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mobile.setForeground(new java.awt.Color(255, 255, 255));
        mobile.setToolTipText("");
        mobile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        mobile.setCaretColor(new java.awt.Color(255, 255, 255));
        mobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobileActionPerformed(evt);
            }
        });
        getContentPane().add(mobile);
        mobile.setBounds(270, 240, 260, 30);

        mobileLabel.setBackground(new java.awt.Color(0, 0, 0));
        mobileLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mobileLabel.setForeground(new java.awt.Color(252, 196, 25));
        mobileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/phone.png"))); // NOI18N
        mobileLabel.setText("MOBILE");
        getContentPane().add(mobileLabel);
        mobileLabel.setBounds(40, 240, 140, 30);

        emailLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 196, 25));
        emailLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email.png"))); // NOI18N
        emailLabel.setText("EMAIL");
        getContentPane().add(emailLabel);
        emailLabel.setBounds(40, 140, 130, 30);

        addressLabel.setBackground(new java.awt.Color(0, 0, 0));
        addressLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addressLabel.setForeground(new java.awt.Color(252, 196, 25));
        addressLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-home-address-30.png"))); // NOI18N
        addressLabel.setText("ADDRESS");
        getContentPane().add(addressLabel);
        addressLabel.setBounds(40, 300, 130, 30);

        passwordLabel.setBackground(new java.awt.Color(0, 0, 0));
        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(255, 196, 25));
        passwordLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/password (2).png"))); // NOI18N
        passwordLabel.setText("PASSWORD");
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(40, 190, 140, 30);

        email.setBackground(new java.awt.Color(0, 0, 0));
        email.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        email.setCaretColor(new java.awt.Color(255, 255, 255));
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        getContentPane().add(email);
        email.setBounds(270, 140, 260, 30);

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        username.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(username);
        username.setBounds(270, 90, 260, 30);

        password.setBackground(new java.awt.Color(0, 0, 0));
        password.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        password.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(password);
        password.setBounds(270, 190, 260, 30);

        address.setBackground(new java.awt.Color(0, 0, 0));
        address.setColumns(20);
        address.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        address.setForeground(new java.awt.Color(255, 255, 255));
        address.setRows(5);
        address.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        address.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(address);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(270, 280, 260, 83);

        photoLabel.setBackground(new java.awt.Color(0, 0, 0));
        photoLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        photoLabel.setForeground(new java.awt.Color(252, 196, 25));
        photoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-camera-30.png"))); // NOI18N
        photoLabel.setText("PHOTO");
        getContentPane().add(photoLabel);
        photoLabel.setBounds(50, 360, 100, 80);

        jButton1.setBackground(new java.awt.Color(252, 196, 25));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("SIGNUP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(210, 450, 130, 30);

        filechooser.setBackground(new java.awt.Color(0, 0, 0));
        filechooser.setForeground(new java.awt.Color(252, 196, 25));
        filechooser.setText("Choose file");
        filechooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filechooserActionPerformed(evt);
            }
        });
        getContentPane().add(filechooser);
        filechooser.setBounds(360, 380, 130, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(252, 196, 25));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIGN UP");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(90, 10, 350, 58);

        backBt.setBackground(new java.awt.Color(0, 0, 0));
        backBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/backbt (2).png"))); // NOI18N
        backBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtActionPerformed(evt);
            }
        });
        getContentPane().add(backBt);
        backBt.setBounds(10, 10, 36, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String username = this.username.getText();
        String emailid = this.email.getText();
        String password = this.password.getText();
        String mobilenum = this.mobile.getText();
        String addresstext = this.address.getText();
        try {
            if (username.equals("") || password.equals("") || emailid.equals("") || mobilenum.equals("") || addresstext.equals("") || path.equals("")) {
                JOptionPane.showMessageDialog(this, "ALL FIELDS ARE MANDATORY");
            } else if (!emailid.contains("@")) {
                JOptionPane.showMessageDialog(this, "Enter valid email id");
            } else if (password.length() <= 8) {
                JOptionPane.showMessageDialog(this, "Password Should be atleast 8 Digit long!!");
            } else if (!checkMobile(mobilenum)) {
                JOptionPane.showMessageDialog(this, "Incorrect mobile number");
            } else {
                HttpResponse<String> response = Unirest.post("http://localhost:8000/signup").queryString("username", username).queryString("email", emailid).queryString("password", password).queryString("phone", mobilenum).queryString("address", addresstext).field("photo", new File(path)).asString();

                if (response.getStatus() == 200) {
                    String back = response.getBody();
                    if (back.contains("Email Already Registered")) {
                        JOptionPane.showMessageDialog(this, "Email Already Exists Try another Email");

                    } else if (back.contains("Signed Up Successfully !!!")) {
                      
                        File f = new File("src/dump");
                        f.delete();
                        dump.email = "";
                        dump.user = "";
                        dump.render = true;
                          JOptionPane.showMessageDialog(this, "User created succesfully");
                        new newMovieWorld();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "something went wrong try again");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Server error");
                }

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "server is not started");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void filechooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filechooserActionPerformed
        // TODO add your handling code here:
        jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File f = jfc.getSelectedFile();
        path = f.getPath();
        ImageIcon ic = new ImageIcon(path);
        Image img = ic.getImage().getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img);

        photoLabel.setIcon(ic1);

    }//GEN-LAST:event_filechooserActionPerformed

    private void mobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobileActionPerformed

    private void backBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backBtActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    public boolean checkMobile(String mobile) {
        if (mobile.length() != 10) {
            return false;
        }
        boolean ok = true;

        for (int i = 0; i < mobile.length(); i++) {
            char ch = mobile.charAt(i);

            if (!Character.isDigit(ch)) {
                ok = false;
                break;
            }

        }
        return ok;
    }

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
            java.util.logging.Logger.getLogger(userSignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userSignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userSignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userSignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userSignup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea address;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JButton backBt;
    private javax.swing.JTextField email;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton filechooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mobile;
    private javax.swing.JLabel mobileLabel;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JTextField username;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
