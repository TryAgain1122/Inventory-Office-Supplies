/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
