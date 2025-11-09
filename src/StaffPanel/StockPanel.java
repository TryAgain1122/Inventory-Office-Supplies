
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class StockPanel extends javax.swing.JPanel {

    private int currrentPage = 1;
    private int rowsPerPage = 6;
    private int totalPages = 1;
    public StockPanel() {
        initComponents();
        loadStockTable();
        loadCategories();
        
        TextFieldStyle.customInputFields(txtSearch, "Search...");
        
        ComponentStyles.styleComboBox(cmbCategory);
        ButtonStyles.setDark(btnSearch);
        ButtonStyles.setDark(btnPrev);
        ButtonStyles.setDark(btnNext);
        TableStyles.CustomTable(tblStocks);
    }
    
    private void loadStockTable() {
            DefaultTableModel model = (DefaultTableModel) tblStocks.getModel();
            model.setRowCount(0); // Clear existing rows

            String searchText = txtSearch.getText().trim();
            if (searchText.equals("Search")) searchText = "";

            String category = cmbCategory.getSelectedItem().toString();
            if (category.equals("Category")) category = "%";

            int offset = (currrentPage - 1) * rowsPerPage;

            DbConnection db = new DbConnection();
            Connection conn;
            PreparedStatement pst;
            ResultSet rs = null;
            PreparedStatement countPst;
            ResultSet countRs;

            try {
                conn = db.getConnection();

                String countSql = "SELECT "
                                    + "COUNT(*) AS total "
                                  + "FROM products_tbl p "
                                  + "JOIN categories_tbl c ON p.category_id = c.category_id "
                                  + "WHERE p.product_name LIKE ? AND c.category_name LIKE ?";
                countPst = conn.prepareStatement(countSql);
                countPst.setString(1, "%" + searchText + "%");
                countPst.setString(2, "%" + category + "%");
                countRs = countPst.executeQuery();

                int totalRows = 0;
                if (countRs.next()) totalRows = countRs.getInt("total");
                totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);


                String sql = "SELECT p.product_id, p.product_name, c.category_name, p.quantity, "
                           + "CASE WHEN p.quantity >= 50 THEN 'OK' "
                           + "WHEN p.quantity >= 10 THEN 'Low' ELSE 'Critical' END AS status "
                           + "FROM products_tbl p "
                           + "JOIN categories_tbl c ON p.category_id = c.category_id "
                           + "WHERE p.product_name LIKE ? AND c.category_name LIKE ? "
                           + "ORDER BY p.product_name ASC "
                           + "LIMIT ? OFFSET ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + searchText + "%");
                pst.setString(2, "%" + category + "%");
                pst.setInt(3, rowsPerPage);
                pst.setInt(4, offset);

                rs = pst.executeQuery();
                boolean hasData = false;
                while(rs.next()) {
                    hasData = true;
                    model.addRow(new Object[] {
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                    });
                }

                if (!hasData) {
                    model.addRow(new Object[] {" - ", "No Data found", "-", "-", "-"});
                }

                txtPage.setText(currrentPage + " / " + totalPages);
                rs.close();
                pst.close();
                conn.close();

            } catch(Exception e) {
                ModalCustom.showError("Error loading stock: " + e.getMessage(), "Invalid");
        }
    }
    
    private void loadCategories () {
        DbConnection db = new DbConnection();
        PreparedStatement pst;
        Connection conn;
        ResultSet rs;
        
        try {
            conn = db.getConnection();
            String sql = "SELECT category_name FROM categories_tbl";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            cmbCategory.removeAllItems();
            cmbCategory.addItem("Category");
            while (rs.next()) {
                cmbCategory.addItem(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            ModalCustom.showError("Error loading categories: " + e.getMessage(), "Error");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        cmbCategory = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        txtPage = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStocks = new javax.swing.JTable();

        jTextField1.setText("jTextField1");

        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Category", " " }));

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        txtPage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPage.setText("1 / 10");

        btnPrev.setText("Prev");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("Stock");

        tblStocks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Category", "quantity", "status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblStocks);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(144, 144, 144)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPage)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(134, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        currrentPage = 1;
        loadStockTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currrentPage > 1) {
            currrentPage--;
            loadStockTable();
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currrentPage < totalPages) {
            currrentPage++;
            loadStockTable();
        }

    }//GEN-LAST:event_btnNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblStocks;
    private javax.swing.JLabel txtPage;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables


}
