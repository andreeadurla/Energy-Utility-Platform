package ds.assignment.rpc.views.home;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {

    private String username;
    private JMenuItem logoutButton = new JMenuItem("Logout");
    private JTabbedPane tabbedPane = new JTabbedPane();

    public HomeView(String username) {
        this.username = username;

        initUI();

        this.pack();
        this.setTitle("Energy platform");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    private void initUI() {

        createMenu();

        //Tabbed Pane
        Font font = new Font("Arial", Font.PLAIN, 15);
        tabbedPane.setFont(font);
        this.add(tabbedPane);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(Box.createHorizontalGlue());

        JMenu menu = new JMenu(username);
        Font font = new Font("Arial", Font.ITALIC, 15);
        menu.setFont(font);
        menu.add(logoutButton);

        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    public void addPanel(String name, JPanel panel) {
        tabbedPane.addTab(name, panel);
    }

    public void addLogoutButtonListener(ActionListener e) {
        logoutButton.addActionListener(e);
    }
}
