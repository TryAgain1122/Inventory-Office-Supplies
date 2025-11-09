/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminPanel;

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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luisr
 */
public class InventoryPanel extends javax.swing.JPanel {

    private Map<String, Integer> productMap = new HashMap<>();
    private Map<String, Integer> userMap = new HashMap<>();
    
    private int currentPage = 1;
    private int rowsPerPage = 5;
    private int totalRows = 0;
    
    public InventoryPanel() {
        initComponents();
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnAll);
        group.add(rbtnIn);
        group.add(rbtnOut);
        
         
        TableStyles.CustomTable(tblInventoryLogs);
        TableStyles.autoResizeColumns(tblInventoryLogs);
        
        
        TextFieldStyle.customInputFields(txtSearch, "Search... ");
        ButtonStyles.setDark(btnNext);
        ButtonStyles.setDark(btnPrev);
        ButtonStyles.setAdd(btnSearch);
        
        
        ComponentStyles.styleRadio(rbtnAll);
        ComponentStyles.styleRadio(rbtnIn);
        ComponentStyles.styleRadio(rbtnOut);
        
        ComponentStyles.styleJCalendar(dateTo);
        ComponentStyles.styleJCalendar(dateFrom);
        

        
        rbtnAll.setSelected(true);
        cmbProductFilter.setSelectedIndex(0);
        cmbUserFilter.setSelectedIndex(0);
                
        populateProductFilter();
        populateUserFilter();
        
