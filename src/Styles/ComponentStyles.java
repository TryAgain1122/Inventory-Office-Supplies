package Styles;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class ComponentStyles {
    
    
    // ============================
// ✅ JCALENDAR STYLE
// ============================
public static void styleJCalendar(com.toedter.calendar.JDateChooser dateChooser) {

    // Style the date editor (input box)
    JComponent editor = dateChooser.getDateEditor().getUiComponent();
    editor.putClientProperty(FlatClientProperties.STYLE, ""
            + "arc: 10;"
            + "borderWidth: 2;"
            + "borderColor: #A0A0A0;"
            + "focusedBorderColor: #4A90E2;"
            + "background: #F5F5F5;"
    );

    com.toedter.calendar.JCalendar cal = dateChooser.getJCalendar();
    
    // ✅ Custom calendar popup (remove blue, apply grey styling)
    // Remove BLUE highlight background
    cal.setDecorationBackgroundColor(new Color(245, 245, 245)); // Light grey
    cal.setBackground(new Color(255, 255, 255, 0));            // Transparent
    cal.setOpaque(false);

    // ✅ Remove blue selection — replace with GREY
    cal.setSundayForeground(new Color(180, 0, 0));             // Dark red Sunday
    cal.setWeekdayForeground(new Color(60, 60, 60));           // Normal text
    cal.setForeground(new Color(30, 30, 30));

    // ✅ Cleaner look
    cal.setTodayButtonVisible(false);
    cal.setNullDateButtonVisible(false);

    // ✅ Slightly bigger popup
    cal.setPreferredSize(new java.awt.Dimension(330, 280));
}



    public static void styleComboBox(JComboBox<?> combo) {
        combo.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc: 10;"
                + "borderWidth: 2;"
                + "borderColor: #A0A0A0;"
                + "focusedBorderColor: #4A90E2;"
                + "background: #F8F8F8;"
        );
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }



    // ============================
    // ✅ PANEL (CARD STYLE)
    // ============================
    public static void stylePanel(JPanel panel) {
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc: 20;"
                + "background: #FFFFFF;"
                + "borderWidth: 1;"
                + "borderColor: #E0E0E0;"
        );
    }

    // ============================
    // ✅ RADIO BUTTON STYLE
    // ============================
    public static void styleRadio(JRadioButton rbtn) {
        rbtn.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        rbtn.setForeground(new Color(55, 55, 55));

        rbtn.putClientProperty(FlatClientProperties.STYLE, ""
                + "icon.arc: 8;"
                + "background: #00000000;"  // ✅ Transparent without error
        );
    }

}
