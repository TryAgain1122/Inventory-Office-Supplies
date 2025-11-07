
package inventory_office_supplies;

import AdminPanel.CategotryPanel;
import AdminPanel.InventoryPanel;
import AdminPanel.InventoryPanel;
import AdminPanel.ProductPanel;
import AdminPanel.UserPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author luisr
 */
public class AdminDashboard extends javax.swing.JFrame {
    
    private UserPanel userPanel = new UserPanel();
    private InventoryPanel inventoryPanel = new InventoryPanel();
    private ProductPanel productPanel = new ProductPanel(inventoryPanel);
    private CategotryPanel categotryPanel = new CategotryPanel(productPanel);
    
    
    public AdminDashboard() {
        initComponents();
        
        //Dashboard Icon 
        ImageIcon dashboardIcon = new ImageIcon(getClass().getResource("../Images/dashboard.png"));
        Image dashboard = dashboardIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblDashboard.setIcon(new ImageIcon(dashboard));
        
        //Home Icon 
        ImageIcon homeIcon = new ImageIcon(getClass().getResource("../Images/home.png"));
        Image home = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblHome.setIcon(new ImageIcon(home));
        
        //User Icon
        ImageIcon UserIcon = new ImageIcon(getClass().getResource("../Images/user.png"));
        Image user = UserIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblUser.setIcon(new ImageIcon(user));
        
        //Categories
        ImageIcon categorIcon = new ImageIcon(getClass().getResource("../Images/categories.png"));
        Image category = categorIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblCategory.setIcon(new ImageIcon(category));
        
        //Reports
        ImageIcon reportIcon = new ImageIcon(getClass().getResource("../Images/report.png"));
        Image report = reportIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblReports.setIcon(new ImageIcon(report));
     
        //Inventory
        ImageIcon inventoryIcon = new ImageIcon(getClass().getResource("../Images/inventory.png"));
        Image inventory = inventoryIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblInventory.setIcon(new ImageIcon(inventory));
        
        //Requests
        ImageIcon requestsIcon = new ImageIcon(getClass().getResource("../Images/Requests.png"));
        Image requests = requestsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblRequests.setIcon(new ImageIcon(requests));
        
        //Logout
        ImageIcon logoutIcon = new ImageIcon(getClass().getResource("../Images/logout.png"));
        Image logout = logoutIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblLogout.setIcon(new ImageIcon(logout));
        
        ImageIcon productIcon = new ImageIcon(getClass().getResource("../Images/products.png"));
        Image product = productIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblProducts.setIcon(new ImageIcon(product));
        
        //User Icon
        ImageIcon UserIcon1 = new ImageIcon(getClass().getResource("../Images/user.png"));
        Image user1 = UserIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblReports.setIcon(new ImageIcon(user1));
        
        userPanel = new UserPanel();
        mainSwitchPanel.setLayout(new BorderLayout());
        mainSwitchPanel.add(userPanel, BorderLayout.CENTER);
        mainSwitchPanel.revalidate();
        mainSwitchPanel.repaint();

    }
    
    public void refreshUsersTable() {
        if (userPanel != null) {
            userPanel.refreshUsersTable();
        }
    }
    
    private void switchPanel(JPanel newPanel) {
       mainSwitchPanel.removeAll();
       mainSwitchPanel.setLayout(new BorderLayout());
       mainSwitchPanel.add(newPanel, BorderLayout.CENTER);
       mainSwitchPanel.revalidate();
       mainSwitchPanel.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblUserClick = new javax.swing.JLabel();
        txtProductClick = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblInventoryClick = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDashboard = new javax.swing.JLabel();
        lblReports = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblInventory = new javax.swing.JLabel();
        lblRequests = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        txtCategoryClick = new javax.swing.JLabel();
        lblProducts = new javax.swing.JLabel();
        mainSwitchPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideBarPanel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Dashboard");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Home");

        lblUserClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUserClick.setText("Users");
        lblUserClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserClickMouseClicked(evt);
            }
        });

        txtProductClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtProductClick.setText("Products");
        txtProductClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProductClickMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Reports");

        lblInventoryClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblInventoryClick.setText("Inventory Logs");
        lblInventoryClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInventoryClickMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Requests");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Logout");

        lblDashboard.setText("0");

        lblReports.setText("0");

        lblUser.setText("0");

        lblCategory.setText("0");

        lblInventory.setText("0");

        lblRequests.setText("0");

        lblLogout.setText("0");

        lblHome.setText("0");

        txtCategoryClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtCategoryClick.setText("Categories");
        txtCategoryClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCategoryClickMouseClicked(evt);
            }
        });

        lblProducts.setText("0");

        javax.swing.GroupLayout sideBarPanelLayout = new javax.swing.GroupLayout(sideBarPanel);
        sideBarPanel.setLayout(sideBarPanelLayout);
        sideBarPanelLayout.setHorizontalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sideBarPanelLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sideBarPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(lblUserClick, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCategoryClick, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblReports, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(lblInventoryClick)
                            .addComponent(jLabel7)
                            .addComponent(txtProductClick, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        sideBarPanelLayout.setVerticalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCategoryClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReports, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInventoryClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout mainSwitchPanelLayout = new javax.swing.GroupLayout(mainSwitchPanel);
        mainSwitchPanel.setLayout(mainSwitchPanelLayout);
        mainSwitchPanelLayout.setHorizontalGroup(
            mainSwitchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
        );
        mainSwitchPanelLayout.setVerticalGroup(
            mainSwitchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(sideBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainSwitchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sideBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainSwitchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblUserClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserClickMouseClicked
       if (userPanel == null) {
           userPanel = new UserPanel();
       }
        switchPanel(userPanel);
    }//GEN-LAST:event_lblUserClickMouseClicked

    private void lblInventoryClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInventoryClickMouseClicked
        switchPanel(inventoryPanel);
        
    }//GEN-LAST:event_lblInventoryClickMouseClicked

    private void txtProductClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProductClickMouseClicked
        switchPanel(productPanel);
    }//GEN-LAST:event_txtProductClickMouseClicked

    private void txtCategoryClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCategoryClickMouseClicked
         switchPanel(categotryPanel);
    }//GEN-LAST:event_txtCategoryClickMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
//        try {
//            FlatLightLaf.setup();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblInventory;
    private javax.swing.JLabel lblInventoryClick;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblReports;
    private javax.swing.JLabel lblRequests;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserClick;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainSwitchPanel;
    private javax.swing.JPanel sideBarPanel;
    private javax.swing.JLabel txtCategoryClick;
    private javax.swing.JLabel txtProductClick;
    // End of variables declaration//GEN-END:variables
}
