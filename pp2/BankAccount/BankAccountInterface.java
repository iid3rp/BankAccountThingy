package BankAccountThingy.pp2.BankAccount;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.Dialogs.DepositDialog;
import BankAccountThingy.pp2.BankAccount.Dialogs.EditBankAccount;
import BankAccountThingy.pp2.BankAccount.Dialogs.WithdrawDialog;
import BankAccountThingy.pp2.BankAccount.StreamIO.ImageMaker;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
import BankAccountThingy.pp2.BankAccount.Utils.Log;

public class BankAccountInterface extends JPanel
{
    private BankAccountListPane pane;
    JPanel panel = this;
    @Intention InitialFrame frame;
    public static final int HEIGHT = 100;
    public static int WIDTH = 1080;
    public BankAccount2 b;
    public JLabel image;
    
    //
    private JLabel name;
    private JLabel number;
    private JLabel balance;
    
    public BankAccountInterface(InitialFrame frame, BankAccount2 ba, BankAccountListPane pane)
    {
        super();
        this.frame = frame;
        this.pane = pane;
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

    private JLabel createDeposit(BankAccount2 ba, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setText("Deposit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(650, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(pane != null)
                {
                    double currentBalance = ba.getBalance();
                    BankAccount2 edit = new DepositDialog(frame, pane.getBankList()).showDialog(ba);
                    if(edit != null)
                    {
                        currentBalance -= edit.getBalance();
                        pane.requestDeposit(edit);
                        frame.logger.add(Log.DEPOSIT, pane.ba, edit, currentBalance);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.blue);
                panel.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    private JLabel createWithdraw(BankAccount2 ba, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setText("Withdraw");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(800, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                double currentBalance = ba.getBalance();
                BankAccount2 edit = new WithdrawDialog(frame, pane.getBankList()).showDialog(ba);
                if(edit != null)
                {
                    currentBalance -= edit.getBalance();
                    pane.requestWithdraw(edit);
                    frame.logger.add(Log.WITHDRAW, pane.ba, edit, currentBalance);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.blue);
                panel.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    private JLabel createEditBankAccount(BankAccount2 ba, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setText("Edit");
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
                BankAccount2 edit = new EditBankAccount(frame, pane.ba).showDialog(ba);
                if(edit != null)
                {
                    pane.requestEdit(edit);
                    frame.logger.add(Log.EDIT_ACCOUNT, pane.ba, edit);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.blue);
                panel.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    public JLabel createDeleteBankAccount(BankAccount2 b, BankAccountListPane pane)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setText("Delete");
        label.setForeground(Color.black);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(950, (BankAccountInterface.HEIGHT /2) - (height / 2), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete this " +
                                "bank?",
                        "Deleting bank account",
                        JOptionPane.YES_NO_OPTION
                );
                if(result == JOptionPane.YES_OPTION)
                {
                    pane.requestRemove(b);
                    frame.logger.add(Log.DELETE_ACCOUNT, pane.ba, null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.RED);
                panel.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }
    
    @Deprecated // !!! take note
    public BankAccountInterface(String first, String second, String last, long num)
    {
        initializeComponent(); // for the panel itself.

        b = new BankAccount2(first, second, last, num);
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
        setBorder(new LineBorder(new Color(200, 200, 200), 1));
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                setBackground(Color.white);
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
                g.drawImage(ImageMaker.parseImage(b, pane.ba, 70), 0, 0, getWidth(), getHeight(), null);
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