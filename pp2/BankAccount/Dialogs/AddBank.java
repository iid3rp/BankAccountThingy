package BankAccountThingy.pp2.BankAccount.Dialogs;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountListPane;
import BankAccountThingy.pp2.BankAccount.BankAccountInterface;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankMaker;
public class AddBank extends JDialog
{
    private boolean result;
    
    public JPanel imageEditor;
    
    public JTextField bankName;
    public JLabel bankNumber;
    public JLabel fileLabel;
    
    public Point offset;
    public boolean isDragging;
    
    private boolean confirm = false;
    
    public Random rand = new Random();
    public AddBank()
    {
        super();
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL); // this ensures modallity of the jdialog    
        setSize(new Dimension(500, 350));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        JPanel panel = createPanel();
        add(panel);
        
        
        bankName = createBankName();
        fileLabel = createFileLabel();
        
        JLabel ok = createOk();
        panel.add(createCancel(10, "X"));
        panel.add(createCancel(getHeight() - 50, "Cancel"));
        
        JLabel refresh = createRefresh();
        
        panel.add(createTitle(20, 20, 10, "Add New Bank"));
        
        bankNumber = createBankNumber();
        panel.add(bankNumber);
        panel.add(createNumber());
        panel.add(createTip());
        panel.add(fileLabel);
        panel.add(refresh);
        
        panel.add(bankName);
        panel.add(ok);
        
    }
    
    public BankMaker showDialog() 
    {
        setVisible(true);
        return confirm? new BankMaker(bankName.getText(), Long.parseLong(bankNumber.getText())) : null;
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
        JLabel label = new JLabel("Create New Bank");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
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
    
    private JLabel createRefresh()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText("regenerate number");
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(120, 150, width, height);
        label.setVisible(true);
        
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                bankNumber.setText(generateNumber());
            }
        });
        return label;
    }
        
    private JTextField createBankName() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(20, 50, 400, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("Create Bank Name:");
        
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
                fileLabel.setText("<html> it'll be saved as <b>'" + textField.getText().replaceAll(" ", "_")
                                                                             .replaceAll("[^a-zA-Z0-9_.-]", "") + ".csv' </b> at <br><b> " + System.getProperty("user.home") + "\\Documents <b/> <html/>");
                consistentLabelling();
            }
        
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                fileLabel.setText("<html> it'll be saved as <b>'" + textField.getText().replaceAll(" ", "_")
                                                                             .replaceAll("[^a-zA-Z0-9_.-]", "") + ".csv' </b> at <br><b> " + System.getProperty("user.home") + "\\Documents <b/> <html/>");
                consistentLabelling();
            }
         
            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                fileLabel.setText("<html> it'll be saved as <b>'" + textField.getText().replaceAll(" ", "_")
                                                                             .replaceAll("[^a-zA-Z0-9_.-]", "") + ".csv' </b> at <br><b> " + System.getProperty("user.home") + "\\Documents <b/> <html/>");
                consistentLabelling();
            }
        });



        return textField;
    }
    
    private JLabel createFileLabel()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText("<html> it'll be saved as <b>'.csv' </b> at <br><b> " + System.getProperty("user.home") + "\\Documents <b/> <html/>");
        Dimension d = label.getPreferredSize();
        label.setBounds(20, 75, (int) d.getWidth(), (int) d.getHeight());
        label.setVisible(true);

        return label;
    }
    
    private void consistentLabelling()
    {
        Dimension d = fileLabel.getPreferredSize();
        fileLabel.setBounds(20, 75, (int) d.getWidth(), (int) d.getHeight());
    }
        
    private JLabel createNumber()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setText("Serial UID:");
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(20, 150, width, height);
        label.setVisible(true);

        return label;
    }
    
    private JLabel createTip()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setText("<html> <b>tip:</b> Serial UIDs are used for securty measures <br> of the .csv (bank) file.");
        Dimension d = label.getPreferredSize();
        label.setBounds(20, 210, 400, (int) d.getHeight());
        label.setVisible(true);

        return label;
    }
    
    private JLabel createBankNumber()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        String str  = "";
        label.setText(generateNumber());
        // Get the FontMetrics object associated with the font
        FontMetrics metrics = getFontMetrics(label.getFont());
        
        // Calculate the width and height of the text
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(20, 170, width, height);
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
        AddBank i = new AddBank();
        BankMaker b = i.showDialog();
        System.out.print("h");
    }
}