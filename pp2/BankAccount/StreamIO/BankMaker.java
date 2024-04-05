package BankAccountThingy.pp2.BankAccount.StreamIO;

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
import java.io.FileWriter;
import java.io.BufferedReader;
import java.nio.file.Paths;


public class BankMaker 
{
    public String title, fileTitle;
    public BankMaker(String title, long serial) 
    {   
        // theres difference between the title of the bank and the file name of the bank, bc there are
        // such limitations to the the file naming than the title of the bank in the @interface of the
        // BankAccount's InitialFrame.
        
        // and the title of the bank
        this.title = title;
        
        // the file title
        String fileTitle = title.replaceAll(" ", "_") // Replace spaces with underscores
                                .replaceAll("[^a-zA-Z0-9_.-]", ""); // Remove remaining special characters
                                
        // contents of every bank account csv in order:
        String[] bankInfo = {"Title ->", title, "Serial UID ->", serial + ""};
        String[] header = {"First Name", "Middle Name", "Last Name", "Account Number", "Balance"};
        
        // Get the directory of the Java file
        
        
        String fileName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + fileTitle +".csv";

        try {
            createCSV(fileName, bankInfo, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean createBank(String title, long serial) throws IOException
    {
        BankMaker b = new BankMaker(title, serial);
        return true;
    }
    
    @Deprecated
    public static void main(String[] args) throws IOException 
    {
        createBank("BSIT-BTM Students' List for 2023-2024", 1234567890123456L);
    }

    public void createCSV(String fileName, String[] bankInfo, String[] header) throws IOException 
    {
        // try-with-resources method btw :3
        try(FileWriter writer = new FileWriter(new File(fileName))) 
        {
            writer.write(String.join(",", bankInfo) + "\n");
            writer.write(String.join(",", header) + "\n");

            System.out.println("CSV file created successfully at: " + fileName);
        }
    }
}
