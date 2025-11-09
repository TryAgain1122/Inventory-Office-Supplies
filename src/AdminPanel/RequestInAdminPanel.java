/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminPanel;

import Database_config.DbConnection;
import Styles.ModalCustom;
import Styles.TableStyles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


public class RequestInAdminPanel extends javax.swing.JPanel {

private int currentPage = 1;
private int rowsPerPage = 5;
private int totalRows = 0;
private int totalPages = 0;

    public RequestInAdminPanel() {
        initComponents();
        loadRequestTable();
        loadUserCombo();
        loadProductCombo();
        
        TableStyles.applyDefault(tblRequests);
        TableStyles.autoResizeColumns(tblRequests);
    }
    
    private void loadUserCombo () {
        cmbUsers.removeAllItems();
        cmbUsers.addItem("Select User");
        
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            String sql = "SELECT user_id, fullname FROM users_tbl ORDER BY fullname ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String combText = rs.getInt("user_id") + " - " + rs.getString("fullname");
                cmbUsers.addItem(combText);
            }
            
        } catch (SQLException e) {
            ModalCustom.showError("Failed to load users: " + e.getMessage(), "Error");
        }
    }
    
    private void loadProductCombo () {
        cmbProduct.removeAllItems();
        cmbProduct.addItem("Select Product");
        
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            String sql = "SELECT product_name FROM products_tbl ORDER BY product_name ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String comboText = rs.getString("product_name");
                cmbProduct.addItem(comboText);
            }
        } catch (SQLException e) {
            ModalCustom.showError("Failed to load products: " + e.getMessage(), "Error");
        }
    }

    private void loadRequestTable () {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn; 
        PreparedStatement pst;
        ResultSet rs;
        
        try {
            conn = db.getConnection();
            String countSql = "SELECT COUNT(*) FROM request_tbl WHERE user_id = ? ";
            pst = conn.prepareStatement(countSql);
            int currentUserId = 1;
            pst.setInt(1, currentUserId);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                totalRows = rs.getInt(1);
            }
            rs.close();
            pst.close();
            
            totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            
            String sql = "SELECT "
                    + "r.request_id, "
                    + "p.product_name, "
                    + "r.quantity, "
                    + "r.status, "
                    + "r.request_date, "
                    + "r.action_date, "
                    + "r.remarks "
                    + "FROM request_tbl r "
                    + "JOIN products_tbl p ON r.product_id = p.product_id "
                    + "WHERE r.user_id = ? "
                    + "ORDER BY r.request_date DESC "
                    + "LIMIT ?, ?";
            
            
            pst = conn.prepareStatement(sql);
            int offset = (currentPage - 1) * rowsPerPage;
            pst.setInt(1, currentUserId);
            pst.setInt(2, offset);
            pst.setInt(3, rowsPerPage);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("request_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getString("status"),
                    rs.getString("request_date"),
                    rs.getString("action_date"),
                    rs.getString("remarks")
                });
            }
            
            btnPrev.setEnabled(currentPage > 1);
            btnNext.setEnabled(currentPage < totalPages);
            
            updatePageLabel();
            
        } catch(SQLException e) {
            ModalCustom.showError("Error loading requests: " + e.getMessage() , "Error");
        }
    }
    
    private void updatePageLabel () {
        lblPage.setText(currentPage + " / " + totalPages);
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAll = new javax.swing.JRadioButton();
        btnPending = new javax.swing.JRadioButton();
        btnReject = new javax.swing.JRadioButton();
        cmbProduct = new javax.swing.JComboBox<>();
        cmbUsers = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        btnApproved = new javax.swing.JRadioButton();
        btnPrev = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Requests");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Status:");

        btnAll.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAll.setText("All");

        btnPending.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPending.setText("Pending");

        btnReject.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnReject.setText("Reject");

        cmbProduct.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Producct:" }));

        cmbUsers.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbUsers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Users", "Admin", "Staff", " " }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("User:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Product");

        txtSearch.setText("Search");

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Request ID", "Product Name", "Quantity", "Status", "Request Date", "Action Date", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblRequests);

        jButton2.setText("Search");

        btnApproved.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnApproved.setText("Approved");

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPrev.setText("Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        lblPage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPage.setText("1 / 10");

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(btnAll)
                        .addGap(18, 18, 18)
                        .addComponent(btnPending)
                        .addGap(18, 18, 18)
                        .addComponent(btnApproved)
                        .addGap(18, 18, 18)
                        .addComponent(btnReject)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)))
                        .addGap(52, 52, 52))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(cmbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(btnPrev)
                .addGap(18, 18, 18)
                .addComponent(lblPage)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAll)
                    .addComponent(btnPending)
                    .addComponent(btnApproved)
                    .addComponent(btnReject))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(110, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
       if (currentPage > 1) {
           currentPage--;
           loadRequestTable();
       }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currentPage < totalPages) {
            currentPage++;
            loadRequestTable();
        }
    }//GEN-LAST:event_btnNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnAll;
    private javax.swing.JRadioButton btnApproved;
    private javax.swing.JButton btnNext;
    private javax.swing.JRadioButton btnPending;
    private javax.swing.JButton btnPrev;
    private javax.swing.JRadioButton btnReject;
    private javax.swing.JComboBox<String> cmbProduct;
    private javax.swing.JComboBox<String> cmbUsers;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblPage;
    private javax.swing.JTable tblRequests;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
