import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

/**
 * Created by BangoCs on 11/14/2017.
 */
public class GUI extends JFrame{
    private JTextField txtUsername;
    private JTextField txtPsw;
    private JButton startServerButton;
    private JButton stopServerButton;
    private JPanel rootPanel;
    private JCheckBox loginWithUploadCheckBox;
    private JCheckBox logoutWithDownloadCheckBox;
    public static boolean checked = false;
    public static boolean checkedLogout = false;

    public GUI() {


        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MyEchoClient client = new MyEchoClient();
                try {
                       client.login(checked, txtUsername.getText(), txtPsw.getText());

                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Login failed at passing args to login()","Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });
        stopServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MyEchoClient client = new MyEchoClient();
                try {
                    if (!checkedLogout){
                        client.logout();
                    }
                    else {

                        client.logoutWithDownload(txtUsername.getText());
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Logout failed at passing args","Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }

            }
        });
        loginWithUploadCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    checked = true;

                } else {
                   checked = false;
                };
            }
        });
        logoutWithDownloadCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    checkedLogout = true;

                } else {
                    checkedLogout = false;
                };
            }
        });
    }
}
