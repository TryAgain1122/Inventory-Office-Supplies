/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminPanel;

import Database_config.DbConnection;
import Styles.ButtonStyles;
import Styles.ModalCustom;
import Styles.TextFieldStyle;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luisr
 */
public class ProductPanel extends javax.swing.JPanel {
    
    private InventoryPanel inventoryPanel = new InventoryPanel();
    private int currentPage = 1;
    private int rowsPerPage = 5;
    private int totalRows = 0;
    private boolean isEditing = false;
    private int editingProductId = -1;
    
    public ProductPanel(InventoryPanel inventoryPanel) {
        initComponents();
        this.inventoryPanel = inventoryPanel;
        setUpProductTable();
        loadCategoriesToComboBox();
        loadProducts();
        
        TextFieldStyle.customInputFields(txtSearch, "Search...");
        TextFieldStyle.customInputFields(txtProductName, "Product name");
        TextFieldStyle.customInputFields(txtPrice, "Price");
        TextFieldStyle.customInputFields(txtUnit, "Unit");
        TextFieldStyle.customInputFields(txtSupplier, "Supplier name");
        
        ButtonStyles.setDark(btnPrev);
        ButtonStyles.setDark(btnNext);
        
        
        ButtonStyles.setDark(btnSearch);
        ButtonStyles.setAdd(btnAdd);
        ButtonStyles.setEdit(btnUpdate);
        ButtonStyles.setDelete(btnDelete);
        
        initCustomBehavior();
    }
    
