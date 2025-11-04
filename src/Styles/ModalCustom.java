
package Styles;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class ModalCustom {
    
    //Custom for info Dialog 
    public static void showInfo (String message, String title) {
        JOptionPane.showMessageDialog(
                null, 
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    //Custom Error Dialog 
    public static void showError(String message, String title) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }
    //Custom Warning Dialog
    public static void showWarning(String message, String title) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.WARNING_MESSAGE
        );
    }
    
    // Custom confirmation Dialog Yes or no
    public static boolean showConfirm(String message, String title) {
        int result = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    //Custom modal Flatlaf style 
    public static void showStyledInfo(String message, String title) {
         UIManager.put("OptionPane.background", new Color(245, 247, 250));
         UIManager.put("Panel.background", new Color(245, 247, 250));
         UIManager.put("OptionPane.messageForeground", new Color(40, 40, 40));
         UIManager.put("Button.arc", 15);
         UIManager.put("Component.arc", 15);
         UIManager.put("Button.background", new Color(33, 150, 243));
         UIManager.put("Button.foreground", Color.white);
         UIManager.put("OptionPane.font", new Font("Segoe UI", Font.PLAIN, 24));
         
         JOptionPane.showMessageDialog(null,message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
