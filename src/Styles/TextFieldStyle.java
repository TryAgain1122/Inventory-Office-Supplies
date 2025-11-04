
package Styles;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;


public class TextFieldStyle {
    
    public static void applyUnderlineStyle(JTextField textField, String placeholder) {
        
        //Place Holder Text
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        
        //Remove rounded corners underline style onlyy
        textField.putClientProperty("JComponent.roundRect", false);
        
        //Show clear button
        textField.putClientProperty("JTextField.showClearButton", true);
        
        //For focused and unfocused color
        Color unfocusedLine = new Color(210, 210, 210);
        Color focusedLine = new Color(30, 30, 30);
        
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, unfocusedLine));
        
        textField.setOpaque(false);
        textField.setBackground(new Color(0, 0, 0, 0));
        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, focusedLine));
            }
            
            @Override 
            public void focusLost (java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, unfocusedLine));
            }
        });
    }
    
    public static void customInputFields(JTextField textField, String placeholder) {
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        textField.putClientProperty("JComponent.roundRect", true);
        textField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
    }
    
    public static void SearchStyle(JTextField textField, String placeholder) {
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        textField.putClientProperty("JComponent.roundRect", true);
        textField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        textField.setPreferredSize(new java.awt.Dimension(512, 40));
    }
}
