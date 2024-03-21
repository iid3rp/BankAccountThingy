package pp2.BankAccount;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.*;
import java.util.Random;
public class AddBankAccount extends JDialog
{
    private boolean result;
    public Random rand = new Random();
    public AddBankAccount()
    {
        super();
        setTitle("Adding a Bank Account");    
        setSize(new Dimension(400, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        //setUndecorated(true);
        
        JPanel panel = createPanel();
        add(panel);
        
        JPanel image = createImagePanel();
        panel.add(image);
        
        JTextField firstName = createFirstName();
        JTextField middleName = createMiddleName();
        JTextField lastName = createLastName();
        
        JLabel accountNumber = createCreditNumber();
        panel.add(accountNumber);
        panel.add(createNumber());
        
        panel.add(firstName);
        panel.add(middleName);
        panel.add(lastName);
        
    }
    
    public void showDialog()
    {
        setVisible(true);
    }
    
    private JPanel createPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(new Dimension(400, 400));
        panel.setVisible(true);
        return panel;
    }
    
    private JPanel createImagePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(250, 10, 100, 100);
        panel.setBackground(Color.GRAY);
        
        return panel;
    }
        
    private JTextField createFirstName() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(10, 10, 200, 25);
        textField.setEnabled(false);
        textField.setText("First Name: ");
        textField.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                
            }
        });
        
        textField.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                    textField.setEnabled(true);
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Set back to default color
            }
        });


        return textField;
    }
    
    private JTextField createMiddleName() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(10, 40, 200, 25);
        textField.setVisible(true);
        textField.setEnabled(false);
        textField.setText("Middle Name: ");
        textField.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                
            }
        });
        
        textField.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                    textField.setEnabled(true);
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Set back to default color
            }
        });


        return textField;
    }
    
    private JTextField createLastName() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(10, 70, 200, 25);
        textField.setVisible(true);
        textField.setEnabled(false);
        textField.setText("Last Name: ");
        textField.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                
            }
        });
        
        textField.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                    textField.setEnabled(true);
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Set back to default color
            }
        });

        return textField;
    }
    
    private JLabel createNumber()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setText("This will be your credit Card:");
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 200, width, height);
        label.setVisible(true);

        return label;
    }
    
    private JLabel createCreditNumber()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
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
                if((i + 1) % 4 == 0 && (i + 1) != 16) 
                {
                    str += " ";
                }
            }
        }
        label.setText(str);
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 230, width, height);
        label.setVisible(true);

        return label;
    }
    
    public static void main(String[] a)
    {
        AddBankAccount i = new AddBankAccount();
        i.showDialog();
    }
}