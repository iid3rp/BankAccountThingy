package BankAccountThingy.pp2.BankAccount.StreamIO;

import javax.swing.*;
import java.io.File;

public class BankChooser extends JFileChooser
{
    private BankChooser()
    {
        super();
    }

    public static File showDialog()
    {
        BankChooser b = new BankChooser();
        int returnValue = b.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            return b.getSelectedFile();
        }
        return null;
    }
}
