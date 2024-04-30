package BankAccountThingy;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import BankAccountThingy.pp2.BankAccount.BankAccount2;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;
import BankAccountThingy.pp2.BankAccount.Dialogs.AddBank;
import BankAccountThingy.pp2.BankAccount.Dialogs.AddBankAccount;
import BankAccountThingy.pp2.BankAccount.Dialogs.WithdrawDialog;
import BankAccountThingy.pp2.BankAccount.Dialogs.DepositDialog;
import BankAccountThingy.pp2.BankAccount.StreamIO.FileLogger;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankChooser;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankMaker;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankReader;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
import BankAccountThingy.pp2.BankAccount.Utils.Log;
import BankAccountThingy.pp2.BankAccount.Utils.Region;

public final class InitialFrame extends JFrame
{
    public static Dimension dimension = new Dimension(1280, 720);
    public FileLogger logger = new FileLogger();
    private final JLabel addBank;
    InitialFrame frame = this;
    public JLabel withdraw;
    public JLabel deposit;
    public JLabel interest;
    public JLabel changeBank;
    public JLabel title;
    public JPanel panel;
    public JPanel menu;
    public JLabel addAccount;

    @Intention(design = "reference point panel")
    public BankAccountPane contentPanel;

    @Intention(design = "panel with bank account list displayed")
    public BankAccountPane pane;

    public boolean isDragging;
    public Point offset;
    private File referenceFile;

    private JLabel closeApplication;

    public InitialFrame()
    {
        super();
        initializeComponent();
        panel = createPanel();
        menu = createMenu();
        add(panel);
        panel.add(menu);

        closeApplication = createCloseApplication();
        contentPanel = createContentPanel();
        contentPanel.setLocation(200, 0);
        panel.add(contentPanel);

        title = createTitle();
        addAccount = createAddAccount();
        deposit = createDepositMoney();
        withdraw = createWithdrawMoney();
        interest = createInterestRate();
        changeBank = createOpenBank();
        addBank = createAddBank();

        menu.add(title);
        menu.add(changeBank);
        menu.add(addBank);

        panel.validate();
        panel.repaint();

        setVisible(true);
        @Intention var x = new File(BankMaker.pictures).mkdirs(); // creates a library of the pictures :3
        logger.add(Log.OPEN_APPLICATION, null, null);
    }

