
package inventory_office_supplies;

import AdminPanel.CategotryPanel;
import AdminPanel.AdminDashboardCharts;
import AdminPanel.InventoryPanel;
import AdminPanel.ProductPanel;
import AdminPanel.RequestInAdminPanel;
import AdminPanel.UserPanel;
import Styles.ModalCustom;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author luisr
 */
public class AdminDashboard extends javax.swing.JFrame {
    
    private UserPanel userPanel = new UserPanel();
    private InventoryPanel inventoryPanel = new InventoryPanel();
    private ProductPanel productPanel = new ProductPanel(inventoryPanel);
    private CategotryPanel categotryPanel = new CategotryPanel(productPanel);
    private RequestInAdminPanel requestPanel = new RequestInAdminPanel();
    private LoginForm loginForm = new LoginForm();
    private AdminDashboardCharts dashboardCharts = new AdminDashboardCharts(0, "Admin");
    
    public AdminDashboard() {
        initComponents();
        
        JScrollPane scroll = new JScrollPane(dashboardCharts);
scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //Dashboard Icon 
//        ImageIcon dashboardIcon = new ImageIcon(getClass().getResource("../Images/dashboard.png"));
//        Image dashboard = dashboardIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//        lblDashboard.setIcon(new ImageIcon(dashboard));
        
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
        //ImageIcon reportIcon = new ImageIcon(getClass().getResource("../Images/report.png"));
        //Image report = reportIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        //lblReports.setIcon(new ImageIcon(report));
     
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
//        ImageIcon UserIcon1 = new ImageIcon(getClass().getResource("../Images/user.png"));
//        Image user1 = UserIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//        lblReports.setIcon(new ImageIcon(user1));
        
        //Default Panel
        mainSwitchPanel.setLayout(new BorderLayout());
        mainSwitchPanel.add(dashboardCharts, BorderLayout.CENTER);
        mainSwitchPanel.revalidate();
        mainSwitchPanel.repaint();

    }
    
//    public void refreshUsersTable() {
//        if (userPanel != null) {
//            userPanel.refreshUsersTable();
//        }
//    }
    
    
    private void switchPanel(JPanel newPanel) {
       mainSwitchPanel.removeAll();
       mainSwitchPanel.setLayout(new BorderLayout());
       mainSwitchPanel.add(newPanel, BorderLayout.CENTER);
       mainSwitchPanel.revalidate();
       mainSwitchPanel.repaint();
    }
    
        private void logoutAction (){
         
        boolean confirm = ModalCustom.showConfirm("Are you sure you want to logout", "Confirmation");
        
        if (confirm){
        loginForm.setVisible(true);
        this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainSwitchPanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblUserClick = new javax.swing.JLabel();
        txtProductClick = new javax.swing.JLabel();
        lblInventoryClick = new javax.swing.JLabel();
        lblRequestsClick = new javax.swing.JLabel();
        lblLogout1 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblInventory = new javax.swing.JLabel();
        lblRequests = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        txtCategoryClick = new javax.swing.JLabel();
        lblProducts = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout mainSwitchPanelLayout = new javax.swing.GroupLayout(mainSwitchPanel);
        mainSwitchPanel.setLayout(mainSwitchPanelLayout);
        mainSwitchPanelLayout.setHorizontalGroup(
            mainSwitchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
        );
        mainSwitchPanelLayout.setVerticalGroup(
            mainSwitchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 726, Short.MAX_VALUE)
        );

        sideBarPanel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Home");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

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

        lblInventoryClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblInventoryClick.setText("Inventory Logs");
        lblInventoryClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInventoryClickMouseClicked(evt);
            }
        });

        lblRequestsClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRequestsClick.setText("Requests");
        lblRequestsClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRequestsClickMouseClicked(evt);
            }
        });

        lblLogout1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLogout1.setText("Logout");
        lblLogout1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogout1MouseClicked(evt);
            }
        });

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
                .addGap(48, 48, 48)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblUserClick, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCategoryClick, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLogout1))
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInventoryClick)
                            .addComponent(lblRequestsClick)
                            .addComponent(txtProductClick, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        sideBarPanelLayout.setVerticalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGap(137, 137, 137)
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
                    .addComponent(lblInventoryClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRequestsClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogout1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(mainSwitchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(sideBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void lblLogout1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogout1MouseClicked
        logoutAction();
    }//GEN-LAST:event_lblLogout1MouseClicked

    private void lblRequestsClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRequestsClickMouseClicked
        switchPanel(requestPanel);
    }//GEN-LAST:event_lblRequestsClickMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        switchPanel(userPanel);
    }//GEN-LAST:event_jLabel2MouseClicked

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblInventory;
    private javax.swing.JLabel lblInventoryClick;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblLogout1;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblRequests;
    private javax.swing.JLabel lblRequestsClick;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserClick;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainSwitchPanel;
    private javax.swing.JPanel sideBarPanel;
    private javax.swing.JLabel txtCategoryClick;
    private javax.swing.JLabel txtProductClick;
    // End of variables declaration//GEN-END:variables
}
