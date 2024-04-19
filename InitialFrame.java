package BankAccountThingy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountInterface;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountListPane;
import BankAccountThingy.pp2.BankAccount.Dialogs.AddBankAccount;
import BankAccountThingy.pp2.BankAccount.Dialogs.WithdrawDialog;
import BankAccountThingy.pp2.BankAccount.Dialogs.DepositDialog;
import BankAccountThingy.pp2.BankAccount.Utils.Region;

public class InitialFrame extends JFrame
{
    public static Dimension dimension = new Dimension(1280, 720);
    private JLabel withdraw;
    private JLabel deposit;
    private JLabel interest;
    private JLabel update;
    private JLabel changeBank;
    private JLabel title;
    private JLabel closeBank;
    public JPanel panel;
    public JPanel menu;
    public JPanel info;

    public JLabel titleList;
    public JLabel addAccount;
    private JLabel editAccount;
    private JLabel deleteAccount;
    private JLabel depositMoney;
    private JLabel withdrawMoney;

    public JTextField search;
    
    public BankAccountListPane pane;
    public BankAccountList list;
    public JLabel closeApplication;
    public boolean isDragging;
    public Point offset;
    private JLabel deleteBankAccount;

    public InitialFrame()
    {
        super();
        initializeComponent();
        panel = createPanel();
        add(panel);
        setContentPane(panel);
        
        menu = createMenu();
        info = createInfo();

        panel.add(menu);
        panel.add(info);
        
        //JLabels for the header
        titleList = createTitleList();
        search = createSearch();
        closeBank = createCloseBank();
        closeApplication = createCloseApp();
        
        info.add(titleList);
        info.add(search);
        info.add(closeBank);
        info.add(closeApplication);

                
        pane = createList();
        pane.setLocation(250, 40);
        panel.add(pane);

        title = createTitle();
        addAccount = createAddAccount();
        deposit = createDepositMoney();
        withdraw = createWithdrawMoney();
        interest = createInterestRate();
        update = createUpdateAccount();
        changeBank = createChangeBank();
                
        menu.add(title);
        menu.add(addAccount);
        menu.add(deposit);
        menu.add(withdraw);
        menu.add(interest);
        menu.add(update);
        menu.add(changeBank);

        panel.validate();
        panel.repaint();
        
        setVisible(true);
    }


    // STATThread   
    @Region("Static Thread Must go here")
    public static void main(String[] args)
    {
            new InitialFrame();
    }
    
    public void initializeComponent()
    {
        setVisible(false);
        setSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_SLASH)
                {
                    search.setFocusable(true);
                    search.setText("");
                    search.requestFocus();
                }
            }
        });

    }

    private JPanel addComponentsToBankAccountInterface(JPanel p)
    {
        editAccount = createEditAccount();
        deleteAccount = createDeleteAccount();
        depositMoney = createDeposit();
        withdrawMoney = createWithdraw();
        p.add(editAccount);
        p.add(deleteAccount);
        p.add(depositMoney);
        p.add(withdrawMoney);
        return p;
    }

    private JLabel createWithdraw()
    {

        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("withdraw");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(700, (getHeight() / 2)  - (height /2), width + 40, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

        });
        return label;
    }

    private JLabel createDeposit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("deposit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(570, (getHeight() / 2)  - (height /2), width + 40, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

        });
        return label;
    }

    private JLabel createDeleteAccount()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("delete");
        label.setForeground(Color.RED);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(850, (getHeight() / 2)  - (height /2), width + 40, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                //hjdshfhs
            }

        });

        return label;
    }

    private JLabel createEditAccount()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("edit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(500, (getHeight() / 2) - (height /2), width + 40, height);

        // adding an event on this one :3
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.BLACK);
            }
        });

        return label;
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
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                search.setFocusable(false);
                search.setText("[/] to Search   ");
                pane.restore();
            }
            
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e)) 
                {
                    isDragging = true;
                    offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e)) 
                {
                    isDragging = false;
                }
            }

        });
        panel.addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                if (isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - offset.x;
                    int deltaY = currentMouse.y - offset.y;

                    setLocation(deltaX, deltaY);
                }
            }
        });

        return panel;
    }

    @Deprecated
    public JScrollPane createScrollPane()
    {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(1030, 720));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(1030, 720));
        JScrollPane pane = new JScrollPane(panel);
        pane.setSize(new Dimension(1030, 720));
        pane.setPreferredSize(new Dimension(1030, 720));
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        pane.setLocation(250, 0);
        return pane;
    }
    
    public JLabel createTitleList()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("Title of The Frame..");
        label.setForeground(Color.BLACK);
        
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 10, width, height);
        label.setVisible(true);
        return label;

    }
     
    public JTextField createSearch()
    {
        JTextField searchBar = new JTextField();
        
        searchBar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchBar.setBounds(300, 5, 400, 30);
        searchBar.setText("[/] to Search   ");
        searchBar.setEditable(true);
        searchBar.setFocusable(false);
        searchBar.setHorizontalAlignment(SwingConstants.RIGHT);

        searchBar.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }
        });
        
        searchBar.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                search.setEditable(true);
                search.setFocusable(true);
                searchBar.setText(""); // Set back 
                search.requestFocus();
            }
        });
        searchBar.setVisible(true);
            
        return searchBar;
    }
    
    public JLabel createCloseBank()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("closeBank");
        label.setForeground(Color.BLACK);
        
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(1030 - width - 30 - 100, 10, width, height);
        label.setVisible(true);
        return label;
    }
    
    public JLabel createCloseApp()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("X");
        label.setForeground(Color.RED);
        
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(1030 - width - 20, 10, width, height);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                dispose();
            }
        });
        return label;
    }


    
    public BankAccountListPane createList()
    {
        list = new BankAccountList();
        list.add(new BankAccount("Almond", "Bastiano", "Salas", 1234567890123456L));
        list.ba = list.sort(BankAccountList.Sort.LAST_NAME, null);
        return new BankAccountListPane(list);
    }
    
    public JLabel createTitle()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setLayout(null);
        label.setText("<html>" +
                       "Bank <br/> Account <br/> Center </html>");
        label.setForeground(Color.WHITE);
        
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        
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
        
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount b = new AddBankAccount().showDialog();
                if(b != null) // if it confirms
                {
                    list.add(b);
                    pane.requestAdd(b);
                }
            }
            
        });
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
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount b = new DepositDialog(list).showDialog(null);
                if(b != null) // if it confirms
                {
                    list.replace(b);
                    pane.replaceAccount(b);
                }
            }
            
        });

        
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
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount b = new WithdrawDialog(list).showDialog(null);
                if(b != null) // if it confirms
                {
                    list.replace(b);
                    pane.replaceAccount(b);
                }
            }
            
        });

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
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount b = new AddBankAccount().showDialog();
                if(b != null) // if it confirms
                {
                    list.replace(b);
                    pane.replaceAccount(b);
                }
            }
            
        });
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
        
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // will be used in the future :3
            }
        });
        return label;
    }
    
    public JPanel createInfo()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(250, 0, 1030, 40);
        panel.setBackground(Color.WHITE);
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                search.setFocusable(false);
                search.setText("[/] to Search   ");
                pane.restore();
            }
            
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e)) 
                {
                    isDragging = true;
                    offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e)) 
                {
                    isDragging = false;
                }
            }

        });
        panel.addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                if (isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - offset.x - 250;
                    int deltaY = currentMouse.y - offset.y;

                    setLocation(deltaX, deltaY);
                }
            }
        });
        return panel;
    }
    
}