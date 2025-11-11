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
import java.sql.SQLDataException;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luisr
 */
public class CategotryPanel extends javax.swing.JPanel {

    private ProductPanel productPanelRef;
    private int currentPage = 1;
    private int rowsPerPage = 5;
    private int totalRows = 0;
    public CategotryPanel(ProductPanel productRef) {
        initComponents();
        this.productPanelRef = productRef;
        setUpCategoryTable();
        
        
        TextFieldStyle.customInputFields(txtCategoryName, "Enter category name");
        TextFieldStyle.customInputFields(txtDescription, "Enter description");
        TextFieldStyle.SearchStyle(txtSearch, "Search...");
        
        
        ButtonStyles.setAdd(btnAdd);
        ButtonStyles.setEdit(btnUpdate);
        ButtonStyles.setDelete(btnDelete);
        ButtonStyles.setDark(btnSearch);
        ButtonStyles.setDark(btnNext);
        ButtonStyles.setDark(btnPrev);
    }

    private void setUpCategoryTable () {
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Description"); 
        
        tblCategory.setModel(model);
        tblCategory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        tblCategory.setRowHeight(40);
        tblCategory.setGridColor(new Color(220, 220, 220));
        tblCategory.setShowVerticalLines(true);
        tblCategory.setShowHorizontalLines(true);
        tblCategory.setSelectionBackground(new Color(180, 180, 180));
        tblCategory.setSelectionForeground(Color.WHITE);
        
        tblCategory.getTableHeader().setBackground(new Color(60, 60, 60));
        tblCategory.getTableHeader().setForeground(Color.WHITE);
        tblCategory.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        
        tblCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row  = tblCategory.getSelectedRow();
                if (row != -1) {
                    txtCategoryName.setText(tblCategory.getValueAt(row, 1).toString());
                    txtDescription.setText(tblCategory.getValueAt(row, 2) != null ? tblCategory.getValueAt(row, 2).toString() : "");
                }
            }
});
        
        loadCategoriesTable();
    }
    
    private void loadCategoriesTable() {
        DefaultTableModel model = (DefaultTableModel) tblCategory.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String countSql = "SELECT COUNT(*) FROM categories_tbl";
            PreparedStatement countPst = conn.prepareStatement(countSql);
            ResultSet countRs = countPst.executeQuery();
            
            if (countRs.next()) {
                totalRows = countRs.getInt(1);
            }
            
            int offset = (currentPage - 1) * rowsPerPage;
            
            
            String sql = "SELECT * FROM categories_tbl LIMIT ? OFFSET ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, rowsPerPage);
            pst.setInt(2, offset);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("description")
                });
            }
            pst.close();
            rs.close();
            db.closeConnection();
            
            updatePageInfo();
        } catch(SQLException e ) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }   
    }
    
    private void updatePageInfo () {
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        lblPageInfo.setText(currentPage + " / " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }
    
    private void searchCategories() {
        String keyword = txtSearch.getText().trim();
        
        DefaultTableModel model = (DefaultTableModel) tblCategory.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String sql = "SELECT * FROM categories_tbl WHERE category_name LIKE ? OR description LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("description")
                });
            }
            
            lblPageInfo.setText("Search Result");
            btnNext.setEnabled(false);
            btnPrev.setEnabled(false);
            
            pst.close();
            rs.close();
            db.closeConnection();
        } catch (SQLException e) {
            ModalCustom.showError("Search Error: " + e.getMessage(), "Error");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        txtDescription = new javax.swing.JTextField();
        txtCategoryName = new javax.swing.JTextField();
        btnPrev = new javax.swing.JButton();
        lblPageInfo = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setText("Category");

        tblCategory.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCategory);

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

        lblPageInfo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPageInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPageInfo.setText("1 / 10");

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescription))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(btnPrev)
                        .addGap(18, 18, 18)
                        .addComponent(lblPageInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(128, 128, 128)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
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
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPageInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
       String categoryName = txtCategoryName.getText().trim();
       String description = txtDescription.getText().trim();
       
       if (categoryName.isEmpty()) {
           ModalCustom.showError("Category Name is Required", "Input Error");
           return;
       }
       
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String sql = "INSERT INTO categories_tbl (category_name, description) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, categoryName);
            pst.setString(2, description);
            pst.executeUpdate();
            ModalCustom.showInfo("Category addedd successfully", "Success");
            txtCategoryName.setText("");
            txtDescription.setText("");
            loadCategoriesTable();
            
            if (productPanelRef != null) {
                productPanelRef.loadCategoriesToComboBox();
            }
        } catch (SQLException e) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }
         db.closeConnection();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectedRow = tblCategory.getSelectedRow();
        
        if (selectedRow == -1) {
            ModalCustom.showError("Please select a category to update", "Selection Error");
            return;
        }
        
        int categoryId = (int) tblCategory.getValueAt(selectedRow, 0);
        String newName = txtCategoryName.getText().trim();
        String newDescription = txtDescription.getText().trim();
        
        if (newName.isEmpty()) {
            ModalCustom.showError("Category name is required", "Input Error");
            return;
        }
        
        DbConnection db = new DbConnection();
        Connection conn  = db.getConnection();
        
        try {
           String sql = "UPDATE categories_tbl SET category_name = ?, description = ? WHERE category_id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newName);
            pst.setString(2, newDescription);
            pst.setInt(3, categoryId);
            
            pst.executeUpdate();
            
            ModalCustom.showInfo("Category updated successfully", "Success");
            
            txtCategoryName.setText("");
            txtDescription.setText("");
            
            loadCategoriesTable();
            
            if (productPanelRef != null) {
                productPanelRef.loadCategoriesToComboBox();
            }
            
            pst.close();
            conn.close();
            db.closeConnection();
        } catch(SQLException e) {
        
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

      int selectRow = tblCategory.getSelectedRow();
      if (selectRow == -1) {
          ModalCustom.showError("Please select a category", "Selection Error");
          return;
      }
      
      int categoryId = (int) tblCategory.getValueAt(selectRow, 0);
      
      DbConnection db = new DbConnection();
      Connection conn = db.getConnection();
      
      try {
          String sql = "DELETE FROM categories_tbl WHERE category_id = ?";
          PreparedStatement pst = conn.prepareStatement(sql);
          pst.setInt(1, categoryId);
          pst.executeUpdate();
          
          ModalCustom.showInfo("Category deleted successfully", "Success");
          loadCategoriesTable();
      } catch(SQLException e) {
          ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
      } 
      db.closeConnection();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadCategoriesTable();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            loadCategoriesTable();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtSearch.getText().trim().isEmpty()) {
            currentPage = 1;
            loadCategoriesTable();
        } else {
            searchCategories();
        }
        
        
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
    private javax.swing.JTable tblCategory;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
