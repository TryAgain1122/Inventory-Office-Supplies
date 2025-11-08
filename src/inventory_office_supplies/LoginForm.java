/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_office_supplies;

import Database_config.DbConnection;
import Styles.GradientPanel;
import Styles.ModalCustom;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author luisr
 */
public class LoginForm extends javax.swing.JFrame {
    
public LoginForm() {
    initComponents();
    
    //For username ICon 
    ImageIcon iconUsername = new ImageIcon(getClass().getResource("../Images/user.png"));
    Image userImg = iconUsername.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    lblUsername.setIcon(new ImageIcon(userImg));
    
    //For Password Icon
    ImageIcon iconPassword = new ImageIcon(getClass().getResource("../Images/password.png"));
    Image passwordImg = iconPassword.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    lblPassword.setIcon(new ImageIcon(passwordImg));
    
    // 🌈 Gradient background setup (keep this)
    GradientPanel gradient = new GradientPanel();
    gradient.setLayout(new BorderLayout());
    jPanel1.setOpaque(false);
    gradient.add(jPanel1, BorderLayout.CENTER);
    setContentPane(gradient);
    
    //Icon 
    
    // 🧩 Placeholder text
    txtUsername.putClientProperty("JTextField.placeholderText", "Enter username");
    txtPassword.putClientProperty("JTextField.placeholderText", "Enter password");

    // 🧩 Remove rounded corners (underline style only)
    txtUsername.putClientProperty("JComponent.roundRect", false);
    txtPassword.putClientProperty("JComponent.roundRect", false);

    // ⚠️ FIXED: Typo here — should be “showClearButton”, not “show6ClearButton”
    txtUsername.putClientProperty("JTextField.showClearButton", true);
    txtPassword.putClientProperty("JTextField.showClearButton", true);

    // 🖤 Default underline (light gray when not focused)
    Color unfocusedLine = new Color(120, 120, 120); // light gray line
    Color focusedLine = new Color(30, 30, 30);      // dark black line

    txtUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, unfocusedLine));
    txtPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, unfocusedLine));

    // 🧠 Transparent background for cleaner look
    txtUsername.setOpaque(false);
    txtPassword.setOpaque(false);
    txtUsername.setBackground(new Color(0, 0, 0, 0));
    txtPassword.setBackground(new Color(0, 0, 0, 0));
    
    btnLogin.putClientProperty("JButton.buttonType", "roundRect");
    btnLogin.setBackground(new java.awt.Color(45, 45, 45)); // dark gray
    btnLogin.setForeground(new java.awt.Color(255, 255, 255)); // white text
    btnLogin.setFocusPainted(false);
    btnLogin.setBorderPainted(false);
    btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
//    btnLogin.putClientProperty("JButton.buttonType", "roundRect");
//    btnLogin.putClientProperty("JButton.focusedBackground", new Color(45, 45, 45));
//    btnLogin.putClientProperty("JButton.hoverBackground", new Color(33, 150, 243));
//    btnLogin.putClientProperty("JButton.pressedBackground", new Color(25, 118, 210));
//    btnLogin.putClientProperty("JButton.selectedBackground", new Color(33, 150, 243));
//    btnLogin.putClientProperty("JButton.background", new Color(40, 40, 40));
//    
//    btnLogin.setForeground(Color.BLACK);
//    btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
//    btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    // ✨ Focus effects
    txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            txtUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, focusedLine));
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            txtUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, unfocusedLine));
        }
    });

    txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            txtPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, focusedLine));
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            txtPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, unfocusedLine));
        }
    });

    revalidate();
    repaint();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblUsername.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        lblUsername.setText("Username");

        lblPassword.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        lblPassword.setText("Password");

        txtUsername.setBackground(new java.awt.Color(207, 207, 207));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 36)); // NOI18N
        jLabel3.setText("WELCOME");

        btnLogin.setBackground(new java.awt.Color(207, 207, 207));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogin.setText("LOG IN");
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Forget Password ");

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jCheckBox1.setText("Remember Me");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(267, 267, 267)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel3)
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            ModalCustom.showWarning("Please enter both and username and password", "Missing fields");
            return;
        }
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String sql = "SELECT * FROM users_tbl WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                String fullname = rs.getString("fullname");
                ModalCustom.showStyledInfo("Welcome " + fullname + " (" + role + ")", "Login Successfully");
                
                if (role.equalsIgnoreCase("Admin")) {
                    new AdminDashboard().setVisible(true);
   
                } else if (role.equalsIgnoreCase("Staff")) {
                    new StaffDashboard().setVisible(true);
                }
                this.dispose();
            } 
             else {
               ModalCustom.showError("Invalid username or password", "Login Failed");
            }
            
            rs.close();
            pst.close();
            db.closeConnection();
        } catch (SQLException e) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        
        
    }//GEN-LAST:event_btnLoginMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
//        try { 
//             FlatLightLaf.setup();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