        loadInventoryLogsTable();  
    }
    
    private void populateProductFilter() {
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            cmbProductFilter.removeAllItems();
            cmbProductFilter.addItem("Select Product");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT product_id, product_name FROM products_tbl");
            while(rs.next()) {
                String name = rs.getString("product_name");
                int id = rs.getInt("product_id");
                cmbProductFilter.addItem(name);
                productMap.put(name, id);
            }
            db.closeConnection();
        } catch (SQLException e) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }
    }
    
    private void populateUserFilter() {
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            cmbUserFilter.removeAllItems();
            cmbUserFilter.addItem("Select User");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_id, fullname FROM users_tbl");
            while(rs.next()) {
                String name = rs.getString("fullname");
                int id = rs.getInt("user_id");
                cmbUserFilter.addItem(name);
                userMap.put(name, id);
            }
            db.closeConnection();
        } catch (SQLException e) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }
        
    }
    
    public void loadInventoryLogsTable() {
         DefaultTableModel model = (DefaultTableModel) tblInventoryLogs.getModel();
    model.setRowCount(0); // clear table
    DbConnection db = new DbConnection();
    Connection conn = db.getConnection();

    try {
        Integer selectedProductId = cmbProductFilter.getSelectedIndex() > 0
                ? productMap.get(cmbProductFilter.getSelectedItem().toString())
                : null;

        Integer selectedUserId = cmbUserFilter.getSelectedIndex() > 0
                ? userMap.get(cmbUserFilter.getSelectedItem().toString())
                : null;

        String stockType = null;
        if (rbtnIn.isSelected()) stockType = "IN";
        else if (rbtnOut.isSelected()) stockType = "OUT";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = dateFrom.getDate() != null ? sdf.format(dateFrom.getDate()) + " 00:00:00" : "1970-01-01 00:00:00";
        String toDate = dateTo.getDate() != null ? sdf.format(dateTo.getDate()) + " 23:59:59" : "9999-12-31 23:59:59";

        String searchText = txtSearch.getText().trim();

        
        String countSql = "SELECT COUNT(*) FROM inventory_logs_tbl il "
                + "JOIN products_tbl p ON il.product_id = p.product_id "
                + "JOIN users_tbl u ON il.user_id = u.user_id "
                + "WHERE 1=1";
        if (selectedProductId != null) countSql += " AND p.product_id = ?";
        if (selectedUserId != null) countSql += " AND u.user_id = ?";
        if (stockType != null) countSql += " AND il.stock_history = ?";
        countSql += " AND (il.date_inventory_logs BETWEEN ? AND ?)";
        if (!searchText.isEmpty()) countSql += " AND (p.product_name LIKE ? OR u.fullname LIKE ?)";

        PreparedStatement countPst = conn.prepareStatement(countSql);
        int idx = 1;
        if (selectedProductId != null) countPst.setInt(idx++, selectedProductId);
        if (selectedUserId != null) countPst.setInt(idx++, selectedUserId);
        if (stockType != null) countPst.setString(idx++, stockType);
        countPst.setString(idx++, fromDate);
        countPst.setString(idx++, toDate);
        if (!searchText.isEmpty()) {
            countPst.setString(idx++, "%" + searchText + "%");
            countPst.setString(idx++, "%" + searchText + "%");
        }

        ResultSet rsCount = countPst.executeQuery();
        if (rsCount.next()) totalRows = rsCount.getInt(1);
        rsCount.close();
        countPst.close();

        
        int offset = (currentPage - 1) * rowsPerPage;

        String sql = "SELECT il.log_id, p.product_name, u.fullname AS user_name, "
                + "il.stock_history, il.quantity, il.date_inventory_logs "
                + "FROM inventory_logs_tbl il "
                + "JOIN products_tbl p ON il.product_id = p.product_id "
                + "JOIN users_tbl u ON il.user_id = u.user_id "
                + "WHERE 1=1";
        if (selectedProductId != null) sql += " AND p.product_id = ?";
        if (selectedUserId != null) sql += " AND u.user_id = ?";
        if (stockType != null) sql += " AND il.stock_history = ?";
        sql += " AND (il.date_inventory_logs BETWEEN ? AND ?)";
        if (!searchText.isEmpty()) sql += " AND (p.product_name LIKE ? OR u.fullname LIKE ?)";
        sql += " ORDER BY il.date_inventory_logs DESC LIMIT ? OFFSET ?";

        PreparedStatement pst = conn.prepareStatement(sql);
        idx = 1;
        if (selectedProductId != null) pst.setInt(idx++, selectedProductId);
        if (selectedUserId != null) pst.setInt(idx++, selectedUserId);
        if (stockType != null) pst.setString(idx++, stockType);
        pst.setString(idx++, fromDate);
        pst.setString(idx++, toDate);
        if (!searchText.isEmpty()) {
            pst.setString(idx++, "%" + searchText + "%");
            pst.setString(idx++, "%" + searchText + "%");
        }
        pst.setInt(idx++, rowsPerPage);
        pst.setInt(idx++, offset);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getInt("log_id"),
                rs.getString("product_name"),
                rs.getString("user_name"),
                rs.getString("stock_history"),
                rs.getInt("quantity"),
                rs.getTimestamp("date_inventory_logs")
            });
        }

        rs.close();
        pst.close();
        conn.close();

     
        updatePageInfo();

    } catch (SQLException e) {
        ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
    }
    }
    
    private void updatePageInfo () {
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        lblPageText.setText(currentPage + " / " + totalPages);
        
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cmbProductFilter = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventoryLogs = new javax.swing.JTable();
        btnPrev = new javax.swing.JButton();
        lblPageText = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmbUserFilter = new javax.swing.JComboBox<>();
        rbtnAll = new javax.swing.JRadioButton();
        rbtnIn = new javax.swing.JRadioButton();
        rbtnOut = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        dateTo = new com.toedter.calendar.JDateChooser();
        dateFrom = new com.toedter.calendar.JDateChooser();

        jLabel2.setText("jLabel2");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setText("Inventory logs");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cmbProductFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Product", " " }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("0");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Last Updated: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Total Items: ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("0");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Total OUT:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Total IN:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        tblInventoryLogs.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblInventoryLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "User", "Type", "Qty", "Date/Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblInventoryLogs);

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPrev.setText("Prev");
        btnPrev.setToolTipText("");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        lblPageText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPageText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPageText.setText("Page 1 of !0");

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.setActionCommand("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Stock Type");

        cmbUserFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Staff", " " }));
        cmbUserFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserFilterActionPerformed(evt);
            }
        });

        rbtnAll.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rbtnAll.setText("All");
        rbtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnAllActionPerformed(evt);
            }
        });

        rbtnIn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rbtnIn.setText("IN");

        rbtnOut.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rbtnOut.setText("OUT");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Date Range:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("to");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("From");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbProductFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbtnAll)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbtnIn)
                                        .addGap(26, 26, 26)
                                        .addComponent(rbtnOut)
                                        .addGap(58, 58, 58)
                                        .addComponent(cmbUserFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(41, 41, 41))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPageText, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbProductFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(rbtnAll)
                        .addComponent(rbtnIn)
                        .addComponent(rbtnOut)
                        .addComponent(cmbUserFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFrom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPageText, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadInventoryLogsTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadInventoryLogsTable();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            loadInventoryLogsTable();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void cmbUserFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUserFilterActionPerformed

    private void rbtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbProductFilter;
    private javax.swing.JComboBox<String> cmbUserFilter;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageText;
    private javax.swing.JRadioButton rbtnAll;
    private javax.swing.JRadioButton rbtnIn;
    private javax.swing.JRadioButton rbtnOut;
    private javax.swing.JTable tblInventoryLogs;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
