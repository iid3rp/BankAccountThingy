import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import pp2.BankAccount.BankAccount;
import pp2.BankAccount.BankAccountList;
import pp2.BankAccount.BankAccountInterface;
import pp2.BankAccount.Dialogs.AddBankAccount;
import pp2.BankAccount.Dialogs.EditBankAccount;

public class InitialFrame extends JFrame
{
    public JTextField search;
    public BankAccountList list;
    public JLabel closeApplication;
    public boolean isDragging;
    public Point offset;
    public InitialFrame()
    {
        super();
        initializeComponent();        
        JPanel panel = createPanel();
        add(panel);
        setContentPane(panel);
        
        JPanel menu = createMenu();
        panel.add(menu);
        
        JPanel info = createInfo();
        panel.add(info);
        
        //JLabels for the header
        JLabel titleList = createTitleList();
        /*JTextField*/ search = createSearch();
        JLabel closeBank = createCloseBank();
        /*JLabel*/ closeApplication = createCloseApp();
        
        info.add(titleList);
        info.add(search);
        info.add(closeBank);
        info.add(closeApplication);
                
        /*BankAccountList*/ list = createList();
        JScrollPane pane = list.getPane();
        //JScrollPane pane = createScrollPane();
        pane.setLocation(250, 40);
        
        panel.add(pane);
        
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
        
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        new InitialFrame();
    }
    
    public void initializeComponent()
    {
        setVisible(false);
        setSize(new Dimension(1280, 720));
        setDefaultCloseOperation(3);
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
                list.restore();
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
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 10, width, height);
        label.setVisible(true);
        return label;

    }
     
    public JTextField createSearch()
    {
        JTextField searchBar = new JTextField();
        
        // Set font style to Segoe UI 15 plain
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
                    list.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else list.restore();
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    list.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else list.restore();
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    list.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else list.restore();
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
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
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
        label.setText("closeApp");
        label.setForeground(Color.RED);
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
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
    
    
    public BankAccountList createList()
    {
        BankAccount[] b =
        {
       
            new BankAccount("a", "MiddleName", "LastName", 234562356235L, 0),
            new BankAccount("birstName", "MiddleName", "LastName", 234562356235L, 0),
            new BankAccount("cirstName", "MiddleName", "LastName", 123455643355L, 0),
            new BankAccount("dirstName", "MiddleName", "LastName", 674853456345L, 0),
            new BankAccount("eirstName", "MiddleName", "LastName", 947537432498L, 0),
            new BankAccount("girstName", "MiddleName", "LastName", 734097398562L, 0),
            new BankAccount("hirstName", "MiddleName", "LastName", 20848728474L, 0),
            new BankAccount("iirstName", "MiddleName", "LastName", 130927348635L, 0),
            new BankAccount("jirstName", "MiddleName", "LastName", 698474724381L, 0),
            new BankAccount("kirstName", "MiddleName", "LastName", 243756398642L, 0),
            new BankAccount("lirstName", "MiddleName", "LastName", 834956298424L, 0),
            new BankAccount("a", "MiddleName", "LastName", 234562356235L, 0),
            new BankAccount("birstName", "MiddleName", "LastName", 234562356235L, 0),
            new BankAccount("cirstName", "MiddleName", "LastName", 123455643355L, 0),
            new BankAccount("dirstName", "MiddleName", "LastName", 674853456345L, 0),
            new BankAccount("eirstName", "MiddleName", "LastName", 947537432498L, 0),
            new BankAccount("girstName", "MiddleName", "LastName", 734097398562L, 0),
            new BankAccount("hirstName", "MiddleName", "LastName", 20848728474L, 0),
            new BankAccount("iirstName", "MiddleName", "LastName", 130927348635L, 0),
            new BankAccount("jirstName", "MiddleName", "LastName", 698474724381L, 0),
            new BankAccount("kirstName", "MiddleName", "LastName", 243756398642L, 0),
            new BankAccount("lirstName", "MiddleName", "LastName", 834956298424L, 0),
            new BankAccount("mirstName", "MiddleName", "LastName", 995084504030L, 0)
        };
               
        BankAccountList bl = new BankAccountList(b);
        return bl;
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
        
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                
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
                list.restore();
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
    
    @Deprecated
    public JLabel createTitlexeew() // reference of the new label
    {
        JLabel label = new JLabel();
        return label;
    }
    
}