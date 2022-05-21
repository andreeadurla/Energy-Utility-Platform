package ds.assignment.rpc.views.styles;

import javax.swing.*;
import java.awt.*;

public class Style {

    public static void styleTextField(JTextField text, int width, int height, Font font) {
        text.setFont(font);
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setPreferredSize(new Dimension(width, height));
    }

    public static void styleButton(JButton button, int width, int height, Font font) {
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
    }

    public static void styleLabel(JLabel title, Font font) {
        title.setFont(font);
    }

    public static void styleTextArea(JTextArea text, int width, int height, Font font) {
        text.setFont(font);
        text.setPreferredSize(new Dimension(width, height));
    }

    public static void styleComboBox(JComboBox comboBox, int width, int height, Font font) {
        comboBox.setFont(font);
        comboBox.setPreferredSize(new Dimension(width, height));
    }

    public static void styleErrorArea(JTextArea text, int noColumns) {
        text.setLineWrap(true);
        text.setEditable(false);
        text.setVisible(false);
        text.setColumns(noColumns);
        text.setOpaque(false);
        text.setForeground(Color.RED);
    }
}
