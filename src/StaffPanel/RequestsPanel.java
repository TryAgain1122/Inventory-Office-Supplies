package StaffPanel;

import Database_config.DbConnection;
import Styles.ButtonStyles;
import Styles.ComponentStyles;
import Styles.ModalCustom;
import Styles.TableStyles;
import Styles.TextFieldStyle;
import inventory_office_supplies.AdminDashboard;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class RequestsPanel extends javax.swing.JPanel {
    
    private int currentPage = 1;
    private int rowsPerPage = 4;
    private int totalRows = 0;
    private int totalPages = 0;
    
    public RequestsPanel() {
        initComponents();
        loadProducts();
        loadRequestsTable();
        
        ComponentStyles.styleComboBox(cmbProducts);
        ButtonStyles.setAdd(btnSubmitRequest);
        TableStyles.applyDefault(tblRequests);
        
        tblRequests.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        // Set preferred column widths
        tblRequests.getColumnModel().getColumn(0).setPreferredWidth(100); // Request ID
        tblRequests.getColumnModel().getColumn(1).setPreferredWidth(150); // Product Name
        tblRequests.getColumnModel().getColumn(2).setPreferredWidth(80);  // Quantity
        tblRequests.getColumnModel().getColumn(3).setPreferredWidth(100); // Status
        tblRequests.getColumnModel().getColumn(4).setPreferredWidth(150); // Request Date
        tblRequests.getColumnModel().getColumn(5).setPreferredWidth(150); // Action Date
        tblRequests.getColumnModel().getColumn(6).setPreferredWidth(300); // Remarks
        
        ButtonStyles.setDark(btnExportToExcel);
        ButtonStyles.setDark(btnNext);
        ButtonStyles.setDark(btnPrev);
    }
    
    private void loadProducts() {
        try {
            DbConnection db = new DbConnection();
            Connection conn;
            conn = db.getConnection();
            String sql = "SELECT product_id, product_name FROM products_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            cmbProducts.removeAllItems();
            cmbProducts.addItem("Select Product");
            
            while(rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                cmbProducts.addItem(name + " (ID: " + id + ")");
            }
            rs.close();
        } catch (SQLException  e) {
            ModalCustom.showError("Error loading products " + e.getMessage(), "Error");
        }
    }
    
    private void loadRequestsTable () {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        DbConnection db = new DbConnection();
        Connection conn;
        PreparedStatement pst;
        ResultSet rs;
        
        try {
        
         conn = db.getConnection();
         int currentUserId = 1;
         
         String countSql = "SELECT COUNT(*) FROM request_tbl WHERE user_id = ?";
         pst = conn.prepareStatement(countSql);    
         pst.setInt(1, currentUserId);
         rs = pst.executeQuery();
         if (rs.next()) {
             totalRows = rs.getInt(1);
         }
         rs.close();
         pst.close();
         
         totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
         if (totalPages == 0) totalPages = 1;
         
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
        int offset =  (currentPage -1 ) * rowsPerPage;  
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
        rs.close();              
        } catch (SQLException e) {
            ModalCustom.showError("Error loading requests", "Error");
        }
        
        
    }

    

    private void updateSearchResult(){
//        String status = (String) statuschecker.getSelectedItem();
//        String searchText = inputSearch.getText();
//        String inputDateText = inputDate.getText();
//        searchProducts(searchText, inputDateText, status);
    }
    
    private void searchProducts(String searchText, String inputDateText, String status) {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String sql = "SELECT request_id, product_id, quantity, status, request_date, action_date, remarks " +
             "FROM request_tbl WHERE 1=1";
            
        boolean hasSearch = searchText != null && !searchText.trim().isEmpty();    
        boolean hasDate = inputDateText != null && !inputDateText.trim().isEmpty();
        boolean hasStatus = status != null && !status.equals("Default");
        
        if (hasSearch) {
            sql += " AND product_id LIKE ?";
    }
        if (hasDate){
            sql += " AND DATE_FORMAT(request_date, '%Y-%m-%d') LIKE ?";
    }
        if (hasStatus){
            sql += " AND status = ?";
    }

        PreparedStatement pst = conn.prepareStatement(sql);
        int index = 1;

        if (hasSearch) pst.setString(index++, "%" + searchText.trim() + "%");
        if (hasDate) pst.setString(index++, "%" + inputDateText.trim() + "%");
        if (hasStatus) pst.setString(index++, status);
        
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            Object[] row = {
                rs.getInt("request_id"),
                rs.getInt("product_id"),
                rs.getInt("quantity"),
                rs.getString("status"),
                rs.getString("request_date"),
                rs.getString("action_date"),
                rs.getString("remarks")
            };
            model.addRow(row);
        }
        
        rs.close();
        pst.close();

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
    }
    
    db.closeConnection();
}
    
    private int getProductIdFromCombo(int index) {
        try {
            String selected = cmbProducts.getItemAt(index);
            int start = selected.indexOf("ID: ") + 4;
            int end = selected.indexOf(")", start);
            return Integer.parseInt(selected.substring(start, end)); 
        } catch (Exception e) {
            return -1;
        }
    }
    
    private String getProductNameFromCombo (int index) {
        String selected = cmbProducts.getItemAt(index);
        return selected.split(" \\(ID:")[0];
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        togglePending = new javax.swing.JLabel();
        cmbProducts = new javax.swing.JComboBox<>();
        togglePending1 = new javax.swing.JLabel();
        spinnerQty = new javax.swing.JSpinner();
        togglePending2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        btnSubmitRequest = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnExportToExcel = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Create New Request");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Request ID", "Product ID", "Quantity", "Status", "Request Date", "Action Date", "Remarks"
            }
        ));
        jScrollPane1.setViewportView(tblRequests);

        togglePending.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        togglePending.setText("Products");

        cmbProducts.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbProducts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "Approved", "Pending", "Reject" }));
        cmbProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProductsActionPerformed(evt);
            }
        });

        togglePending1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        togglePending1.setText("Remarks");

        spinnerQty.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        togglePending2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        togglePending2.setText("Quantity");

        txtRemarks.setColumns(20);
        txtRemarks.setRows(5);
        jScrollPane2.setViewportView(txtRemarks);

        btnSubmitRequest.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSubmitRequest.setText("Submit Request");
        btnSubmitRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitRequestActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblPage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPage.setText("1 / 10");

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPrev.setText("Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnExportToExcel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnExportToExcel.setText("Export to Excel");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(togglePending2)
                            .addComponent(togglePending1))
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2))
                    .addComponent(btnSubmitRequest)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext)
                        .addGap(86, 86, 86)
                        .addComponent(btnExportToExcel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(togglePending)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(togglePending)
                    .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(togglePending2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(togglePending1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSubmitRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportToExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(138, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPage)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProductsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProductsActionPerformed

    private void btnSubmitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitRequestActionPerformed
        int selectedIndex = cmbProducts.getSelectedIndex();
    if (selectedIndex <= 0) {
        ModalCustom.showError("Please select a product", "Validation Error");
        return;
    }

    int quantity = (Integer) spinnerQty.getValue();
    if (quantity <= 0) {
        ModalCustom.showError("Quantity must be greater than 0", "Validation Error");
        return;
    }

    String remarks = txtRemarks.getText().trim();
    int productId = getProductIdFromCombo(selectedIndex);
    if (productId == -1) {
        ModalCustom.showError("Invalid product selected", "Error");
        return;
    }

    int currentUserId = 1; // replace with actual logged-in user id
    String productName = getProductNameFromCombo(selectedIndex);

    DbConnection db = new DbConnection();
    Connection conn = db.getConnection();
    PreparedStatement pst = null;

    try {
        String sql = "INSERT INTO request_tbl (user_id, product_id, quantity, remarks) VALUES (?, ?, ?, ?)";
        pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setInt(1, currentUserId);
        pst.setInt(2, productId);
        pst.setInt(3, quantity);
        pst.setString(4, remarks);
        pst.executeUpdate();

        // get generated request_id
        ResultSet generatedKeys = pst.getGeneratedKeys();
        int requestId = 0;
        if (generatedKeys.next()) {
            requestId = generatedKeys.getInt(1); // <-- use 1, not 0
        }

        // add directly to table for real-time update
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.addRow(new Object[]{
            requestId,
            productName,
            quantity,
            "Pending",
            java.time.LocalDateTime.now().toString(),
            null,
            remarks
        });
        
        

        ModalCustom.showInfo("Request submitted", "Success");

        // clear form
        cmbProducts.setSelectedIndex(0);
        spinnerQty.setValue(1);
        txtRemarks.setText("");

    } catch (SQLException e) {
        ModalCustom.showError("Error submitting request: " + e.getMessage(), "Error");
    } 
    }//GEN-LAST:event_btnSubmitRequestActionPerformed

    private void updatePageLabel () {
        lblPage.setText(currentPage + " / " + totalPages);
    }
    
    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadRequestsTable();
            updatePageLabel();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentPage++;
        loadRequestsTable();
        updatePageLabel();
    }//GEN-LAST:event_btnNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportToExcel;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSubmitRequest;
    private javax.swing.JComboBox<String> cmbProducts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPage;
    private javax.swing.JSpinner spinnerQty;
    private javax.swing.JTable tblRequests;
    private javax.swing.JLabel togglePending;
    private javax.swing.JLabel togglePending1;
    private javax.swing.JLabel togglePending2;
    private javax.swing.JTextArea txtRemarks;
    // End of variables declaration//GEN-END:variables
}
