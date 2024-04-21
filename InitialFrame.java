package BankAccountThingy;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;

import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;
import BankAccountThingy.pp2.BankAccount.Dialogs.AddBankAccount;
import BankAccountThingy.pp2.BankAccount.Dialogs.WithdrawDialog;
import BankAccountThingy.pp2.BankAccount.Dialogs.DepositDialog;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankChooser;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankMaker;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankReader;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
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
    public JPanel panel;
    public JPanel menu;
    public JLabel addAccount;
    private JPanel contentPanel;

    public JTextField search;
    BankAccountPane pane;
    public boolean isDragging;
    public Point offset;
    private File referenceFile;

    public InitialFrame()
    {
        super();
        initializeComponent();
        panel = createPanel();
        menu = createMenu();
        add(panel);
        panel.add(menu);

        contentPanel = createContentPanel();
        contentPanel.setLocation(250, 0);
        panel.add(contentPanel);

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

    private JPanel createMenu()
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
                pane.pane.restore();
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


    private JPanel createContentPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(new Dimension(1030, 720));
        panel.setLocation(new Point(250, 0));
        return panel;
    }

    public void createBankList()
    {
        contentPanel.removeAll();
        contentPanel.add(pane);
        contentPanel.repaint();
        contentPanel.validate();
        repaint();
        validate();
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
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(confirmClose())
                {
                    BankMaker.rewriteFile(referenceFile, pane.pane.ba);
                    e.getWindow().dispose();
                }
            }
        });

    }

    private boolean confirmClose() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to close?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }

    public JPanel createPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setSize(new Dimension(1030, 720));
        panel.setLayout(null);
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
                    pane.pane.requestAdd(b);
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
                BankAccount b = new DepositDialog(pane.pane.getBankList()).showDialog(null);
                if(b != null) // if it confirms
                {
                    pane.pane.replaceAccount(b);
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
                BankAccount b = new WithdrawDialog(pane.pane.getBankList()).showDialog(null);
                if(b != null) // if it confirms
                {
                    pane.pane.replaceAccount(b);
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
                    pane.pane.replaceAccount(b);
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
        label.setText("Open Bank");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 80 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                /*BankMaker b = new AddBank().showDialog();
                if(b != null)
                {
                    createPane(b);
                }*/
                File b = BankChooser.showDialog();
                if(b != null)
                {
                    createPane(b);
                    referenceFile = b;
                }
            }

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
        });
        return label;
    }

    /**
     * 2 @Intention voids in action with 2 different purposes: one that returns BankAccountPane from creating a new Bank,
     * and the other one from a .csv file. They return the same data type but different options...
     */
    private @Intention void createPane(BankMaker b)
    {
        pane = b.createBankAccountList(this, new File(b.fileTitle));
        createBankList(); // this will automatically create a bank list for u
    }

    private @Intention void createPane(File file)
    {
        pane = new BankReader().createListFromBank(this, file);
        createBankList(); // this will automatically create a bank list for u
    }
}