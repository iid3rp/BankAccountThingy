package BankAccountThingy.pp2.BankAccount.StreamIO;
import BankAccountThingy.pp2.BankAccount.BankAccount2;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
import BankAccountThingy.pp2.BankAccount.Utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * the logger class to create a .csv file to write the logs of the BankAccount.
 *  <p></p>
 *  If the logger file wasn't made, it will automatically make one and this method
 *  will also automatically check if there was already a file, therefore it will not
 *  make a file anymore.
 *  <p></p>
 *  It will also write and create a {@code String[]} based on the actual input of it, and
 *  it'll override each log. (on other methods)
 *  <p></p>
 * the file string will always be:
 * <p></p>
 * {@code  C:\Users\<your_username_goes_here>\Documents\logger.csv}
 * <p></p>
 * @author iid3rp
 */
public class FileLogger
{
    // @author Francis (iid3rp) Madanlo
    // APPOPPRIATING THIS CODE IS AGAINST THE ETHICS CODE!!
    // no using of AI was made here...
    private final String loggerString = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "logger.csv";
    private final String header = "Logging Detail,Account Number (Bank Account),Bank Name,Date and Time\n";
    private FileWriter logger;
    private String[] logs;
    private int size;

    public FileLogger()
    {
        File file = new File(loggerString);
        try {
            if(!file.exists())
            {
                logger = new FileWriter(file);
                add(header);
                logger.close();
            }
            readLogs(file);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public boolean add(String string)
    {
        if(logs == null)
        {
            logs = new String[++size];
        }
        else
        {
            String[] reference = new String[++size];
            System.arraycopy(logs, 0, reference, 0, logs.length);
            logs = reference;
        }
        logs[size - 1] = string;
        return true;
    }

    public @Intention boolean add(Log log, BankAccountList list, BankAccount2 bank)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy | hh:mm:ss a.");
        String currentDateTime = formatter.format(now);
        String logString;
        switch(log)
        {
            case OPEN_APPLICATION:
            {
                logString = "Opened the application,,," + currentDateTime + "\n";
                break;
            }
            case CLOSE_APPLICATION:
            {
                logString = "Closed the applicatiom,,," + currentDateTime + "\n";
                break;
            }
            case OPEN_BANK:
            {
                logString = "Opened a bank,," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            case ADD_BANK:
            {
                logString = "Created a bank,," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            case ADD_ACCOUNT:
            {
                logString = "Added an account," + bank.getAccountNumber() + "," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            case EDIT_ACCOUNT:
            {
                logString = "Edited an account," + bank.getAccountNumber() + "," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            case DELETE_ACCOUNT:
            {
                logString = "Deleted bank account," + bank.getAccountNumber() + "," +  list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            case CLOSE_BANK: {
                logString = "Closed bank,," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + log);
        }
        System.out.println("logged");
        return add(logString);
    }

    public @Intention boolean add(Log log, BankAccountList list, BankAccount2 bank, double amount)
    {
        LocalDateTime now = LocalDateTime.now();
        amount = Math.abs(amount);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy | hh:mm:ss a.");
        String currentDateTime = formatter.format(now);
        String logString;
        switch(log)
        {
            case DEPOSIT:
            {
                logString = "Deposited $" + amount + "," + bank.getAccountNumber() + "," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            case WITHDRAW:
            {
                logString = "Withdrawn $" + amount + "," + bank.getAccountNumber() + "," + list.getTitle() + "," + currentDateTime + "\n";
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + log);
        }
        System.out.println("logged");
        return add(logString);
    }

    private void readLogs(File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line != null)
        {
            String nextLine = reader.readLine();
            add(nextLine != null? (line + "\n") : line);
            line = nextLine;
        }
        reader.close();

    }

    public void close() throws IOException
    {
        FileWriter writer = new FileWriter(loggerString);
        writer.write(String.join("", logs) + " ");
        System.out.println("Written");
        writer.close();
    }
}
