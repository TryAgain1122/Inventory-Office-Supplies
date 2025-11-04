/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminPanel;

import Database_config.DbConnection;
import Styles.ButtonStyles;
import Styles.GradientPanel;
import Styles.ModalCustom;
import Styles.TextFieldStyle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luisr
 */
public class UserPanel extends javax.swing.JPanel {

    /**
     * Creates new form UserPanel
     */
    public UserPanel() {
        initComponents();
        
        loadUsersTable();
        loadStaffandAdmin();
       
        

                // Create gradient background
        GradientPanel gradient = new GradientPanel();
        gradient.setLayout(new BorderLayout());
//        gradient.setGradientColors(new java.awt.Color(102, 153, 255), new java.awt.Color(204, 255, 255));
        jPanel1.setOpaque(false);

        gradient.add(jPanel1, BorderLayout.CENTER);

//        setContentPane(gradient);

        
        TextFieldStyle.SearchStyle(txtSearch, "Search...");
        
        ButtonStyles.setAdd(btnSearch);
        ButtonStyles.setDark(btnAdd);
        ButtonStyles.setDelete(btnDelete);
        ButtonStyles.setEdit(btnUpdate);
        
        
        initCustomBehavior();

        revalidate();
        repaint();
    }
    
    public void loadStaffandAdmin() {
        DbConnection db = new DbConnection();
        Connection conn;
        PreparedStatement pst;
        ResultSet rst;
        
        try {
            conn = db.getConnection();
            // Count Staff role
            String staffSql = "SELECT COUNT(*) AS totalStaff FROM users_tbl WHERE role = 'Staff'";
            pst = conn.prepareStatement(staffSql);
            rst = pst.executeQuery();
            if (rst.next()) {
                int staffCount = rst.getInt("totalStaff");
                txtStaff.setText(String.valueOf(staffCount));
            }
            rst.close();
            pst.close();
            
            //Count Admin role 
            
            String adminSql = "SELECT COUNT(*) AS totalAdmin FROM users_tbl WHERE role = 'Admin'";
            pst = conn.prepareStatement(adminSql);
            rst = pst.executeQuery();
            if (rst.next()) {
                int adminCount = rst.getInt("totalAdmin");
                txtAdmin.setText(String.valueOf(adminCount));
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this,"Database Error: " + e.getMessage());
        }
    }
    
    

    public void loadUsersTable() {
        
        String[] columnNames = {"ID", "Fullname", "Email", "Username", "Password", "Role"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        DbConnection db = new DbConnection();
        Connection conn;
        PreparedStatement pst;
        ResultSet rst;
        try {
            conn = db.getConnection();
            String sql = "SELECT user_id, fullname, email, username, password, role from users_tbl";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            
            while (rst.next()) {
                int id = rst.getInt("user_id");
                String fullname = rst.getString("fullname");
                String email = rst.getString("email");
                String username = rst.getString("username");
                String password = rst.getString("password");
                String role = rst.getString("role");
                
                model.addRow(new Object[]{id, fullname, email, username, password, role});
            }
               tblUsers.setModel(model);
               rst.close();
               pst.close();
               conn.close();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
        
        tblUsers.setRowHeight(40);
        tblUsers.setGridColor(new Color(220, 220, 220));
        tblUsers.setShowVerticalLines(true);
        tblUsers.setShowHorizontalLines(true);
        tblUsers.setSelectionBackground(new Color(180, 180, 180));
        tblUsers.setSelectionForeground(Color.WHITE);
        
        tblUsers.getTableHeader().setBackground(new Color(60, 60, 60));
        tblUsers.getTableHeader().setForeground(Color.WHITE);
        tblUsers.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
    }
    
    public void refreshUsersTable () {
        loadUsersTable();
    }
    
    private void initCustomBehavior () {
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        tblUsers.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    boolean hasSelection = tblUsers.getSelectedRow() != -1;
                    btnUpdate.setEnabled(hasSelection);
                    btnDelete.setEnabled(hasSelection);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtStaff = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtAdmin = new javax.swing.JLabel();
        lblIcon1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setText("Users");

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtStaff.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtStaff.setText("0");

        lblIcon.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("Total Staff");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblIcon)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtStaff))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStaff)
                    .addComponent(lblIcon))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        tblUsers.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblUsers);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtAdmin.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtAdmin.setText("0");

        lblIcon1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Total Admin");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblIcon1)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtAdmin))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAdmin)
                    .addComponent(lblIcon1))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnSearch.setText("Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        new AddUser().setVisible(true);
//        this.dispose();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectRow = tblUsers.getSelectedRow();

        if (selectRow == -1) {
            ModalCustom.showWarning("PLease select a user to update", "No Selection");
            return;
        }

        boolean confirm = ModalCustom.showConfirm("DO you want to edit this user?", "Confirm Action");
        if (!confirm) return;

        int userId = (int) tblUsers.getValueAt(selectRow, 0);
        String fullname = tblUsers.getValueAt(selectRow, 1).toString();
        String email = tblUsers.getValueAt(selectRow, 2).toString();
        String username = tblUsers.getValueAt(selectRow, 3).toString();
        String password = tblUsers.getValueAt(selectRow, 4).toString();
        String role = tblUsers.getValueAt(selectRow, 5).toString();

        EditUsers editForm = new EditUsers(fullname, email, username, password, role, userId, this);
        editForm.setVisible(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        int selectedRow = tblUsers.getSelectedRow();

        if (selectedRow == - 1) {
            ModalCustom.showWarning("Please select a user to delete", "No Selection");
            return;
        }

        int userId = (int) tblUsers.getValueAt(selectedRow, 0);
        String fullname = tblUsers.getValueAt(selectedRow, 1).toString();

        boolean confirm = ModalCustom.showConfirm("Are you sure you want to delete user \"" + fullname + "\"?", "Confirm Deletion");

        if (confirm) {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            PreparedStatement pst;

            try {
                String sql = "DELETE FROM users_tbl WHERE user_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, userId);

                int rowsAffected = pst.executeUpdate();
                pst.close();
                db.closeConnection();

                if (rowsAffected > 0) {
                    ModalCustom.showInfo("User \"" + fullname, "\" has been deleted successfully");
                    loadUsersTable();
                    loadStaffandAdmin();
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                } else {
                    ModalCustom.showError("Failed to delete user. Please try again.", "Error");
                }
            } catch(SQLException e) {
                ModalCustom.showError("Database error: " + e.getMessage(), "Database Error");
            }
        } else {
            ModalCustom.showInfo("Deletion cancelled", "Action cancelled");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblIcon1;
    private javax.swing.JTable tblUsers;
    private javax.swing.JLabel txtAdmin;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JLabel txtStaff;
    // End of variables declaration//GEN-END:variables
}
