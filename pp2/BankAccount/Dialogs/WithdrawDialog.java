package BankAccountThingy.pp2.BankAccount.Dialogs;
import java.text.NumberFormat;
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
import javax.swing.JCheckBox;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountListPane;
import BankAccountThingy.pp2.BankAccount.BankAccountInterface;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankMaker;
import BankAccountThingy.pp2.BankAccount.Utils.TextFieldFilter;
public class WithdrawDialog extends JDialog
{
    private BankAccount allocation; // this will be reference of the bankaccount to be withdrawn :#
    private boolean result;
    
    public JPanel imageEditor;
    public JPanel panel;
     
    public JTextField accountNumber;
    public JTextField moneyHandler;
    public JLabel bankIdentifier;
    public JLabel checkBalance;
    public JLabel amount;
    public JLabel ok;
    
    public Point offset;
    public boolean isDragging;
    public BankAccountList lib;
    
    private boolean confirm = false;
    
    public Random rand = new Random();
    public double totalAmount;
    public WithdrawDialog(BankAccountList b)
    {
        super();
        lib = b; // this creates a reference of the list of the BankAccounts when depositing/withdrawing..
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL); // this ensures modallity of the jdialog    
        setSize(new Dimension(500, 350));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        panel = createPanel();
        add(panel);
        
        moneyHandler = createMoney();
        accountNumber = createAccountNumber();
        bankIdentifier = createBankIdentifier();
        amount = createAmount();
        checkBalance = createCheckBalance();
        
        ok = createOk();
        panel.add(createCancel(10, "X"));
        panel.add(createCancel(getHeight() - 50, "Cancel"));
        panel.add(createTitle(20, 20, 10, "Withdraw Money"));
        
        panel.add(createText("<html><b>Account Number", 50));
        panel.add(createText("<html><b>Amount [$]", 135));
        panel.add(createText("<html><b>Total Amount:", 235));
        panel.add(bankIdentifier);
        panel.add(checkBalance);
        panel.add(moneyHandler);
        panel.add(amount);
        
