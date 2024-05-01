package BankAccountThingy.pp2.BankAccount.StreamIO;

import java.io.*;
import java.util.InputMismatchException;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.BankAccount2;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;

import javax.swing.*;

/**
 * The `BankReader` class provides functionalities for reading and processing bank data.
 *
 * @author Francis (iid3rp) Madanlo
 */
public class BankReader
{
    /**
     * Constructs a `BankReader` object. (Default constructor with no arguments)
     */
    public BankReader() {}

    /**
     * This method reads bank data from a specified file and potentially creates a BankAccountPane to store the data.
     *
     * <ul>
     * <li>Read the bank data from the file</li>
     * <li>Use a reference InitialFrame for any graphical user interface interaction during the reading process </li>
     * <li>Create a list object to store the parsed bank data\</li>
     * <li>Throw an exception if there are issues encountered during file reading, and returns nothing.</li>
     * </ul>
     * </p>
     *
     * @param frame A reference to an InitialFrame object, used for final UI interaction and reference pointing..
     * @param file The File object representing the bank data file to be read.
     * @return a BanlAccountPane containing the processed bank data (the type depends on implementation)
     *  that turns into a list ScrollPane based Component.
     */
    public BankAccountPane createListFromBank(InitialFrame frame, File file)
    {
        try {
            // FileWriter ug BufferedReader
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] stuff = reader.readLine().split(",");

            String title = stuff[1];
            long serial = Long.parseLong(stuff[3]);

            BankAccountList list = new BankAccountList();
            list.setSerial(serial);
            list.setTitle(title);
            reader.readLine(); // this is for reference to skip another line...

            // for loop for the iteration of other bank account list
            String line = reader.readLine();
            while(line != null)
            {
                String[] items = line.split(",");
                long number = Long.parseLong(items[3]);
                double balance = Double.parseDouble(items[4]);
                list.add(new BankAccount2(items[0], items[1], items[2], number, balance));
                line = reader.readLine();
            }
            return new BankAccountPane(frame, list);
        }
        catch(InputMismatchException | IOException | ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(frame, "File Invalid.");
            return null;
        }
    }
}