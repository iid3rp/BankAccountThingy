package pp2.BankAccount.Dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pp2.BankAccount.*;
public class EditBankAccount extends JDialog
{
    private boolean result;
    
    public JPanel imageEditor;
    
    public JTextField firstName;
    public JTextField middleName;
    public JTextField lastName;
    public JLabel accountNumber;
    
    public Point offset;
    public boolean isDragging;
    
    private boolean confirm = false;
    
    public Random rand = new Random();
    public EditBankAccount()
    {
        super();
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL); // this ensures modallity of the jdialog
        setTitle("Adding a Bank Account");    
        setSize(new Dimension(550, 300));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        JPanel panel = createPanel();
        add(panel);
        
        imageEditor = createImagePanel();
        imageEditor.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                BufferedImage image = null;  // Initialize image to null

                // ... file chooser setup ...
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
                fileChooser.setFileFilter(filter);

                JLabel label = new JLabel();
                // Show the file chooser dialog
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                
                    // Load the selected image file
                    try 
                    {
                        image = ImageIO.read(selectedFile);
                        label = createImageEditor(image); // Initialize the image editor
                        if(imageEditor.getComponentCount() == 0) // checking if the panel is empty...
                        {
                            imageEditor.add(label);  // Add the label to the panel
                        }
                        else
                        {
                            imageEditor.removeAll();
                            imageEditor.add(label);
                        }
                        imageEditor.repaint();
                        imageEditor.validate();

                    } 
                    catch (IOException err) 
                    {
                        err.printStackTrace();
                        System.out.println("Error loading image: " + err.getMessage());
                    }
                }            
            }
        });
        panel.add(imageEditor);
        
        firstName = createFirstName();
        middleName = createMiddleName();
        lastName = createLastName();
        
        JLabel ok = createOk();
        panel.add(createCancel(10, "X"));
        panel.add(createCancel(getHeight() - 50, "Cancel"));
        
        panel.add(createTitle(20, 20, 10, "Add Account"));
        
        accountNumber = createCreditNumber();
        panel.add(accountNumber);
        panel.add(createNumber());
        
        panel.add(firstName);
        panel.add(middleName);
        panel.add(lastName);
        panel.add(ok);
        
    }
    
    public BankAccount showDialog(BankAccount edit) 
    {
        
        setVisible(true);
        if(confirm)
        {
            BankAccount b = new BankAccount();
            b.setFirstName(firstName.getText());
            b.setMiddleName(middleName.getText());
            b.setLastName(lastName.getText());
            b.setAccountNumber(Long.parseLong(accountNumber.getText().replaceAll(" ", "")));
            return new BankAccount(b);
        } 
        else return null;
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
    
    private JPanel createImagePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.setBounds(20, 50, 180, 180);
        panel.setBackground(Color.GRAY);
        
        return panel;
    }
    
    // diri maghimo ug feeling pfp sa imohang picture, but make it miniature to fit the image
    // also create zooming in/out properties...
    private JLabel createImageEditor(BufferedImage i)
    {
        System.out.println(i.getWidth() + " "+  i.getHeight());
        
        double ratio = Math.min(i.getWidth(), i.getHeight()) / 180;
        int width = (int) (i.getWidth() / ratio);  
        int height = (int) (i.getHeight() / ratio); 
        System.out.println("" + width + " " + height + " " + ratio);
        JLabel label = new JLabel()
        {
           
            Image img = i.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(i, 0, 0, 180, 180, null);
            }
        };
        label.setLayout(null);
        label.setBounds(0, 0, 180, 180);
        label.setVisible(true);
        label.repaint();
        return label;
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
        JLabel label = new JLabel("Create Account");
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
        
    private JTextField createFirstName() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(220, 50, 300, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setText("First Name: ");
        
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


        return textField;
    }
    
    private JTextField createMiddleName() 
    {
        JTextField textField = new JTextField();
        textField.setLayout(null);
        textField.setBounds(220, 80, 300, 25);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setVisible(true);

        textField.setText("Middle Name: ");  
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
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setBounds(220, 110, 300, 25);
        textField.setVisible(true);

        textField.setText("Last Name: ");
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
        label.setBounds(220, 150, width, height);
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
        label.setBounds(220, 170, width, height);
        label.setVisible(true);

        return label;
    }
    
    public static void main(String[] a)
    {
        EditBankAccount i = new EditBankAccount();
        BankAccount h = i.showDialog(new BankAccount());
        System.out.print(h);
    }
}