package pp2.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import pp2.BankAccount.Utils.Intention;
import pp2.BankAccount.Dialogs.EditBankAccount;
import pp2.BankAccount.Dialogs.WithdrawDialog;
import pp2.BankAccount.Dialogs.DepositDialog;

/*
    SOME CHANGES HAVE BEEN MADE (2024, of March 31st, Around 22:20 i think)
    
    -> it will be extending within the BankAccount class itself,
       therefore it'll embed within the interface, and @that interface can be
       used to actually attached it as a BankAccount (polymorphism into play)
       class. This ensures proper transition within the class and avoiding finality
       conflicts. Hopefully :3
       
    - derp
*/
public class BankAccountInterface extends JPanel
{

    // @Intention will be in argument as of this timeline.
    // will remove this strip of comment if Intention is agreeable... :3
    @Intention(isPublic = true, 
               design = "showing the bank account's information and modify within the" + 
                        "extension of the BankAccount class.",
               reason = "it conflicts the finality of a certain bankaccount when" +
                        "overriding the memory referencing in outer classes")
    
    public static final int HEIGHT = 100;
    public static int WIDTH = 1030;
    public BankAccountList lib;
    public BankAccount b;
    public JLabel image;
    
    //
    private JLabel name;
    private JLabel number;
    private JLabel balance;
    
    // the labels are going to be private for certain reason/s:
    private JLabel edit;
    private JLabel delete;
    private JLabel deposit;
    private JLabel withdraw;
    //
    
    @Intention(isPublic = false, 
               design = "quite arguable, but might test..",
               reason = "conflicting issues with DepositDialog and WithdrawDialog" +
                        "with BankAccountList needed")
    public BankAccountList bl;
    
    public BankAccountInterface(BankAccount ba, BankAccountList list)
    {
        super();
        b = ba;
        lib = list;
        initializeComponent(); // for the panel itself.
        
        name = createName();
        number = createNumber();
        balance = createBalance();
        
        //interface stuff goes here
        edit = createEdit();
        delete = createDelete();
        deposit = createDeposit();
        withdraw = createWithdraw();
        
        image = createImage();
        
        add(image);
        add(name);
        add(number);
        add(balance);
        add(edit);
        add(delete);
        add(deposit);
        add(withdraw);
        
        repaint();
        validate();
        setVisible(true);
    }
    
    @Deprecated // !!! take note
    public BankAccountInterface(String first, String second, String last, long num)
    {
        initializeComponent(); // for the panel itself.
      
        b = new BankAccount(first, second, last, num);
        name = createName();
        number = createNumber();
        balance = createBalance();
        
        //interface stuff goes here
        edit = createEdit();
        delete = createDelete();
        deposit = createDeposit();
        withdraw = createWithdraw();
        
        image = createImage();
        
        add(image);
        add(name);
        add(number);
        add(balance);
        add(edit);
        add(delete);
        add(deposit);
        add(withdraw);
        
        repaint();
        validate();
        setVisible(true);
    }
    
    public void updateAccount(String first, String second, String last, long num)
    {
        // will be written here
    }
    
    public void initializeComponent()
    {
        setLayout(null);
        setBackground(Color.WHITE);
        setSize(WIDTH, HEIGHT);
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setBackground(new Color(200, 200, 200));
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                setBackground(Color.WHITE);
            }
        });
        
    }
    
    public JLabel createName()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText(b.getAccountName());
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(100, 8, 400, height);
        return label;
    }
    
    public JLabel createNumber()
    {
        JLabel label = createText("Account Number: " + b.getAccountNumber());

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(100, 40, 400, height);
        return label;
    }
    
    public JLabel createBalance()
    {
        JLabel label = createText("Balance: $" + b.getBalance());

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(100, 60, 400, height);
        return label;
    }
    
    public JLabel createText(String s)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText(s);
        label.setForeground(new Color(75, 75, 75));
        return label;
    } 
    
    public JLabel createImage()
    {
        JLabel label = new JLabel() 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(b.tryImage(b.getAccountNumber() + ""), 0, 0, getWidth(), getHeight(), null);
            }
        };
        label.setLayout(null);
        label.setBounds(15, 15, 70, 70);
        label.setVisible(true);
        return label;
    }
       
    public JLabel createEdit()
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
                edit.setForeground(Color.GRAY);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                edit.setForeground(Color.BLACK);
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount bank = new EditBankAccount().showDialog(b.getCopy());
                if(bank != null)
                {
                    b.referenceFromBank(bank);
                    update();
                }
            }
        });
        
        return label;
    }
    
    public JLabel createDeposit()
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
                BankAccount bank = new DepositDialog(lib).showDialog(b.getCopy());
                if(bank != null) // if it confirms
                {
                    b.referenceFromBank(bank);
                    update();
                }
            }
            
        });
        return label;
    }
    
    public JLabel createWithdraw()
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
                BankAccount bank = new WithdrawDialog(lib).showDialog(b.getCopy());
                if(bank != null) // if it confirms
                {
                    b.referenceFromBank(bank);
                    update();
                }
            }
            
        });
        return label;
    }
    
    public JLabel createDelete()
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
                b.setEmpty();
            }
            
        });

        return label;
    }
    
    public void update()
    {
        name.setText(b.getAccountName());
        number.setText("Account Number: " + b.getAccountNumber());
        balance.setText("Balance: $" + b.getBalance());
        
        // add images sooner ...
    }
    
    // quite unecessary but ok
    public JPanel getPanel()
    {
        return this;
    }
}