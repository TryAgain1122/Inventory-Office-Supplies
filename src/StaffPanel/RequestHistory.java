/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffPanel;

import Database_config.DbConnection;
import Styles.ButtonStyles;
import Styles.ComponentStyles;
import Styles.ModalCustom;
import Styles.TableStyles;
import Styles.TextFieldStyle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luisr
 */
public class RequestHistory extends javax.swing.JPanel {

    private int currentPage = 1;
    private int rowsPerPage = 6;
    private Timer refreshTimer;
    
    public RequestHistory() {
        initComponents();
        loadRequestHistory();
//        refreshTimer = new Timer(5000, e -> loadRequestHistory());
//        refreshTimer.start();
        
        
        TextFieldStyle.customInputFields(txtSearch, "Search...");
        ButtonStyles.setAdd(btnSearch);
        ComponentStyles.styleComboBox(cmbStatus);
        ButtonStyles.setDark(btnExportToExcel);
        ButtonStyles.setDark(btnNext);
        ButtonStyles.setDark(btnPrev);
        TableStyles.applyDefault(tblHistory);
        TableStyles.autoResizeColumns(tblHistory);
    }

    private void loadRequestHistory() {
        DefaultTableModel model = (DefaultTableModel) tblHistory.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn;
        PreparedStatement pst;
        ResultSet rs;
        
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("MMM, dd, yyyy hh:mm a");
            
            conn = db.getConnection();
            
            String searchText = txtSearch.getText().trim();
            String statusFilter = cmbStatus.getSelectedItem().toString();
            
        String sql = "SELECT "
                        + "r.request_id, "
                        + "p.product_name, "
                        + "r.quantity, "
                        + "r.status, " 
                        + "r.request_date, "
                        + "r.action_date, "
                        + "r.remarks " +
                     "FROM request_tbl r " +
                     "JOIN products_tbl p ON r.product_id = p.product_id " +
                     "WHERE 1=1";
        if (!searchText.isEmpty() && !searchText.equalsIgnoreCase("Search")) {
            sql += " AND p.product_name LIKE ?";
        }
        
        if (!statusFilter.equalsIgnoreCase("Status")) {
            sql += " AND r.status";
        }
        
        sql += " ORDER BY r.request_date DESC LIMIT ?, ?";
            
        pst = conn.prepareStatement(sql);
        
        int index = 1;
        if (!statusFilter.isEmpty() && !searchText.equalsIgnoreCase("Search")) {
            pst.setString(index++, "%" + statusFilter + "%");
        }
        
        if (!statusFilter.equalsIgnoreCase("Status")) {
            pst.setString(index++, statusFilter);
        }
        
        pst.setInt(index++, (currentPage - 1) * rowsPerPage);
        pst.setInt(index, rowsPerPage);
        rs = pst.executeQuery();
            
            while (rs.next()) {
                Timestamp requests = rs.getTimestamp("request_date");
                String requestDate = requests != null ? displayFormat.format(requests) : "";
                
                Timestamp actionTs = rs.getTimestamp("action_date");
                String actionDate = actionTs != null ? displayFormat.format(actionTs) : "";
                
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
            
        } catch(SQLException e) {
            ModalCustom.showError("Error loading request history: " + e.getMessage() , "Error");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        btnNext = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnExportToExcel = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();

        txtSearch.setText("Search");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Status", "Pending", "Approved", "Reject" }));

        btnSearch.setText("Search");

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Quantity", "Status", "Request Date", "Action Date", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHistory);

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Request History");

        btnExportToExcel.setText("Export to Excel");

        lblPage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPage.setText("1 / 10");

        btnPrev.setText("Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnExportToExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addGap(105, 105, 105)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExportToExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPage)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
       if (currentPage > 1) {
           currentPage--;
           loadRequestHistory();
           lblPage.setText(currentPage + " / ?");
       }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentPage++;
        loadRequestHistory();
        lblPage.setText(currentPage + " / ?");
    }//GEN-LAST:event_btnNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportToExcel;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPage;
    private javax.swing.JTable tblHistory;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
