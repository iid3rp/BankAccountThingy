package BankAccountThingy.pp2.BankAccount.StreamIO;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.BankAccount2;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

/**
 * BankMaker class provides functionalities for creating and managing bank data.
 *
 * @author Francis (iid3rp) Madanlo
 */
public class BankMaker 
{
    private String title, fileTitle, fileName;

    private static final String[] header = {"First Name", "Middle Name", "Last Name", "Account Number", "Balance"};
    public static final String pictures = System.getProperty("user.home") + File.separator + "Documents" + File.separator +
                                           "BankAccount" + File.separator;
    public static final String picturesNonUnix = pictures.replaceAll("\\u005c", "/");

    /** there's difference between the title of the bank and the file name of the bank, bc there are
     * such limitations to the file naming than the title of the bank in the interface of the
     * BankAccount's InitialFrame.
     *
     * <p></p><p>constructor of the class...</p>
     * @param title the title of the String based on the bank list name
     * @param serial the serial number of the bank list itself
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
        fileName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + fileTitle +".csv";

        @Intention(design = "variable not used in this context")
        var x = new File(BankMaker.pictures + serial).mkdirs();

        try
        {
            createCSV(fileName, bankInfo, header);
        } catch (IOException e) { /* ignore the exception */ }
    }

    /**
     * no one uses this constructor
     */
    private BankMaker() {}

    /**
     * This static method rewrites the contents of a file with information from a provided BankAccountList object.
     *
     * @param file The File object representing the file to be overwritten.
     * @param ba The BankList object containing the bank data to be written.
     * @throws IOException If there are any issues encountered during file operations.
     */
    public static void rewriteFile(File file, BankAccountList ba) throws IOException
    {
        String filePath = file.getAbsolutePath();
        // try-with-resources method btw :3
        try(FileWriter writer = new FileWriter(file))
        {
            writer.write("Title," + ba.getTitle() + ",Serial UID," + ba.getSerial() + "\n");
            writer.write(String.join(",", header) + "\n");

            if(ba.ba != null)
            {
                for(BankAccount2 b : ba.ba)
                {
                    writer.write(b.getFirstName() + "," +
                                      b.getMiddleName() + "," +
                                      b.getLastName() + "," +
                                      b.getAccountNumber() + "," +
                                      b.getBalance() + "\n");
                }
            }

            System.out.println("CSV file created successfully at: " + file.getAbsolutePath());
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a CSV (Comma-Separated Values) file with a user-specified filename.
     *
     * @param fileName The name of the CSV file to be created.
     * @param bankInfo  the Bank information of the CSV file to be created
     * @param header the constant string to be used as a header
     * @throws IOException If there are any issues encountered during file creation.
     */
    public void createCSV(String fileName, String[] bankInfo, String[] header) throws IOException 
    {
        FileWriter writer = new FileWriter(fileName);

        writer.write(String.join(",", bankInfo) + "\n");
        writer.write(String.join(",", header) + "\n");

        System.out.println("CSV file created successfully at: " + fileName);
        writer.close();

    }

    /**
     * Creates a BankAccountPane that will put to the frame with the usage of the file...
     *
     * @param file The name of the CSV file to be created.
     * @param frame the InitialFrame object to be the reference pointer.
     * @return BankAccountPane
     * @see BankAccountPane
     */
    public BankAccountPane createBankAccountList(InitialFrame frame, File file)
    {
        return new BankReader().createListFromBank(frame, file);
    }

    public String getTitle()
    {
        return title;
    }

    public String getFileName()
    {
        return fileName;
    }
}
