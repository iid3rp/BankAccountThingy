package BankAccountThingy.pp2.BankAccount.StreamIO;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;


public class BankMaker 
{
    public String title, fileTitle;
    public BankMaker(String title, long serial)
    {   
        // theres difference between the title of the bank and the file name of the bank, bc there are
        // such limitations to the file naming than the title of the bank in the interface of the
        // BankAccount's InitialFrame.

        // and the title of the bank
        this.title = title;

        // the file title
        fileTitle = title.replaceAll(" ", "_") // Replace spaces with underscores
                         .replaceAll("[^a-zA-Z0-9_.-]", ""); // Remove remaining special characters

        // contents of every bank account csv in order:
        String[] bankInfo = {"Title ->", title, "Serial UID ->", serial + ""};
        String[] header = {"First Name", "Middle Name", "Last Name", "Account Number", "Balance"};

        // Get the directory of the Java file


        String fileName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + fileTitle +".csv";
        fileTitle = System.getProperty("user.home") + File.separator + "\\Documents\\jGRASP\\BankAccountThingy\\pp2\\BankAccount\\StreamIO\\" + fileTitle + ".csv";
        try {
            createCSV(fileTitle, bankInfo, header);
        } catch (IOException e) { /* ignore the exception */ }
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

    public BankAccountPane createBankAccountList(InitialFrame frame, File file)
    {
        return new BankReader().createListFromBank(frame, file);
    }
}