    private JPanel createMenu()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.black);
        panel.setBounds(0, 0, 200, 720);
        panel.addMouseListener(new MouseAdapter()
        {

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

    public BankAccountPane createContentPanel()
    {
        BankAccountPane panel = new BankAccountPane();
        panel.setLayout(null);
        panel.setSize(new Dimension(1080, 720));
        panel.addMouseListener(new MouseAdapter()
        {
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

                    int deltaX = currentMouse.x - offset.x - 200;
                    int deltaY = currentMouse.y - offset.y;

                    setLocation(deltaX, deltaY);
                }
            }
        });
        panel.add(closeApplication);
        panel.add(createWelcome());
        panel.add(createSecondWelcome());
        panel.add(createNewBankMain());
        panel.add(createOpenBankMain());
        return panel;
    }

    public void createBankList()
    {
        // adds the functionalities afterward
        menu.add(addAccount);
        menu.add(deposit);
        menu.add(withdraw);
        menu.add(interest);

        contentPanel.removeAll();
        contentPanel.add(pane);
        contentPanel.repaint();
        contentPanel.validate();
        repaint();
        validate();
    }

    public File getReferenceFile()
    {
        return referenceFile;
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
        setSize(dimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(confirmClose())
                {
                    try
                    {
                        if(referenceFile != null)
                        {
                            BankMaker.rewriteFile(referenceFile, pane.pane.ba);
                            logger.add(Log.CLOSE_BANK, pane.pane.ba, null);
                        }
                        // System.out.println("log: bank closed"); // debuggers
                        logger.add(Log.CLOSE_APPLICATION, null, null);
                        logger.close();
                    }
                    catch(IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    e.getWindow().dispose();
                }
            }
        });
    }

    public boolean confirmClose() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to close?",
                "Closing Application",
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
        label.setText("+ Add");
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
                label.setFont(new Font("Segoe UI", Font.BOLD, 20));
                label.setBounds(30 - (int)((label.getPreferredSize().getWidth() - d.getWidth()) / 2), 220, (int) d.getWidth() + 30, (int) d.getHeight());
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                label.setBounds(30, 220, (int) d.getWidth() + 30, (int) d.getHeight());
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(pane != null) {
                    BankAccount2 b = new AddBankAccount(frame).showDialog();
                    if(b != null)
                    {
                        pane.pane.requestAdd(b);
                        logger.add(Log.ADD_ACCOUNT, pane.pane.ba, b);
                    }
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
        label.setText("< Deposit");
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
                label.setFont(new Font("Segoe UI", Font.BOLD, 20));
                label.setBounds(30 - (int)((label.getPreferredSize().getWidth() - d.getWidth()) / 2), 260, (int) d.getWidth() + 30, (int) d.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                label.setBounds(30, 260, (int) d.getWidth() + 30, (int) d.getHeight());
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(pane != null) {
                    BankAccount2 b = new DepositDialog(frame, pane.pane.getBankList()).showDialog(null);
                    double amount = b.getBalance();
                    b = pane.pane.replaceAccount(b);
                    amount -= b.getBalance();
                    logger.add(Log.DEPOSIT, pane.pane.ba, b, amount);
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
        label.setText("> Withdraw");
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
                label.setFont(new Font("Segoe UI", Font.BOLD, 20));
                label.setBounds(30 - (int)((label.getPreferredSize().getWidth() - d.getWidth()) / 2), 300, (int) d.getWidth() + 30, (int) d.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                label.setBounds(30, 300, (int) d.getWidth() + 30, (int) d.getHeight());
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(pane != null)
                {
                    System.out.println("hello");
                    BankAccount2 b = new WithdrawDialog(frame, pane.pane.ba).showDialog(null);
                    if(b != null)
                    {
                        double amount = b.getBalance();
                        b = pane.pane.replaceAccount(b);
                        amount -= b.getBalance();
                        logger.add(Log.WITHDRAW, pane.pane.ba, b, amount);
                    }
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
        label.setText("Interest: " + ((int) (BankAccount2.getInterestRate() * 100)) + "%");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 160 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                boolean confirm = false;
                while(!confirm)
                {
                    try
                    {
                        String reference = JOptionPane.showInputDialog(frame,"Put Interest Rate [%]");
                        int interest = Integer.parseInt(reference);
                        BankAccount2.setInterestRate((double) interest / 100d);
                        label.setText("Interest: " + ((int) (BankAccount2.getInterestRate() * 100)) + "%");
                        confirm = true;
                    }
                    catch(NumberFormatException ex)
                    {
                        confirm = true;
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 20));
                label.setBounds(30 - (int)((label.getPreferredSize().getWidth() - d.getWidth()) / 2), 720 - 160 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                label.setBounds(30, 720 - 160 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
            }
        });
        return label;
    }

    public JLabel createCloseApplication()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("X");
        label.setForeground(Color.RED);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(1080 - width - 20, 10, width, height);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(frame.confirmClose())
                {
                    try
                    {
                        if(referenceFile != null)
                        {
                            BankMaker.rewriteFile(frame.getReferenceFile(), pane.pane.ba);
                            frame.logger.add(Log.CLOSE_BANK, pane.pane.ba, null);
                        }
                        frame.logger.add(Log.CLOSE_APPLICATION, null, null);
                        frame.logger.close();
                    }
                    catch(IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.dispose();
                }
            }
        });
        return label;
    }

    public JLabel createAddBank()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("+ New Bank");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 120 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                creatingBank();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 20));
                label.setBounds(30 - (int)((label.getPreferredSize().getWidth() - d.getWidth()) / 2), 720 - 120 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                label.setBounds(30, 720 - 120 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
            }
        });
        return label;
    }
    
    public JLabel createOpenBank()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setLayout(null);
        label.setText("o Open");
        label.setForeground(Color.WHITE);
        Dimension d = label.getPreferredSize();
        label.setBounds(30, 720 - 80 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
        label.setVisible(true);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                openingBank();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 20));
                label.setBounds(30 - (int)((label.getPreferredSize().getWidth() - d.getWidth()) / 2), 720 - 80 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                label.setBounds(30, 720 - 80 - (int) d.getHeight(), (int) d.getWidth() + 30, (int) d.getHeight());
            }
        });
        return label;
    }

    /**
     * 2 @Intention voids in action with 2 different purposes: one that returns BankAccountPane from creating a new Bank,
     * and the other one from a .csv file. They return the same data type but different options...
     */
    private @Intention void createPane(InitialFrame frame, BankMaker b)
    {
        referenceFile = new File(b.getFileName());
        pane = b.createBankAccountList(this, referenceFile);
        if(pane != null)
        {
            createBankList(); // this will automatically create a bank list for u
        }
    }

    /**
     * 2 @Intention voids in action with 2 different purposes: one that returns BankAccountPane from creating a new Bank,
     * and the other one from a .csv file. They return the same data type but different options...
     */
    private @Intention void createPane(InitialFrame frame, File file)
    {
        pane = new BankReader().createListFromBank(this, file);
        if(pane != null)
        {
            @Intention(design = "this is where the serialization and security comes with the bank serial thingies")
            var bankValidation = new File(BankMaker.pictures + pane.pane.ba.getSerial()).exists();
            if(bankValidation)
            {
                createBankList(); // this will automatically create a bank list for u
            }
            else JOptionPane.showMessageDialog(frame, "Bank List file invalid.");
        }
    }

    public JLabel createWelcome()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("<html>Welcome to" +
                "<br>Bank Account Center!<br/><html/>");
        label.setFont(new Font("Segoe UI", Font.BOLD, 45));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight() * 2;
        label.setBounds((getWidth() / 2) - (width / 2) + 100, 100, width, height);
        label.setVisible(true);
        return label;
    }

    public JLabel createSecondWelcome()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("You can get started by either:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight() * 2;
        label.setBounds(100, 430, width, height);
        label.setVisible(true);
        return label;
    }

    public JLabel createNewBankMain()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("Create new bank");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(100, 510, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                creatingBank();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    private void creatingBank()
    {
        BankMaker b = new AddBank().showDialog();
        if(b != null)
        {
            createPane(frame, b);
            logger.add(Log.ADD_BANK, pane.pane.ba, null);
        }
    }

    public JLabel createOpenBankMain()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("Opening existing bank");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(100, 475, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                openingBank();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    private void openingBank()
    {
        File b = BankChooser.showDialog(frame);
        if(b != null)
        {
            if(referenceFile != null)
            {
                logger.add(Log.CLOSE_BANK, pane.pane.ba, null);
            }
            createPane(frame, b);
            referenceFile = b;
            logger.add(Log.OPEN_BANK, pane.pane.ba, null);
        }
    }

    public void setReferenceFile(File file)
    {
        referenceFile = file;
    }
}