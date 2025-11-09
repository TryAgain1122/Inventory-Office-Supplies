
package inventory_office_supplies;

import StaffPanel.RequestHistory;
import StaffPanel.RequestsPanel;
import StaffPanel.StaffDashboardChart;
import StaffPanel.StockPanel;
import Styles.ModalCustom;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Image;


public class StaffDashboard extends javax.swing.JFrame {
    
    private RequestsPanel requestsPanel = new RequestsPanel();
    private RequestHistory requestHistory = new RequestHistory();
    private StaffDashboardChart staffDashboardChart = new StaffDashboardChart();
    private StockPanel stockPanel = new StockPanel();

    
    public StaffDashboard() {
        initComponents();
//        
        ImageIcon logoutIcon = new ImageIcon(getClass().getResource("../Images/logout.png"));
        Image logout = logoutIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        lblLogout.setIcon(new ImageIcon(logout));
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(staffDashboardChart, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
        
    }

    public void switchPanel (JPanel newPanel) {
        mainPanel.removeAll();
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(newPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void logOutAction() {
        boolean confirmed = ModalCustom.showConfirm("Are you want to logout?", "Are you sure?");
        if (confirmed) {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true); 
            this.dispose();
        }

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideBarPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblRequestHistory = new javax.swing.JLabel();
        lblReports = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        lblHomeClicked = new javax.swing.JLabel();
        lblNewRequestClick = new javax.swing.JLabel();
        lblStockClick = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideBarPanel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        lblRequestHistory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRequestHistory.setText("My Request History");
        lblRequestHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRequestHistoryMouseClicked(evt);
            }
        });

        lblReports.setText("0");

        lblCategory.setText("0");

        lblLogout.setText("0");
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });

        lblHome.setText("0");

        lblHomeClicked.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHomeClicked.setText("Home");
        lblHomeClicked.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeClickedMouseClicked(evt);
            }
        });

        lblNewRequestClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNewRequestClick.setText("New Request");
        lblNewRequestClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNewRequestClickMouseClicked(evt);
            }
        });

        lblStockClick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblStockClick.setText("Stocks");
        lblStockClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStockClickMouseClicked(evt);
            }
        });

        jLabel3.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Welcome");

        javax.swing.GroupLayout sideBarPanelLayout = new javax.swing.GroupLayout(sideBarPanel);
        sideBarPanel.setLayout(sideBarPanelLayout);
        sideBarPanelLayout.setHorizontalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(sideBarPanelLayout.createSequentialGroup()
                            .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHomeClicked)
                                    .addComponent(lblNewRequestClick, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel4)))
                        .addGroup(sideBarPanelLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(40, 40, 40)
                            .addComponent(lblStockClick)
                            .addGap(126, 126, 126))
                        .addGroup(sideBarPanelLayout.createSequentialGroup()
                            .addComponent(lblReports, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblRequestHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        sideBarPanelLayout.setVerticalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHomeClicked, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNewRequestClick, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReports, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRequestHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockClick, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(268, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 739, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 706, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sideBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblNewRequestClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNewRequestClickMouseClicked
        switchPanel(requestsPanel);
    }//GEN-LAST:event_lblNewRequestClickMouseClicked

    private void lblRequestHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRequestHistoryMouseClicked
        switchPanel(requestHistory);
    }//GEN-LAST:event_lblRequestHistoryMouseClicked

    private void lblHomeClickedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeClickedMouseClicked
        switchPanel(staffDashboardChart);
    }//GEN-LAST:event_lblHomeClickedMouseClicked

    private void lblStockClickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStockClickMouseClicked
        switchPanel(stockPanel);
    }//GEN-LAST:event_lblStockClickMouseClicked

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
       logOutAction();
    }//GEN-LAST:event_lblLogoutMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StaffDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblHomeClicked;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblNewRequestClick;
    private javax.swing.JLabel lblReports;
    private javax.swing.JLabel lblRequestHistory;
    private javax.swing.JLabel lblStockClick;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel sideBarPanel;
    // End of variables declaration//GEN-END:variables
}
