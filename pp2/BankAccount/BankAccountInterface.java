package BankAccountThingy.pp2.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import BankAccountThingy.pp2.BankAccount.Dialogs.DepositDialog;
import BankAccountThingy.pp2.BankAccount.Dialogs.EditBankAccount;
import BankAccountThingy.pp2.BankAccount.Dialogs.WithdrawDialog;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

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
    @Intention(design = "showing the bank account's information and modify within the" +
                        "extension of the BankAccount class.",
               reason = "it conflicts the finality of a certain bankAccount when" +
                        "overriding the memory referencing in outer classes")
    
    public static final int HEIGHT = 100;
    public static int WIDTH = 1030;
    public BankAccount b;
    public JLabel image;
    
    //
    private JLabel name;
    private JLabel number;
    private JLabel balance;
    
    public BankAccountInterface(BankAccount ba, BankAccountListPane pane)
    {
        super();
        b = ba;
        initializeComponent(); // for the panel itself.
        
        name = createName();
        number = createNumber();
        balance = createBalance();
        
        image = createImage();
        
        add(image);
        add(name);
        add(number);
        add(balance);
        JLabel deleteBankAccount = createDeleteBankAccount(ba, pane);
        JLabel editBankAccount = createEditBankAccount(ba, pane);
        JLabel depositMoney = createDeposit(ba, pane);
        JLabel withdrawMoney = createWithdraw(ba, pane);
        add(deleteBankAccount);
        add(editBankAccount);
        add(depositMoney);
        add(withdrawMoney);
        
        repaint();
        validate();
        setVisible(true);
    }

    private JLabel createDeposit(BankAccount ba, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("Deposit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(600, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount edit = new DepositDialog(pane.getBankList()).showDialog(ba);
                edit = edit == null? ba : edit;
                pane.requestEdit(edit);
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

    private JLabel createWithdraw(BankAccount ba, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("Withdraw");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(750, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount edit = new WithdrawDialog(pane.getBankList()).showDialog(ba);
                edit = edit == null? ba : edit;
                pane.requestEdit(edit);
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

    private JLabel createEditBankAccount(BankAccount ba, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("Edit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(500, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BankAccount edit = new EditBankAccount().showDialog(ba);
                edit = edit == null? ba : edit;
                pane.requestEdit(edit);
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

    public JLabel createDeleteBankAccount(BankAccount b, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setText("X");
        label.setForeground(Color.RED);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(950, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                 pane.requestRemove(b);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setFont(new Font("Segoe UI", Font.BOLD, 24));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            }
        });
        return label;
    }
    
    @Deprecated // !!! take note
    public BankAccountInterface(String first, String second, String last, long num)
    {
        initializeComponent(); // for the panel itself.

        b = new BankAccount(first, second, last, num);
        name = createName();
        number = createNumber();
        balance = createBalance();

        image = createImage();

        add(image);
        add(name);
        add(number);
        add(balance);

        repaint();
        validate();
        setVisible(true);
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
        int height = metrics.getHeight();
        label.setBounds(100, 8, 400, height);
        return label;
    }
    
    public JLabel createNumber()
    {
        JLabel label = createText("Account Number: " + b.getAccountNumber());

        FontMetrics metrics = getFontMetrics(label.getFont());
        int height = metrics.getHeight();
        label.setBounds(100, 40, 400, height);
        return label;
    }
    
    public JLabel createBalance()
    {
        JLabel label = createText("Balance: $" + b.getBalance());

        FontMetrics metrics = getFontMetrics(label.getFont());
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
                g.drawImage(b.tryImage(b.getAccountNumber() + "", 70), 0, 0, getWidth(), getHeight(), null);
            }
        };
        label.setLayout(null);
        label.setBounds(15, 15, 70, 70);
        label.setVisible(true);
        return label;
    }

    @Deprecated
    public void update()
    {
        name.setText(b.getAccountName());
        number.setText("Account Number: " + b.getAccountNumber());
        balance.setText("Balance: $" + b.getBalance());
        
        // add images sooner ...
    }

    @Deprecated
    // quite unnecessary but ok
    public JPanel getPanel()
    {
        return this;
    }
}