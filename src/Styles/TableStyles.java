/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Styles;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


public class TableStyles {
    
      public static void CustomTable(JTable table) {
        table.setRowHeight(40);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setSelectionBackground(new Color(180, 180, 180));
        table.setSelectionForeground(Color.WHITE);
        
        table.getTableHeader().setBackground(new Color(60, 60, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
      }
      public static void applyDefault (JTable table) {
          
        table.setRowHeight(40);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setSelectionBackground(new Color(180, 180, 180));
        table.setSelectionForeground(Color.WHITE);
        
        table.getTableHeader().setBackground(new Color(60, 60, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        
          DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
          centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
          
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      }
      
          public static JScrollPane createScrollableTable(JTable table) {
            JScrollPane scrollPane = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        // Keep the table height fixed to its preferred height
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        return scrollPane;
    }
          
    public static void autoResizeColumns (JTable table) {
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        
        // Computer header Width
        for (int col = 0; col < columnCount; col++) {
            int maxWidth = 0;
            
            JTableHeader header = table.getTableHeader();
            String headerText = table.getColumnModel().getColumn(col).getHeaderValue().toString();
            int headerWidth = header.getFontMetrics(header.getFont()).stringWidth(headerText);
            maxWidth = Math.max(maxWidth, headerWidth);
            
            //Computer cell width
            for (int row = 0; row < rowCount; row++) {
                Object value = table.getValueAt(row, col);
                if (value != null) {
                    int cellWidth = table.getFontMetrics(table.getFont()).stringWidth(value.toString());
                    maxWidth = Math.max(maxWidth, cellWidth);
                }
            }
            
            maxWidth += 30;
            
            table.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
        }
    }
    
    public static JScrollPane createJScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        return scrollPane;
    }
}
