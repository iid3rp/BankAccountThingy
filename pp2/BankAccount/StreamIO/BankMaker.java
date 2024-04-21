package BankAccountThingy.pp2.BankAccount.StreamIO;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;


public class BankMaker 
{
    public String title, fileTitle;

    private static final String[] header = {"First Name", "Middle Name", "Last Name", "Account Number", "Balance"};

    /** there's difference between the title of the bank and the file name of the bank, bc there are
     * such limitations to the file naming than the title of the bank in the interface of the
     * BankAccount's InitialFrame.
     */
    public BankMaker(String title, long serial)
    {
        // and the title of the bank
        this.title = title;

        // the file title
        fileTitle = title.replaceAll(" ", "_") // Replace spaces with underscores
                         .replaceAll("[^a-zA-Z0-9_.-]", ""); // Remove remaining special characters

        // contents of every bank account csv in order:
        String[] bankInfo = {"Title ->", title, "Serial UID ->", serial + ""};

        // Get the directory of the Java file (defaults at Documents folder)
        String fileName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + fileTitle +".csv";
        try
        {
            createCSV(fileTitle, bankInfo, header);
        } catch (IOException e) { /* ignore the exception */ }
    }

    private BankMaker() {}

    public static void rewriteFile(File file, BankAccountList ba)
    {
        String filePath = file.getAbsolutePath();
        // try-with-resources method btw :3
        try(FileWriter writer = new FileWriter(file))
        {
            writer.write("Title," + ba.getTitle() + "Serial UID," + ba.getSerial() + "\n");
            writer.write(String.join(",", header) + "\n");

            for(BankAccount b : ba.ba)
            {
                writer.write(b.getFirstName() + "," +
                                  b.getMiddleName() + "," +
                                  b.getLastName() + "," +
                                  b.getAccountNumber() + "," +
                                  b.getBalance() + "\n");
            }

            System.out.println("CSV file created successfully at: " + file.getAbsolutePath());
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCSV(String fileName, String[] bankInfo, String[] header) throws IOException 
    {
        // try-with-resources method btw :3
        try(FileWriter writer = new FileWriter(fileName))
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
