package pp2.BankAccount.Dialogs;
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
import pp2.BankAccount.BankAccount;
import pp2.BankAccount.BankAccountListPane;
import pp2.BankAccount.BankAccountInterface;
public class AddBankAccount extends JDialog
{
    private boolean result;
    
    
    public JPanel imageEditor;
    
    public JTextField firstName;
    public JTextField middleName;
    public JTextField lastName;
    public JLabel accountNumber;
    
    public JLabel zoomIn;
    public JLabel zoomOut;
    public JLabel removeImage;
    
    public JLabel accountPicture;
    
    public Point offset;
    public boolean isDragging;
    
    private boolean confirm = false;
    
    private enum zoom
    {
        IN, OUT
    }
    
    public Random rand = new Random();
    
    public double mult = 1;
    public double refX = .5;
    public double refY = .5;
    
    private int picWidth, picHeight, picX, picY;
    public AddBankAccount()
    {
        super();
        setModalityType(ModalityType.APPLICATION_MODAL); // this ensures modallity of the jdialog
        setTitle("Adding a Bank Account");    
        setSize(new Dimension(550, 300));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        JPanel panel = createPanel();
        add(panel);
        
        removeImage = createRemoveImage();
        zoomIn = createZoom(zoom.IN);
        zoomOut = createZoom(zoom.OUT);
        
        imageEditor = createImagePanel();
        imageEditor.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                fileChoosing();            
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
        
        panel.add(zoomIn);
        panel.add(zoomOut);
        panel.add(removeImage);
        
        panel.add(firstName);
        panel.add(middleName);
        panel.add(lastName);
        panel.add(ok);
        
    }
    
    public BankAccount showDialog() 
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
        panel.setDoubleBuffered(true);
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
        picWidth = (int) (width * mult);
        picHeight = (int) (height * mult);
        // System.out.println("" + width + " " + height + " " + ratio);
        JLabel label = new JLabel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Image img = i.getScaledInstance((int) (width * mult),(int) (height * mult), Image.SCALE_SMOOTH); 
                g.drawImage(i, 0, 0, (int) (width * mult),(int) (height * mult), null);
            }
        };
        label.setLayout(null);
        label.setBounds((int) (imageEditor.getWidth() * refX) - (int) (width * refX),
                        (int) (imageEditor.getHeight() * refY) - (int) (height * refY), 
                        (int) (width * mult),(int) (height * mult));
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {   
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        
            @Override
            public void mouseClicked(MouseEvent e)
            {
                fileChoosing();
            }
            
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e)) 
                {
                    isDragging = true;
                    offset = e.getPoint();
                    System.out.println("Location: " + e.getPoint());
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
        label.addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                if (isDragging)
                {
                    Point parentContainerLocation = label.getParent().getLocationOnScreen();
                    Point mouseLocation = e.getLocationOnScreen();
                    int newX = mouseLocation.x - parentContainerLocation.x - offset.x;
                    int newY = mouseLocation.y - parentContainerLocation.y - offset.y;
                    
                    newX = newX > 0? 0
                         : newX < imageEditor.getWidth() - label.getWidth()? imageEditor.getWidth() - label.getWidth()
                         : newX;
                    newY = newY > 0? 0
                         : newY < imageEditor.getHeight() - label.getHeight()? imageEditor.getHeight() - label.getHeight()
                         : newY;
                    refX = (Math.abs(newX) + (imageEditor.getWidth() / 2)) / (width * mult);
                    refY = (Math.abs(newY) + (imageEditor.getWidth() / 2)) / (height * mult); 
                    System.out.println(refX + ", " + refY);
                    label.setLocation(newX, newY);
                }
            }
        });
        return label;
    }
    
    private JLabel createZoom(zoom z)
    {   
        
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText(z == zoom.IN? "+" : "-");
        Point p = z == zoom.IN? new Point(100, 230) : new Point(130, 230);
        label.setBounds(p.x, p.y, 30, 30);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.out.println();
                mult = mult < 1 ? 1 
                                : (mult > 3 ? 3 
                                            : (z == zoom.IN ? Math.min(mult + .1, 3) 
                                                            : Math.max(mult - .1, 1)));
                
                // System.out.println(mult); // debug;
                double zoomedWidth = picWidth * mult;
                double zoomedHeight = picHeight * mult; 
                System.out.print(zoomedWidth + " " + zoomedHeight + " " + picWidth + " " + picHeight); //debug
            
                double xOffset = (imageEditor.getWidth() - zoomedWidth) * refX;
                double yOffset = (imageEditor.getWidth() - zoomedHeight) * refY;
                
                // Set new bounds for the component (replace with your library's method)
                accountPicture.setBounds((int) xOffset, (int) yOffset, (int) (picWidth * mult), (int) (picHeight * mult));
                accountPicture.repaint();
                accountPicture.validate();
                imageEditor.repaint();

            }
        });
        return label;
    }
    
    private JLabel createRemoveImage()
    {   
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setText("x");
        label.setBounds(70, 230, 30, 30);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(accountPicture != null)
                {
                    imageEditor.remove(accountPicture);
                    imageEditor.repaint();
                }
            }
        });
        return label;
    }
    
    private void fileChoosing()
    {
        BufferedImage image = null;  // Initialize image to null

        // ... file chooser setup ...
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);

        accountPicture = new JLabel();
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
                accountPicture = createImageEditor(image); // Initialize the image editor
                if(imageEditor.getComponentCount() == 0) // checking if the panel is empty...
                {
                    imageEditor.add(accountPicture);  // Add the label to the panel
                }
                else
                {
                    imageEditor.removeAll();
                    imageEditor.add(accountPicture);
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
        AddBankAccount i = new AddBankAccount();
        BankAccount h = i.showDialog();
        System.out.print(h);
    }
}