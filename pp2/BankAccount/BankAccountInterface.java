package pp2.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankAccountInterface extends JPanel
{
    public BankAccount b;
    
    public BankAccountInterface(BankAccount ba)
    {
        super();
        initializeComponent(); // for the panel itself.
      
        b  = ba;
        JLabel name = createName();
        JLabel number = createNumber();
        JLabel amount = createAmount();
        
        //interface stuff goes here
        JLabel edit = createEdit();
        JLabel delete = createDelete();
        JLabel deposit = createDeposit();
        JLabel withdraw = createWithdraw();
        
        add(name);
        add(number);
        add(amount);
        add(edit);
        add(delete);
        add(deposit);
        add(withdraw);
        setVisible(true);
        repaint();
        validate();
    }

    
    public BankAccountInterface(String first, String second, String last, long num)
    {
        super();
        initializeComponent(); // for the panel itself.
      
        b  = new BankAccount(first, second, last, num);
        JLabel name = createName();
        JLabel number = createNumber();
        JLabel amount = createAmount();
        
        //interface stuff goes here
        JLabel edit = createEdit();
        JLabel delete = createDelete();
        JLabel deposit = createDeposit();
        JLabel withdraw = createWithdraw();
        
        add(name);
        add(number);
        add(amount);
        add(edit);
        add(delete);
        add(deposit);
        add(withdraw);
        setVisible(true);
        repaint();
        validate();
    }
    
    public void updateAccount(String first, String second, String last, long num)
    {
        
    }
    
    public void initializeComponent()
    {
        setLayout(null);
        setBackground(Color.WHITE);
        setSize(1030, 115);
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setBackground(new Color(220, 220, 220));
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
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText(b.getAccountName());
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 10, 400, height);
        return label;
    }
    
    public JLabel createNumber()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setText("Account Number: " + b.getAccountNumber());
        label.setForeground(Color.GRAY);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 45, 400, height);
        return label;
    }
    
    public JLabel createAmount()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setText("Balance: $" + b.getBalance());
        label.setForeground(Color.GRAY);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 70, 400, height);
        return label;
    }
    
    public JLabel createEdit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("edit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(500, (getHeight() / 2) - (height /2), 400, height);
        return label;
    }
    
    public JLabel createDeposit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("deposit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(570, (getHeight() / 2)  - (height /2), 400, height);
        return label;
    }
    
    public JLabel createWithdraw()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("withdraw");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(700, (getHeight() / 2)  - (height /2), 400, height);
        return label;
    }
    
    public JLabel createDelete()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("delete");
        label.setForeground(Color.RED);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(850, (getHeight() / 2)  - (height /2), 400, height);
        return label;
    }
}