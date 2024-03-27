package pp2.BankAccount.StreamIO;

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
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedReader;
import java.nio.file.Paths;
import pp2.BankAccount.BankAccountListPane;

public class BankReader
{
    
    public static BankAccountListPane createListFromBank(File f)
    {
        for()
        return null;
    }
    
    public String selectFileForEdition() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
    
        if (returnValue == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
    
    public void editCSVFile(String filePath) throws IOException {
        // Read the entire CSV file into a List of String arrays (rows)
        // no arraylist, remember :3
        
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
        }
    
        // Modify the data in csvData (replace with your modification logic)
        // ... your logic here to modify data in csvData ... 
    
        // Write the modified data to a new CSV file (consider using a temporary file)
        String newFilePath = filePath + ".modified"; // Replace with temporary filename logic
        try (FileWriter writer = new FileWriter(newFilePath)) {
            //for() 
            //{
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 0; i++) 
                {
                    sb.append("");
                    if (i < 1) 
                    {
                        sb.append(",");
                    }
                }
                writer.write(sb.toString() + "\n");
            //}
        }
      // Optionally, replace the original file with the modified one (after successful write)
    }
    
    public static void main(String[] a)
    {
        String filePath = selectFileForEdition();
        if (filePath != null) {
            try {
                editCSVFile(filePath);
                System.out.println("CSV file edited successfully!");
            } 
            catch (IOException e) {
                System.err.println("Error editing CSV file: " + e.getMessage());
            }
        } 
        else {
            System.out.println("No file selected.");
        }
    }
}