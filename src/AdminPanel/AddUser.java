/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminPanel;

import Database_config.DbConnection;
import Styles.ButtonStyles;
import Styles.GradientPanel;
import Styles.TextFieldStyle;
import com.formdev.flatlaf.FlatLightLaf;
import inventory_office_supplies.AdminDashboard;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author luisr
 */
public class AddUser extends javax.swing.JFrame {

    /**
     * Creates new form AddUser
     */
    public AddUser() {
        initComponents();
        
        GradientPanel gradient = new GradientPanel();
        gradient.setLayout(new BorderLayout());
        jPanel1.setOpaque(false);
        gradient.add(jPanel1, BorderLayout.CENTER);
        setContentPane(gradient);
        
        //For Placeholder 
        TextFieldStyle.applyUnderlineStyle(txtAddFullname, "Enter Fullname");
        TextFieldStyle.applyUnderlineStyle(txtEmail, "Enter Email");
        TextFieldStyle.applyUnderlineStyle(txtUsername, "Enter Username");
        TextFieldStyle.applyUnderlineStyle(txtAddPassword, "Enter Password");
        
        //combox box style 
        selectRole.putClientProperty("JComponent.roundRect", true);
        selectRole.setBackground(new java.awt.Color(180, 180, 180));
        selectRole.setForeground(new java.awt.Color(30, 30, 30));
        selectRole.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        selectRole.setFocusable(false);
        selectRole.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        selectRole.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] {"Select Category", "Admin", "Staff"}
        ));
        selectRole.setSelectedIndex(0);
        
        ButtonStyles.setDark(btnAdd);
        ButtonStyles.setDelete(btnCancel);
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtAddFullname = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        selectRole = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtAddPassword = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtAddFullname.setBackground(new java.awt.Color(207, 207, 207));
        txtAddFullname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtAddFullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddFullnameActionPerformed(evt);
            }
        });

        txtUsername.setBackground(new java.awt.Color(207, 207, 207));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        selectRole.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Staff" }));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("ADD USER");

        txtEmail.setBackground(new java.awt.Color(207, 207, 207));
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(selectRole, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAddFullname)
                            .addComponent(txtAddPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(txtUsername)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(77, 77, 77))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addComponent(txtAddFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(txtAddPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(selectRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddFullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddFullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddFullnameActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String fullname = txtAddFullname.getText().trim();
        String username = txtUsername.getText(); 
        String password = new String(txtAddPassword.getPassword());
        String role = (String) this.selectRole.getSelectedItem().toString();
        String email = txtEmail.getText();
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }
        
        if ("Select Category".equals(selectRole)) {
            javax.swing.JOptionPane.showMessageDialog(
            this,
            "Please select a role before adding the user.",
            "Missing Role",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
          return;
        }
       
//        if (!email.matches("@")) {
//            JOptionPane.showMessageDialog(this, "Invalid email");
//            return;
//        }
        try {
            String sql = "INSERT INTO users_tbl (username, password, role, fullname, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            pst.setString(4, fullname);
            pst.setString(5, email);
            
            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User Added Successfully");
//                new AdminDashboard().setVisible(true);
                this.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }

           
//            System.out.println("Fullname: " + fullname);
//            System.out.println("username: " + username);
//            System.out.println("password: " + password);
//            System.out.println("Role Selected: " + selectRole);
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
//        new AdminDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed


    public static void main(String args[]) {
      
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> selectRole;
    private javax.swing.JTextField txtAddFullname;
    private javax.swing.JPasswordField txtAddPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
