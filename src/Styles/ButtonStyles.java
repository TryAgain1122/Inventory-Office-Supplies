/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Styles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;

public class ButtonStyles {
    public static void setColor (JButton button, Color bgColor, Color textColor) {
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor);
            }
});
        
    }
    
        // Para sa common buttons (madali kabisaduhin)
    public static void setAdd(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        setColor(button, new Color(46, 204, 113), Color.WHITE); // Green
    }

    public static void setEdit(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        setColor(button, new Color(52, 152, 219), Color.WHITE); // Blue
    }

    public static void setDelete(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        setColor(button, new Color(231, 76, 60), Color.WHITE); // Red
    }

    public static void setDark(JButton button) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        setColor(button, new Color(45, 45, 45), Color.WHITE); // Dark gray
    }
}
