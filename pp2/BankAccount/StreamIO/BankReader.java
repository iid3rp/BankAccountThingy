package BankAccountThingy.pp2.BankAccount.StreamIO;

import javax.swing.JFileChooser;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import BankAccountThingy.pp2.BankAccount.BankAccountListPane;

@SuppressWarnings("")
public class BankReader
{
    
    public static BankAccountListPane createListFromBank(File f)
    {
        return null;
    }

    @Deprecated
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

    @Deprecated
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
        /*String filePath = selectFileForEdition();
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
        }*/
    }
}