        panel.add(accountNumber);
        panel.add(ok);
        
    }
    
    // depositing method (double for certain reasons)
    public BankAccount showDialog(BankAccount b) 
    {
        if(b != null)
        {
            allocation = new BankAccount(b); // deep copying ig
            // modify if the bank is not null lol
            accountNumber.setText(String.valueOf(b.getAccountNumber()));
            accountNumber.setEnabled(false);
        }
        setVisible(true);
        if(b == null) // however
        {
            try
            {
                String s = accountNumber.getText().isBlank()? "0" :
                           accountNumber.getText();
                b = lib.searchByNumber(Long.parseLong(s));
            }
            catch(NumberFormatException e)
            {
                b = lib.searchByNumber(0L);
            }
        }
        if(confirm)
        {
            b.withdraw(totalAmount);
            System.out.println(b.getBalance());
        }
        return confirm? b : null;
    }
   
    
    private JPanel createPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(new Dimension(400, 400));
        panel.setVisible(true);
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
    
    private JLabel createTitle(int fontSize, int x, int y, String s)
    {
        JLabel label = new JLabel(s);
        label.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        label.setLayout(null);
        Dimension d = label.getPreferredSize();
        label.setBounds(x, y, (int) d.getWidth(), (int) d.getHeight());
        label.setBackground(Color.GRAY);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                confirm = true;
                dispose();
            }
        });
        return label;
    }
    
    private JLabel createOk()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("Withdraw Money");
        label.setEnabled(false);
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        label.setBounds(getWidth() - (int) width - 20 - 90, getHeight() - 50, width, height);

        label.setBackground(Color.BLACK);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                confirm = true;
                dispose();
            }
        });
        return label;
    }
    
    private JLabel createCancel(int y, String s)
    {
        JLabel label = new JLabel(s);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setForeground(Color.RED);
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        label.setBounds(getWidth() - (int) width - 20, y, width, height);
        label.setBackground(Color.RED);
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
       
    private JTextField createAccountNumber() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(20, 75, 400, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("Enter Bank Account Number:");
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new TextFieldFilter(TextFieldFilter.DataType.TYPE_NUMERICAL));
        
        textField.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if(accountNumber.isEnabled())
                {
                    textField.setCaretColor(Color.BLACK);
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Set back to default color
                }
            }
        });
        
        textField.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                searchQuery();
                consistentLabelling();
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                searchQuery();
                consistentLabelling();
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                searchQuery();
                consistentLabelling();
            }
        });
        return textField;
    }
    
    private void searchQuery()
    {
        long num = 0L;
        try
        {
            num = Long.parseLong(accountNumber.getText());
        }
        catch(NumberFormatException e)
        {
            num = 0;
        }
        finally
        {
            BankAccount b = lib.searchByNumber(num);
            if(b == null)
            {
                bankIdentifier.setText("No Bank Account was found.");
                checkBalance.setText("<html>The current balance will be shown <b>here.</b></html>");
                moneyHandler.setEnabled(false);
            }
            else 
            {
                bankIdentifier.setText("<html> Bank Account identified as <b>'" + b.getAccountName() + "'</b>");
                checkBalance.setText("<html>Your current balance is $<b>" + b.getBalance() +"</b></html>");
                allocation = new BankAccount(b); // deep copying
                moneyHandler.setEnabled(true);
            }
        }
    }
    
    private JTextField createMoney() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(20, 160, 400, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("Enter Amount: [$]");
        textField.setEnabled(false);
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new TextFieldFilter(TextFieldFilter.DataType.TYPE_CURRENCY));

        
        textField.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if(moneyHandler.isEnabled())
                {
                    textField.setCaretColor(Color.BLACK);
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Set back to default color
                }
            }
        });
        
        textField.getDocument().addDocumentListener(new DocumentListener() 
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
                // Not needed for plain text components
            }
         
        });
        return textField;
    }
    
    public void putAmount()
    {
        if((moneyHandler.getText().equals("Enter Amount: [$]") || moneyHandler.getText().equals("")))
        {
            amount.setText("$0.00");
            totalAmount = 0;
            if(allocation != null)
            {
                ok.setEnabled(totalAmount <= allocation.getBalance());
            }
        }
        else
        {
            totalAmount = Double.parseDouble(moneyHandler.getText());
            // Format the total amount like currency
            String formattedAmount = String.format("$%.2f", totalAmount);
            amount.setText(formattedAmount);
        }
        if(allocation != null)
        {
            ok.setEnabled(totalAmount <= allocation.getBalance());
            amount.setForeground(totalAmount > allocation.getBalance()? Color.RED : Color.BLACK);
        }
    }
    
    
    private JLabel createBankIdentifier()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText("<html>The Bank Acoount will be indentified <b>here.</b></html>");
        Dimension d = label.getPreferredSize();
        label.setBounds(20, 100, (int) d.getWidth() +30, (int)d.getHeight());
        label.setVisible(true);

        return label;
    }
    
    private JLabel createCheckBalance()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText("<html>The current balance will be shown <b>here.</b></html>");
        Dimension d = label.getPreferredSize();
        label.setBounds(20, 185, (int) d.getWidth() +30, (int)d.getHeight());
        label.setVisible(true);

        return label;
    }
    
    private void consistentLabelling()
    {
        Dimension d = bankIdentifier.getPreferredSize();
        bankIdentifier.setBounds(20, 100, (int) d.getWidth(), (int) d.getHeight());
    }
    
    private JLabel createText(String s, int y)
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText(s);
        Dimension d = label.getPreferredSize();
        label.setBounds(20, y, 400, (int) d.getHeight());
        label.setVisible(true);

        return label;
    }
    
    private JLabel createAmount()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        String str  = "";
        label.setText("$0.00");
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(20, 250, 400, height);
        label.setVisible(true);

        return label;
    }
    
    private String generateNumber()
    {
        String str  = "";
        for(int i = 0; i < 16; i++) 
        {
            if(i == 0) 
            {
                str += "" + (rand.nextInt(9) + 1);
            } 
            else 
            {
                str += "" + rand.nextInt(10);
            }
        }
        return str;
    }
    
    
    public static void main(String[] a)
    {
        // Bank account list for reference/testing
        BankAccountList bankie = new BankAccountList();
        bankie.add(new BankAccount("FirstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("airstName", "MiddleName", "dastName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("birstName", "MiddleName", "castName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("hewwo", "ame", "ame", 1234567890123456L)); 
        bankie.add(new BankAccount("cirstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "bastName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "LastName", 1111111111111111L));
        bankie.add(new BankAccount("dirstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "aastName", 1234567890123456L));
        bankie.add(new BankAccount("hewwo", "ame", "ame", 1234567890123456L)); 
        bankie.add(new BankAccount("FirstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("virstName", "MiddleName", "LastName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "rastName", 1234567890123456L));
        bankie.add(new BankAccount("wirstName", "MiddleName", "LtastName", 1234567890123456L));
        bankie.add(new BankAccount("FirstName", "MiddleName", "zastName", 1234567890123456L));
        bankie.add(new BankAccount("hewwohgjhgj", "ggfhfuuyuiame", "ame", 5555555555555555L)); 
        WithdrawDialog i = new WithdrawDialog(bankie);
        bankie.ba[17].deposit(5000); // example of what would happen to deposit smth and the withdraw
        BankAccount b = bankie.ba[8];
        b = i.showDialog(null);
        System.out.print(b);
    }
}