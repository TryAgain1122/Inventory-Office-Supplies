/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminPanel;

import Database_config.DbConnection;
import Styles.ButtonStyles;
import Styles.ModalCustom;
import Styles.TableStyles;
import Styles.TextFieldStyle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.Timer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class RequestInAdminPanel extends javax.swing.JPanel {

private int currentPage = 1;
private int rowsPerPage = 5;
private int totalRows = 0;
private int totalPages = 0;
private boolean isSearching = false;

    public RequestInAdminPanel() {
        initComponents();
        loadRequestTable();
//        loadUserCombo();
//        loadProductCombo();
//        txtSearch.addActionListener(e -> btnSearchActionPerformed(null));
//        Timer timer = new Timer(5000, e -> {
//            if (isSearching) {
//                searchRequests();
//            } else {
//                loadRequestTable();
//            }
//        }); 
//        timer.start();
        
        ButtonStyles.setDark(btnNext);
        ButtonStyles.setDark(btnPrev);
        ButtonStyles.setDark(btnSearch);
        
        TextFieldStyle.SearchStyle(txtSearch, "Search...");
        
        TableStyles.applyDefault(tblRequests);
        TableStyles.autoResizeColumns(tblRequests);
        
        setupStatusDropdown();
        setupTableListener();
    }
    
//    private void loadUserCombo () {
//        cmbUsers.removeAllItems();
//        cmbUsers.addItem("Select User");
//        
//        try {
//            DbConnection db = new DbConnection();
//            Connection conn = db.getConnection();
//            String sql = "SELECT user_id, fullname FROM users_tbl ORDER BY fullname ASC";
//            PreparedStatement pst = conn.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            
//            while (rs.next()) {
//                String combText = rs.getInt("user_id") + " - " + rs.getString("fullname");
//                cmbUsers.addItem(combText);
//            }
//            
//        } catch (SQLException e) {
//            ModalCustom.showError("Failed to load users: " + e.getMessage(), "Error");
//        }
//    }
//    
//    private void loadProductCombo () {
//        cmbProduct.removeAllItems();
//        cmbProduct.addItem("Select Product");
//        
//        try {
//            DbConnection db = new DbConnection();
//            Connection conn = db.getConnection();
//            String sql = "SELECT product_name FROM products_tbl ORDER BY product_name ASC";
//            PreparedStatement pst = conn.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            
//            while (rs.next()) {
//                String comboText = rs.getString("product_name");
//                cmbProduct.addItem(comboText);
//            }
//        } catch (SQLException e) {
//            ModalCustom.showError("Failed to load products: " + e.getMessage(), "Error");
//        }
//    }

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
            SimpleDateFormat displayFormat = new SimpleDateFormat("MMM, dd, yyyy hh:mm a"); // 12-hour

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
                Timestamp timestamp = rs.getTimestamp("request_date");
                String requestDate = timestamp != null ? displayFormat.format(timestamp) : "";
                
                Timestamp timestamp1 = rs.getTimestamp("action_date");
                String actionDate = timestamp1 != null ? displayFormat.format(timestamp1) : "";
                
                model.addRow(new Object[] {
                    rs.getInt("request_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getString("status"),
//                    rs.getString("request_date"),
                    requestDate,
//                    rs.getString("action_date"),
                    actionDate,
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
            
    private void updateRequestStatus (int requestId, String status) {
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            
            String sql = status.equals("Approved")
                    ? "UPDATE request_tbl SET status = ?, action_date = NOW() WHERE request_id = ?"
                    : "UPDATE request_tbl SET status = ?, action_date = NULL WHERE request_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2,requestId);
            pst.executeUpdate();
            
            loadRequestTable();
            
        } catch(SQLException e) {
            ModalCustom.showError("Failed to update status: " + e.getMessage(), "Error");
        }
    }
    
    private void setupStatusDropdown() {
        TableColumn statusColumn = tblRequests.getColumnModel().getColumn(3);
        JComboBox<String> statusComboBox = new JComboBox<>(new String [] {"Pending", "Approved", "Reject"});
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));   
    }
    
    private void setupTableListener() {
        tblRequests.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 3 && e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int requestId = (int) tblRequests.getValueAt(row, 0);
                    String newStatus = (String) tblRequests.getValueAt(row, 3);
                    
                    updateRequestStatus(requestId, newStatus);
                }
            }
        });
    }
    
    private void searchRequests () {
        String keyword = txtSearch.getText().trim();
        
        if (keyword.equals("Search...")){
            keyword = "";
        }
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn;
        PreparedStatement pst;
        ResultSet rs;
        
        try {
            conn = db.getConnection();
            
            String sql =
                "SELECT r.request_id, p.product_name, r.quantity, r.status, " +
                "r.request_date, r.action_date, r.remarks " +
                "FROM request_tbl r " +
                "JOIN products_tbl p ON r.product_id = p.product_id " +
                "WHERE p.product_name LIKE ? " +
                "OR r.status LIKE ? " +
                "OR r.remarks LIKE ? " +
                "OR DATE_FORMAT(r.request_date, '%M %d %Y %h:%i %p') LIKE ? " +
                "OR DATE_FORMAT(r.action_date, '%M %d %Y %h:%i %p') LIKE ? " +
                "ORDER BY r.request_date DESC";
            
            pst = conn.prepareStatement(sql);
            
            String like = "%" + keyword + "%";
            pst.setString(1, like);
            pst.setString(2, like);
            pst.setString(3, like);
            pst.setString(4, like);
            pst.setString(5, like);
            
            rs = pst.executeQuery();
            
            SimpleDateFormat dateFormmatted  = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            
            while (rs.next()) {
                Timestamp req = rs.getTimestamp("request_date");
                String reqDate = (req != null) ? dateFormmatted.format(req) : "";
                
                Timestamp act = rs.getTimestamp("action_date");
                String actDate = (act != null) ? dateFormmatted.format(act) : "";
                
                model.addRow(new Object[] {
                    rs.getInt("request_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getString("status"),
                    reqDate,
                    actDate,
                    rs.getString("remarks")
                });
            }
            
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
            lblPage.setText("Search");
            
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            ModalCustom.showError("Search Error" + e.getMessage(), "Error");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Requests");

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

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch)))
                        .addGap(52, 52, 52))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 575, Short.MAX_VALUE))))
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
                .addGap(131, 131, 131)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(119, Short.MAX_VALUE))
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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       String input = txtSearch.getText().trim();
       
       if (input.isEmpty() || input.equals("Search...")) {
           currentPage = 1;
           loadRequestTable();
       } else {
           searchRequests();
       }
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblPage;
    private javax.swing.JTable tblRequests;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
