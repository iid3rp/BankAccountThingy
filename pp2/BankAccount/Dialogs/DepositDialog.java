package pp2.BankAccount.Dialogs;
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
import pp2.BankAccount.BankAccount;
import pp2.BankAccount.BankAccountList;
import pp2.BankAccount.BankAccountListPane;
import pp2.BankAccount.BankAccountInterface;
import pp2.BankAccount.StreamIO.BankMaker;
public class DepositDialog extends JDialog
{
    private boolean result;
    
    public JPanel imageEditor;
    public JPanel panel;
    
    
    public JTextField accountNumber;
    public JTextField moneyHandler;
    public JCheckBox addInterest;
    public JLabel bankIdentifier;
    public JLabel amount;
    public JLabel ok;
    
    public Point offset;
    public boolean isDragging;
    public BankAccountList lib;
    
    private boolean confirm = false;
    
    public Random rand = new Random();
    public double totalAmount;
    public DepositDialog(BankAccountList b)
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
        
        ok = createOk();
        panel.add(createCancel(10, "X"));
        panel.add(createCancel(getHeight() - 50, "Cancel"));
        
        addInterest = createCheckBox();
        panel.add(createTitle(20, 20, 10, "Deposit Money"));
        
        panel.add(createText("<html><b>Account Number", 50));
        panel.add(createText("<html><b>Amount [$]", 135));
        panel.add(createText("<html><b>Total Amount:", 235));
        panel.add(bankIdentifier);
        panel.add(moneyHandler);
        panel.add(amount);
        panel.add(addInterest);
        
        panel.add(accountNumber);
        panel.add(ok);
        
    }
    
    // depositing method
    public BankAccount showDialog(BankAccount b) 
    {
        if(b != null)
        {
            // modify if the bank is not null lol
            accountNumber.setText(String.valueOf(b.getAccountNumber()));
            accountNumber.setEnabled(false);
        }
        setVisible(true);
        if(b == null) // however
        {
            b = lib.searchByNumber(Long.parseLong(accountNumber.getText()));
        }
        b.deposit(totalAmount);
        return b;
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
        label.setText("Deposit Money");
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
    
    // Method to create a checkbox with listener
    private JCheckBox createCheckBox() 
    {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setLayout(null);
        checkBox.setFont(new Font("Segoe UI", Font.BOLD, 15));
        checkBox.setText("Add Interest rate on deposit (" + (1 + BankAccount.getInterestRate()) + ")");
        Dimension d = checkBox.getPreferredSize();
        checkBox.setBounds(20, 200, (int) d.getWidth() + 20, (int) d.getHeight());
        checkBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e) 
            {
                putAmount();
            }
        });
        return checkBox;
    }
       
    private JTextField createAccountNumber() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(20, 75, 400, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("Enter Bank Account Number:");
        
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
            }
            else bankIdentifier.setText("<html> Bank Account identified as <b>'" + b.getAccountName() + "'</b>");
        }
    }
    
    private JTextField createMoney() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(20, 160, 400, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("Enter Amount: [$]");
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new MyIntFilter());

        
        textField.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                textField.setCaretColor(Color.BLACK);
                textField.setText("");
                textField.setForeground(Color.BLACK); // Set back to default color
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
        }
        else
        {
            double moneyAmount = Double.parseDouble(moneyHandler.getText());
            totalAmount = moneyAmount * (addInterest.isSelected() ? (1 + BankAccount.getInterestRate()) : 1);
            // Format the total amount like currency
            String formattedAmount = String.format("$%.2f", totalAmount);
            amount.setText(formattedAmount);
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
        DepositDialog i = new DepositDialog(bankie);
        BankAccount b = bankie.ba[8];
        b = i.showDialog(b);
        System.out.print(b);
    }
}

class MyIntFilter extends DocumentFilter 
{
    // thank you Stack Overflow with this one!!!
    // citation: https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
    // user: https://stackoverflow.com/users/522444/hovercraft-full-of-eels :3 :3 :3
    // index modifiers will be stated here xd
    //
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);
        
        if (test(sb.toString())) 
        {
            super.insertString(fb, offset, string, attr);
        } 
        else 
        {
            // warn the user and don't allow the insert
        }
    }

    private boolean test(String text) 
    {
        if (text.isEmpty()) 
        {
            return true; // Allow blank document
        }
        try 
        {
            Double.parseDouble(text);
            String[] parts = text.split("\\.");
            if (parts.length > 1) 
            {
                // Check if the fractional part has more than two digits
                String fractionalPart = parts[1];
                if (fractionalPart.length() > 2) 
                {
                    return false; // More than two decimal places
                }
            }
        
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
    {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);
    
        if (test(sb.toString())) 
        {
            super.replace(fb, offset, length, text, attrs);
        } 
        else 
        {
            // warn the user and don't allow the insert
        }
 
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);
    
        if (test(sb.toString())) 
        {
        
           super.remove(fb, offset, length);
        } 
        else 
        {
           // warn the user and don't allow the insert
        }
    }
}