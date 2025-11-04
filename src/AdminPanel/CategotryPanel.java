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

    /**
     * Creates new form CategotryPanel
     */
    public CategotryPanel() {
        initComponents();
        setUpCategoryTable();
        
        
        TextFieldStyle.customInputFields(txtCategoryName, "Enter category name");
        TextFieldStyle.customInputFields(txtDescription, "Enter description");
        TextFieldStyle.SearchStyle(txtSearch, "Search...");
        
        
        ButtonStyles.setAdd(btnAdd);
        ButtonStyles.setEdit(btnUpdate);
        ButtonStyles.setDelete(btnDelete);
        ButtonStyles.setDark(btnSearch);
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
        
        loadCategoriesTable();
    }
    
    private void loadCategoriesTable() {
        DefaultTableModel model = (DefaultTableModel) tblCategory.getModel();
        model.setRowCount(0);
        
        DbConnection db = new DbConnection();
        Connection conn = db.getConnection();
        
        try {
            String sql = "SELECT category_id, category_name, description FROM categories_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("description")
                });
            }
        } catch(SQLException e ) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }
        
        db.closeConnection();
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                .addGap(0, 27, Short.MAX_VALUE))
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
                .addContainerGap(127, Short.MAX_VALUE))
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
            loadCategoriesTable();
        } catch (SQLException e) {
            ModalCustom.showError("Database Error: " + e.getMessage(), "Error");
        }
            
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
     
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCategory;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
