package pp2.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import pp2.BankAccount.Utils.Intention;
public class BankAccountInterface extends JPanel
{
    public static final int HEIGHT = 100;
    public static int WIDTH = 1030;
    public BankAccount b;
    public JLabel image;
    
    // the labels are going to be public for the reason to
    // make the functionality of the BankAccount EMBEDDED into
    // the interface work and doing it as expected...
    //
    // it's like the analogy of a public method to function stuff
    // but OOP based yk.. :3
    // - derp
    @Intention(isPublic = true, reason = "functionality")
    public JLabel edit;
    
    @Intention(isPublic = true, reason = "functionality")
    public JLabel delete;
    
    @Intention(isPublic = true, reason = "functionality")
    public JLabel deposit;
    
    @Intention(isPublic = true, reason = "functionality")
    public JLabel withdraw;
    //
    //
    //
    
    public BankAccountInterface(BankAccount ba)
    {
        super();
        initializeComponent(); // for the panel itself.
      
        b = new BankAccount(ba);
        JLabel name = createName();
        JLabel number = createNumber();
        JLabel amount = createAmount();
        
        //interface stuff goes here
        edit = createEdit();
        delete = createDelete();
        deposit = createDeposit();
        withdraw = createWithdraw();
        
        JLabel image = createImage();
        
        add(image);
        add(name);
        add(number);
        add(amount);
        add(edit);
        add(delete);
        add(deposit);
        add(withdraw);
        repaint();
        validate();
        setVisible(true);
    }
    
    public BankAccountInterface(String first, String second, String last, long num)
    {
        super();
        initializeComponent(); // for the panel itself.
      
        b = new BankAccount(first, second, last, num);
        JLabel name = createName();
        JLabel number = createNumber();
        JLabel amount = createAmount();
        
        //interface stuff goes here
        JLabel edit = createEdit();
        JLabel delete = createDelete();
        JLabel deposit = createDeposit();
        JLabel withdraw = createWithdraw();
        
        image = createImage();
        
        add(image);
        add(name);
        add(number);
        add(amount);
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
    
    public JLabel createAmount()
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
        label.setBounds(500, (getHeight() / 2) - (height /2), 400, height);
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
        label.setBounds(570, (getHeight() / 2)  - (height /2), 400, height);
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
        label.setBounds(700, (getHeight() / 2)  - (height /2), 400, height);
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
        label.setBounds(850, (getHeight() / 2)  - (height /2), 400, height);
        return label;
    }
}