package BankAccountThingy.pp2.BankAccount;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.Utils.TextFieldFilter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountInformation extends JPanel
{
    private final JTextField interestField;
    private JLabel calculateWithdrawAmount;
    public BankAccount bankAccount;
    public JLabel picture;
    public JLabel name;
    public JLabel accountNumber;
    public JLabel balance;

    public JPanel modifyAccount;
    public JPanel withdrawPanel;
    public JPanel depositPanel;


    public JLabel back;
    public JLabel depositMoney;
    public JLabel withdrawMoney;
    public JLabel deleteAccount;

    public JTextField depositAmount;
    public JTextField withdrawAmount;

    public JCheckBox addInterest;
    public JLabel calculateDepositAmount;
    public double amount;
    public int interest;

    public AccountInformation(BankAccount b)
    {
        super();
        initializeComponent();
        bankAccount = b;
        picture = createPicture();
        name = createName();
        accountNumber = createAccountNumber();
        balance = createBalance();
        back = createBackLabel();

        modifyAccount = createModify();

        depositMoney = createDeposit();
        withdrawMoney = createWithdraw();
        deleteAccount = createDelete();

        withdrawPanel = createWithdrawPanel();
        depositPanel = createDepositPanel();

        withdrawAmount = createWithdrawAmount();
        depositAmount = createDepositAmount();

        addInterest = createCheckBox();

        add(picture);
        add(name);
        add(back);
        add(accountNumber);
        add(balance);
        add(modifyAccount);
        add(depositMoney);
        add(withdrawMoney);
        add(deleteAccount);

        // default
        modifyAccount.add(depositPanel);

        // the deposit sector
        JLabel addAmount  = createLabel("Enter amount: [$]", 20, 20, 20);
        calculateDepositAmount = createLabel("Total Amount: $0.00", 20, 150, 20);
        JLabel interestLabel  = createLabel("Interest Rate: [%]", 20, 70, 20);
        interestField = createInterestField();

        depositPanel.add(addAmount);
        depositPanel.add(calculateDepositAmount);
        depositPanel.add(depositAmount);
        depositPanel.add(addInterest);
        depositPanel.add(interestField);
        depositPanel.add(interestLabel);

        // the withdrawal panel addition
        JLabel decreaseAmount  = createLabel("Enter amount: [$]", 20, 20, 20);
        calculateWithdrawAmount = createLabel("Total Amount: $0.00", 20, 150, 20);

        withdrawPanel.add(decreaseAmount);
        withdrawPanel.add(calculateWithdrawAmount);
        withdrawPanel.add(withdrawAmount);

        setVisible(true);

    }

    private JLabel createLabel(String s, int x, int y, int fontSize)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        label.setText(s);
        label.setForeground(Color.black);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText() + "aaa");
        int height = metrics.getHeight();
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createInterestField()
    {
        JTextField j = new JTextField();
        j.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        j.setLocation(new Point(200, 70));
        j.setSize(new Dimension(50, 30));
        j.setText("15");
        PlainDocument doc = (PlainDocument) j.getDocument();
        doc.setDocumentFilter(new TextFieldFilter(TextFieldFilter.DataType.TYPE_NUMERICAL, 2));
        j.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(j.isEnabled())
                {
                    j.setCaretColor(Color.BLACK);
                    j.setText("");
                    j.setForeground(Color.BLACK); // Set back to default color
                }
            }
        });
        j.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                interest = Integer.parseInt(j.getText().isEmpty()? "0" : j.getText());
                putAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                interest = Integer.parseInt(j.getText().isEmpty()? "0" : j.getText());
                putAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                interest = Integer.parseInt(j.getText().isEmpty()? "0" : j.getText());
                putAmount();
            }
        });
        j.setVisible(true);
        return j;
    }

    private JTextField createDepositAmount()
    {
        JTextField j = new JTextField();
        j.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        j.setLocation(new Point(200, 20));
        j.setSize(new Dimension(200, 30));
        j.setText("Enter Amount: [$]");
        PlainDocument doc = (PlainDocument) j.getDocument();
        doc.setDocumentFilter(new TextFieldFilter(TextFieldFilter.DataType.TYPE_CURRENCY));
        j.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(j.isEnabled())
                {
                    j.setCaretColor(Color.BLACK);
                    j.setText("");
                    j.setForeground(Color.BLACK); // Set back to default color
                }
            }
        });
        j.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                putAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                putAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                putAmount();
            }
        });
        j.setVisible(true);
        return j;
    }

    private JTextField createWithdrawAmount()
    {
        JTextField j = new JTextField();
        j.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        j.setLocation(new Point(200, 20));
        j.setSize(new Dimension(200, 30));
        j.setText("Enter Amount: [$]");
        PlainDocument doc = (PlainDocument) j.getDocument();
        doc.setDocumentFilter(new TextFieldFilter(TextFieldFilter.DataType.TYPE_CURRENCY, 5));
        j.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(j.isEnabled())
                {
                    j.setCaretColor(Color.BLACK);
                    j.setText("");
                    j.setForeground(Color.BLACK); // Set back to default color
                }
            }
        });
        j.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                System.out.println("sdfsd");
                decreaseAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                decreaseAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                decreaseAmount();
            }
        });
        j.setVisible(true);
        return j;
    }

    private void decreaseAmount()
    {
        if((withdrawAmount.getText().equals("Enter Amount: [$]") || withdrawAmount.getText().isEmpty()))
        {
            calculateWithdrawAmount.setText("total amount: $0.00");
            amount = 0;
        }
        else
        {
            double moneyAmount = Double.parseDouble(withdrawAmount.getText());
            String formattedAmount = String.format("$%.2f", moneyAmount);
            calculateWithdrawAmount.setText("total Amount: " + formattedAmount);
        }
    }

    private JCheckBox createCheckBox()
    {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setLayout(null);
        checkBox.setFont(new Font("Segoe UI", Font.BOLD, 20));
        checkBox.setText("include");
        Dimension d = checkBox.getPreferredSize();
        checkBox.setBounds(270, 70, (int) d.getWidth() + 20, (int) d.getHeight());
        checkBox.addItemListener(e -> putAmount());
        return checkBox;
    }

    private void putAmount()
    {
        if((depositAmount.getText().equals("Enter Amount: [$]") || depositAmount.getText().isEmpty()))
        {
            calculateDepositAmount.setText("$0.00");
            amount = 0;
        }
        else
        {
            double moneyAmount = Double.parseDouble(depositAmount.getText());
            amount = moneyAmount * (addInterest.isSelected() ? (1 + ((double) interest / 100)) : 1);
            // Format the total amount like currency
            String formattedAmount = String.format("$%.2f", amount);
            calculateDepositAmount.setText("total Amount: " + formattedAmount);
        }
    }

    private JPanel createDepositPanel()
    {
        JPanel p = new JPanel();
        p.setSize(new Dimension((int) InitialFrame.dimension.getWidth() - 210, (int) InitialFrame.dimension.getHeight() - 240));
        // p.setBackground(new Color(121, 13, 13)); // debuggers
        p.setLocation(new Point(0,0 ));
        p.setLayout(null);
        return p;
    }

    private JPanel createWithdrawPanel()
    {
        JPanel p = new JPanel();
        p.setSize(new Dimension((int) InitialFrame.dimension.getWidth() - 210, (int) InitialFrame.dimension.getHeight() - 240));
        // p.setBackground(new Color(206, 183, 183)); // debuggers
        p.setLocation(new Point(0, 0));
        p.setLayout(null);
        return p;
    }

    private JLabel createDelete()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("Delete");
        label.setForeground(Color.red);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText() + "  ");
        int height = metrics.getHeight();
        label.setBounds(20, (int) InitialFrame.dimension.getHeight() - 60, width, height);
        return label;
    }

    private JLabel createWithdraw()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        label.setText("Withdraw");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText() + "  ");
        int height = metrics.getHeight();
        label.setBounds(20, 350, width, height);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                label.setFont(new Font("Segoe UI", Font.BOLD, 30));
                depositMoney.setFont(new Font("Segoe UI", Font.PLAIN, 30));
                modifyAccount.removeAll();
                modifyAccount.add(withdrawPanel);
                modifyAccount.repaint();
            }
        });
        return label;
    }

    private JLabel createDeposit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("Deposit");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText() + "  ");
        int height = metrics.getHeight();
        label.setBounds(20, 270, width, height);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                label.setFont(new Font("Segoe UI", Font.BOLD, 30));
                withdrawMoney.setFont(new Font("Segoe UI", Font.PLAIN, 30));
                modifyAccount.removeAll();
                modifyAccount.add(depositPanel);
                modifyAccount.repaint();
            }
        });
        return label;
    }

    private JPanel createModify()
    {
        JPanel p = new JPanel();
        p.setSize(new Dimension((int) InitialFrame.dimension.getWidth() - 210, (int) InitialFrame.dimension.getHeight() - 240));
        p.setBackground(new Color(0, 0, 0));
        p.setLocation(new Point(210, 240));
        p.setLayout(null);
        return p;
    }

    private JLabel createBackLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("<");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText() + "  ");
        int height = metrics.getHeight();
        label.setBounds(20, 10, width, height);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });
        return label;
    }

    private JLabel createBalance()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setText("<html><b> Balance:</b> $" + bankAccount.getBalance());
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int height = metrics.getHeight();
        label.setBounds(210, 160, 400, height);
        return label;
    }

    private JLabel createAccountNumber()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setText("<html><b>Account Number: </b>" + bankAccount.getAccountNumber());
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int height = metrics.getHeight();
        label.setBounds(210, 130, 400, height);
        return label;
    }

    private JLabel createName()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 42));
        label.setText(bankAccount.getAccountName());
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText() + "aaa");
        int height = metrics.getHeight();
        label.setBounds(210, 60, width, height);
        return label;
    }

    private JLabel createPicture()
    {
        final int length = 150;
        JLabel label = new JLabel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(bankAccount.tryImage(bankAccount.getAccountNumber() + "", length), 0, 0, null);
            }
        };
        label.setLayout(null);
        label.setBounds(20, 60, length, length);
        label.setVisible(true);
        return label;
    }

    private void initializeComponent()
    {
        setLayout(null);
        setSize(new Dimension(InitialFrame.dimension));
        setDoubleBuffered(true);
        setLocation(0, 0);
    }

    public static void main(String[] a)
    {
        JFrame x = new JFrame();
        x.setSize(new Dimension(InitialFrame.dimension));
        x.setUndecorated(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AccountInformation b = new AccountInformation(new BankAccount("FirstName", "MiddleName", "LastName", 2323_1234_7632_3429L));
        x.add(b);
        x.setVisible(true);
    }
}
