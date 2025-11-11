
package AdminPanel;

import Database_config.DbConnection;
import Styles.ModalCustom;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author luisr
 */
public class AdminDashboardCharts extends javax.swing.JPanel {

    private int currentUserId;
    private String role;
    
    public AdminDashboardCharts(int userId, String role) {
        this.currentUserId = userId;
        this.role = role;
        initComponents();
        initCharts();
        
        setPreferredSize(new java.awt.Dimension(665, 772));
    }

    private void initCharts() {

        // PIE
        JFreeChart pieChart = createRequestStatusChart();
        ChartPanel pieCP = new ChartPanel(pieChart);
        pieChartPanel.setLayout(new BorderLayout());
        pieChartPanel.add(pieCP, BorderLayout.CENTER);
        pieChartPanel.revalidate();
        pieChartPanel.repaint();

        // LINE
        JFreeChart lineChart = createRequestOverTimeChart();
        ChartPanel lineCP = new ChartPanel(lineChart);
//        lineChartPanel.setLayout(new BorderLayout());
//        lineChartPanel.add(lineCP, BorderLayout.CENTER);
//        lineChartPanel.revalidate();
//        lineChartPanel.repaint();
    }

    private JFreeChart createRequestStatusChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> data = getRequestStatusCounts(currentUserId);

        if (data.isEmpty()) {
            dataset.setValue("Pending", 0);
            dataset.setValue("Approved", 0);
            dataset.setValue("Reject", 0);
        } else {
            data.forEach(dataset::setValue);
        }

        return ChartFactory.createPieChart("Request Status", dataset, true, true, false);
    }

    private JFreeChart createRequestOverTimeChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> data = getRequestOverTime(currentUserId);

        if (data.isEmpty()) {
            dataset.addValue(0, "Requests", "No Data");
        } else {
            data.forEach((month, count) -> dataset.addValue(count, "Requests", month));
        }

        return ChartFactory.createLineChart("Requests Over Time", "Month", "Requests", dataset);
    }

    private Map<String, Integer> getRequestStatusCounts(int userId) {

        Map<String, Integer> map = new java.util.HashMap<>();

        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();

            String sql;

            if (role.equalsIgnoreCase("Admin")) {
                sql = "SELECT status, COUNT(*) AS count FROM request_tbl GROUP BY status";
            } else {
                sql = "SELECT status, COUNT(*) AS count FROM request_tbl WHERE user_id = ? GROUP BY status";
            }

            PreparedStatement pst = conn.prepareStatement(sql);

            if (!role.equalsIgnoreCase("Admin")) {
                pst.setInt(1, userId);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                map.put(rs.getString("status"), rs.getInt("count"));
            }

            rs.close();
            pst.close();
            db.closeConnection();

        } catch (SQLException e) {
            ModalCustom.showError("Error loading pie chart: " + e.getMessage(), "Error");
        }

        return map;
    }

    private Map<String, Integer> getRequestOverTime(int userId) {

        Map<String, Integer> map = new java.util.LinkedHashMap<>();

        try {
            DbConnection db = new DbConnection();
            Connection conn = db.getConnection();

            String sql;

            if (role.equalsIgnoreCase("Admin")) {
                sql = "SELECT DATE_FORMAT(request_date, '%Y-%m') AS month, COUNT(*) AS count "
                        + "FROM request_tbl GROUP BY month ORDER BY month ASC";
            } else {
                sql = "SELECT DATE_FORMAT(request_date, '%Y-%m') AS month, COUNT(*) AS count "
                        + "FROM request_tbl WHERE user_id = ? GROUP BY month ORDER BY month ASC";
            }

            PreparedStatement pst = conn.prepareStatement(sql);

            if (!role.equalsIgnoreCase("Admin")) {
                pst.setInt(1, userId);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                map.put(rs.getString("month"), rs.getInt("count"));
            }

            rs.close();
            pst.close();
            db.closeConnection();

        } catch (SQLException e) {
            ModalCustom.showError("Error loading line chart: " + e.getMessage(), "Error");
        }

        return map;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        pieChartPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pieChartPanelLayout = new javax.swing.GroupLayout(pieChartPanel);
        pieChartPanel.setLayout(pieChartPanelLayout);
        pieChartPanelLayout.setHorizontalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("Request Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pieChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel2)))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel2)
                .addGap(142, 142, 142)
                .addComponent(pieChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pieChartPanel;
    // End of variables declaration//GEN-END:variables
}
