package BankAccountThingy.pp2.BankAccount.StreamIO;

import java.io.*;
import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.BankAccount;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.BankAccountPane;

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
            reader.readLine(); // this is for reference to skip another line...

            // for loop for the iteration of other bank account list
            String line = reader.readLine();
            while(line != null)
            {
                String[] items = line.split(",");
                long number = Long.parseLong(items[3]);
                double balance = Double.parseDouble(items[4]);
                list.add(new BankAccount(items[0], items[1], items[2], number, balance));
                line = reader.readLine();
            }
            return new BankAccountPane(frame, list);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] a)
    {
        BankReader x = new BankReader();
    }
}