import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import pp2.BankAccount.*;

public class InitialFrame extends JFrame
{
    public InitialFrame()
    {
        super();
        setSize(new Dimension(1280, 720));
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        
        JPanel panel = createPanel();
        add(panel);
        setContentPane(panel);
        
        JPanel menu = createMenu();
        panel.add(menu);
        
        JPanel info = createInfo();
        
        JScrollPane list = createList();
        panel.add(list);
        
        JLabel title = createTitle();
        JLabel addAccount = createAddAccount();
        JLabel deposit = createDepositMoney();
        JLabel withdraw = createWithdrawMoney();
        JLabel interest = createInterestRate();
        JLabel update = createUpdateAccount();
        JLabel changeBank = createChangeBank();
        
        menu.add(title);
        menu.add(addAccount);
        menu.add(deposit);
        menu.add(withdraw);
        menu.add(interest);
        menu.add(update);
        menu.add(changeBank);

        panel.validate();
        panel.repaint();
        
    }
    
    public static void main(String[] args)
    {
        new InitialFrame();
    }
    
    public JPanel createPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(null);
        return panel;
    }
    
    public JPanel createMenu()
    {   
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, 250, 720);
        return panel;
    }
    
    public JScrollPane createList()
    {
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 1));
        panel.setBackground(Color.GRAY);
        panel.setLocation(250, 0);
        panel.setPreferredSize(new Dimension(1030, 115 * 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 5, 5, 5); // Add insets for spacing
        
        // we put the components here instead
        // assume that Jpanels are in here...
        for(int i = 1; i <= 20; i++)
        {
            BankAccountInterface b1 = new BankAccountInterface("Kim Vera", "Hdhkgsd", "Tequin", 4564756);
            b1.setLocation(0, 115 * i);
            panel.add(b1);
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(250, 0, 1030, 725);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.addMouseWheelListener(new MouseWheelListener() 
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) 
            {
                // Increase scroll sensitivity by multiplying the scroll distance
                int unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });
        
        return scrollPane;
    }
    
    public JLabel createTitle()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setLayout(null);
        label.setText("<html>" +
                       "Bank <br/> Account <br/> Center </html>");
        label.setForeground(Color.WHITE);
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(30, 30, width, height * 3);
        label.setVisible(true);
        return label;
    }
    
    public JLabel createAddAccount()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("Add Account");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 220, (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        return label;
    }
    
    public JLabel createDepositMoney()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("Deposit Money");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 260, (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        return label;
    }
    
    public JLabel createWithdrawMoney()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("Withdraw Money");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 300, (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        return label;
    }
    
    public JLabel createUpdateAccount()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("Update Account");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 160 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        return label;
    }
    
    public JLabel createInterestRate()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("Interest Rate: 0%");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 120 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        return label;
    }
    
    public JLabel createChangeBank()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("Change Bank");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 80 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        return label;
    }
    
    public JPanel createInfo()
    {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 70, 1030);
        return panel;
    }
    
    public JLabel createTitlexeew()
    {
        JLabel label = new JLabel();
        return label;
    }
    
}