    private void loadProducts() {
        String searchText = txtSearch.getText().trim();
        String categoryFilter = (String) selectCategory.getSelectedItem();
        
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            
            String countSql = "SELECT COUNT(*) FROM products_tbl";
            PreparedStatement countPst = conn.prepareStatement(countSql);
            ResultSet countRs = countPst.executeQuery();
            
            if (countRs.next()) {
                totalRows = countRs.getInt(1);
            }
            
            //Compute offset 
            int offset = (currentPage - 1) * rowsPerPage;
            
            //limit the data 
            String sql = "SELECT * FROM products_tbl LIMIT ? OFFSET ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, rowsPerPage);
            pst.setInt(2, offset);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getString("unit"),
                    rs.getDouble("price"),
                    rs.getString("date_added")
                });
            }
            
            updatePageInfo();
        } catch (SQLException e) {
            ModalCustom.showError("Invalid Connection: " + e.getMessage(), "Connection Error");
        }
    }
    
    private void updatePageInfo () {
        int totalPages = (int) Math.ceil((double)totalRows / rowsPerPage);
        lblPageInfo.setText(currentPage + " / " + totalPages);
        
        //Disable button when at limits 
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
        
    }
    
    private void setUpProductTable () {
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Unit");
        model.addColumn("Price");
        model.addColumn("Date");
        
        tblProduct.setModel(model);
        tblProduct.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        tblProduct.setRowHeight(40);
        tblProduct.setGridColor(new Color(220, 220, 220));
        tblProduct.setShowVerticalLines(true);
        tblProduct.setShowHorizontalLines(true);
        tblProduct.setSelectionBackground(new Color(180, 180, 180));
        tblProduct.setSelectionForeground(Color.WHITE);
        
        tblProduct.getTableHeader().setBackground(new Color(60, 60, 60));
        tblProduct.getTableHeader().setForeground(Color.WHITE);
        tblProduct.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        
    }
    
    private void loadProductTable() {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
           String sql = "SELECT product_id,product_name, quantity, unit, price, date_added FROM products_tbl";
           PreparedStatement pst = conn.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();

           while(rs.next()) {
              model.addRow(new Object[] {
                  rs.getInt("product_id"),
                  rs.getString("product_name"),
                  rs.getInt("quantity"),
                  rs.getString("unit"),
                  rs.getString("price"),
                  rs.getString("date_added")
              });
           }
           db.closeConnection();
           updatePageInfo();
        } catch (SQLException e) {
            ModalCustom.showError("Database Error: ", "Error");
        }
    }

    
    private void initCustomBehavior () {
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        tblProduct.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    boolean hasSelection = tblProduct.getSelectedRow() != -1;
                    btnUpdate.setEnabled(hasSelection);
                    btnDelete.setEnabled(hasSelection);
                }
            }
        });
    }
    
    public void loadCategoriesToComboBox() {
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String sql = "SELECT category_name FROM categories_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            selectCategory.removeAllItems();
            selectCategory.addItem("Select Category");
            
            while(rs.next()) {
                selectCategory.addItem(rs.getString("category_name"));
            }
        } catch(SQLException e) {
            ModalCustom.showError("Error loading categories: ", "Invalid");
        }
    }
    
    private void resetFields() {
        txtProductName.setText("");
        txtUnit.setText("");
        txtPrice.setText("");
        spinnerQty.setValue(0);
        selectCategory.setSelectedIndex(0);
        
        btnAdd.setEnabled(true);
        btnDelete.setEnabled(true);
        btnUpdate.setText("Update");
        
        isEditing = false;
        editingProductId = -1;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        selectCategory = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        txtPrice = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        spinnerQty = new javax.swing.JSpinner();
        txtUnit = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();
        txtSupplier = new javax.swing.JTextField();

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setText("Product Management");

        selectCategory.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        spinnerQty.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tblProduct.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblProduct);

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPrev.setText("Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblPageInfo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPageInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPageInfo.setText("1 / 10");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(btnPrev)
                .addGap(18, 18, 18)
                .addComponent(lblPageInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSupplier)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(3, 3, 3)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(135, 135, 135)
                                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(154, 154, 154)
                                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)
                                                .addComponent(spinnerQty, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectCategory)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(txtSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(txtUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(spinnerQty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPageInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(91, 91, 91))
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
    String productName = txtProductName.getText().trim();
    String unit = txtUnit.getText().trim();
    int quantity = (int) spinnerQty.getValue();
    String priceText = txtPrice.getText().trim();
    String selectedCategory = (String) selectCategory.getSelectedItem();

    if (productName.isEmpty() || unit.isEmpty() || priceText.isEmpty() || selectedCategory.isEmpty()) {
        ModalCustom.showWarning("Please fill all fields properly!", "Warning");
        return;
    }

    try {
        double price = Double.parseDouble(priceText);
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();

        // Get category ID
        String catSql = "SELECT category_id FROM categories_tbl WHERE category_name = ?";
        PreparedStatement catPst = conn.prepareStatement(catSql);
        catPst.setString(1, selectedCategory);
        ResultSet rs = catPst.executeQuery();

        int categoryId = 0;
        if (rs.next()) {
            categoryId = rs.getInt("category_id");
        }

        // Insert into products_tbl
        String sql = "INSERT INTO products_tbl (category_id, product_name, quantity, unit, price) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setInt(1, categoryId);
        pst.setString(2, productName);
        pst.setInt(3, quantity);
        pst.setString(4, unit);
        pst.setDouble(5, price);

        int rows = pst.executeUpdate();
        if (rows > 0) {
            // Get the generated product_id
            ResultSet generatedKeys = pst.getGeneratedKeys();
            int productId = 0;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }

            // Insert IN log into inventory_logs_tbl
            String logSql = "INSERT INTO inventory_logs_tbl (product_id, user_id, stock_history, quantity, remarks) VALUES (?, ?, 'IN', ?, ?)";
            PreparedStatement logPst = conn.prepareStatement(logSql);
            logPst.setInt(1, productId);
            logPst.setInt(2, 1); // <-- dito ilagay ang current user_id (admin) na nag-add
            logPst.setInt(3, quantity);
            logPst.setString(4, "Initial stock added");
            logPst.executeUpdate();
            inventoryPanel.loadInventoryLogsTable();

            ModalCustom.showInfo("Product added successfully!", "Successfully");

            // Recount total rows and update pagination
            String countSql = "SELECT COUNT(*) FROM products_tbl";
            PreparedStatement countPst = conn.prepareStatement(countSql);
            ResultSet countRs = countPst.executeQuery();
            if (countRs.next()) {
                totalRows = countRs.getInt(1);
            }
            int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            currentPage = totalPages;

            loadProducts();
            updatePageInfo();

            // Reset fields
            txtProductName.setText("");
            txtUnit.setText("");
            txtPrice.setText("");
            spinnerQty.setValue(0);
            selectCategory.setSelectedIndex(0);
        }

        db.closeConnection();
        } catch(SQLException e) {
            ModalCustom.showError("Invalid Connection: " + e.getMessage(), "Error");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (!isEditing) {
            int selectRow = tblProduct.getSelectedRow();
        
        if (selectRow == -1) {
            ModalCustom.showWarning("Please select a product to update", "No Selection");
            return;
        }
        
        editingProductId = (int) tblProduct.getValueAt(selectRow, 0);
        String productName = tblProduct.getValueAt(selectRow, 1).toString();
        int quantity = Integer.parseInt(tblProduct.getValueAt(selectRow, 2).toString());
        String unit = tblProduct.getValueAt(selectRow, 3).toString();
        double price = Double.parseDouble(tblProduct.getValueAt(selectRow, 4).toString());
        
        
        txtProductName.setText(productName);
        spinnerQty.setValue(quantity);
        txtUnit.setText(unit);
        txtPrice.setText(String.valueOf(price));
        
        //disable add button para di magkamali 
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        
        btnUpdate.setText("Save");
        isEditing = true;
        ModalCustom.showInfo("You are now editing this product...", "Edit mode");
        
        } else {
            String productName = txtProductName.getText().trim();
            String unit = txtUnit.getText().trim();
            int newQty = (int) spinnerQty.getValue();
            String priceText = txtPrice.getText().trim();
            
            if (productName.isEmpty() || unit.isEmpty() || priceText.isEmpty()) {
                ModalCustom.showWarning("Please fill all fields properly!", "Waring");
                return;
            }
            
            try {
                double price = Double.parseDouble(priceText);
                DbConnection db = new DbConnection();
                Connection conn = db.getConnection();
                
                //kinukuha yung  old quantity logging
                String getOldSql = "SELECT quantity FROM products_tbl WHERE product_id = ?";
                PreparedStatement oldPst = conn.prepareStatement(getOldSql);
                oldPst.setInt(1, editingProductId);
                ResultSet rsOld = oldPst.executeQuery();
                int oldQty = 0;
                
                if (rsOld.next()) {
                    oldQty = rsOld.getInt("quantity");
                }
                
                String sql = "UPDATE products_tbl SET product_name = ?, quantity = ?, unit = ?, price = ? WHERE product_id = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, productName);
                pst.setInt(2, newQty);
                pst.setString(3, unit);
                pst.setDouble(4, price);
                pst.setInt(5, editingProductId);
                
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    
                    int diffrence = newQty - oldQty;
                    
                    if (diffrence != 0) {
                        String movement = diffrence > 0 ? "IN" : "OUT";
                        int qtyLogged = Math.abs(diffrence);
                        
                        String logSql = "INSERT INTO inventory_logs_tbl(product_id, user_id, stock_history, quantity, remarks) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement logPst = conn.prepareStatement(logSql);
                        
                        logPst.setInt(1, editingProductId);
                        logPst.setInt(2, 1);
                        logPst.setString(3, movement);
                        logPst.setInt(4, qtyLogged);
                        logPst.setString(5, "updated product");
                        
                        logPst.executeUpdate();
                        inventoryPanel.loadInventoryLogsTable();
                    }
                    
                    ModalCustom.showInfo("Products updated successfully", "Success");
                    loadProducts();
                    resetFields();
                }
                
            } catch (SQLException e) {
            
            }
        }
        
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
       int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
       if (currentPage < totalPages) {
           currentPage++;
           loadProducts();
       }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadProducts();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectRow = tblProduct.getSelectedRow();
        if (selectRow == -1) {
            ModalCustom.showWarning("Please select a product to delete", "No Selection");           return;
        }
        
        int productId = (int) tblProduct.getValueAt(selectRow, 0);
        String productName = tblProduct.getValueAt(selectRow, 1).toString();
        
        boolean confirmed = ModalCustom.showConfirm("Are you sure tou want to delete \"" + productName + "\"?", "Confirm Deletion");
        
        if (!confirmed) return;
        
        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();
            String sql = "DELETE FROM products_tbl WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productId);
            
            int rows = pst.executeUpdate();
            if (rows > 0) {
                ModalCustom.showInfo("Product deleted successfully", "Deleted");
                loadProducts();
            }
            db.closeConnection();
        } catch(SQLException e) {
            ModalCustom.showError("error deleting product: " + e.getMessage(), "Error");
        } 
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPageInfo;
    private javax.swing.JComboBox<String> selectCategory;
    private javax.swing.JSpinner spinnerQty;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSupplier;
    private javax.swing.JTextField txtUnit;
    // End of variables declaration//GEN-END:variables
}
