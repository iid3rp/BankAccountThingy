package BankAccountThingy.pp2.BankAccount.Dialogs;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.Utils.DataType;
import BankAccountThingy.pp2.BankAccount.Utils.TextFieldFilter;
public class DepositDialog extends JDialog
{
    private BankAccount allocation; // this will be reference of the bankAccount to be withdrawn :#
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
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL); // this ensures modality of the JDialog
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
            b.deposit(totalAmount);
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
        label.setText("Deposit Money");
        label.setEnabled(false);
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        label.setBounds(getWidth() - width - 20 - 90, getHeight() - 50, width, height);

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

        label.setBounds(getWidth() - width - 20, y, width, height);
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
        checkBox.addItemListener(e -> putAmount());
        return checkBox;
    }
       
    private JTextField createAccountNumber() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(20, 75, 400, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("Enter Bank Account Number:");
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new TextFieldFilter(DataType.TYPE_NUMERICAL));
        
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
        long num = 0;
        try
        {
            num = Long.parseLong(accountNumber.getText());
        }
        catch(NumberFormatException e) { /* ignore the exception */ }
        finally
        {
           BankAccount b = lib.searchByNumber(num);
            if(b == null)
            {
                bankIdentifier.setText("No Bank Account was found.");
                moneyHandler.setText("Enter Amount [$]");
                moneyHandler.setEnabled(false);
            }
            else 
            {
                bankIdentifier.setText("<html> Bank Account identified as <b>'" + b.getAccountName() + "'</b>");
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
        doc.setDocumentFilter(new TextFieldFilter(DataType.TYPE_CURRENCY));

        
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
        if((moneyHandler.getText().equals("Enter Amount: [$]") || moneyHandler.getText().isEmpty()))
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

            if(allocation != null)
            {
                ok.setEnabled(totalAmount > 0);
            }
        }
    }
    
    
    private JLabel createBankIdentifier()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText("<html>The Bank Account will be identified <b>here.</b></html>");
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
        StringBuilder str  = new StringBuilder();
        for(int i = 0; i < 16; i++) 
        {
            if(i == 0) 
            {
                str.append(rand.nextInt(9) + 1);
            } 
            else 
            {
                str.append(rand.nextInt(10));
            }
        }
        return str.toString();
    }
}