package BankAccountThingy.pp2.BankAccount.StreamIO;

import javax.swing.JFileChooser;

import java.io.*;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;
import BankAccountThingy.pp2.BankAccount.Utils.Region;

public class BankReader
{
    public BankReader() {}
    public BankAccountPane createListFromBank(InitialFrame frame, File f)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String[] stuff = reader.readLine().split(",");

            String title = stuff[1];
            long serial = Long.parseLong(stuff[3]);

            BankAccountList list = new BankAccountList();
            list.setSerial(serial);
            list.setTitle(title);

            //
            // for loop for the iteration of other bank account list in the next commits :3
            //

            return new BankAccountPane(frame, list);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
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
        try (FileWriter writer = new FileWriter(filePath))
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 0; i++)
            {
                if (i < 1)
                {
                    sb.append(",");
                }
            }
            writer.write(sb.toString() + "\n");
        }
      // Optionally, replace the original file with the modified one (after successful write)
    }

    @Region(value = "continuation goes here derp...")
    public BankAccountList csvReading(File file)
    {
        try {
            BufferedReader f = new BufferedReader(new FileReader(file));
            String line = "";
            while(line != null) {
                line = f.readLine();

            }
            f.close();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        return null; // for now
    }

    public void hello()
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

    public static void main(String[] a)
    {
        BankReader x = new BankReader();
        x.hello();
    }
}