package BankAccountThingy.pp2.BankAccount.StreamIO;

import BankAccountThingy.InitialFrame;

import javax.swing.JFileChooser;
import java.io.File;

/**
* The `BankChooser` class extends `JFileChooser` and provides a way to select a bank data file.
*
* @author Francis (iid3rp) Madanlo
*/
public class BankChooser extends JFileChooser
{
    /**
     * This private constructor is intended to prevent direct instantiation of the `BankChooser` class.
     * It enforces the use of the static method `showDialog` to create a bank chooser dialog.
     */
    private BankChooser()
    {
        super();
    }

    /**
     * This static method displays a file chooser dialog specifically for selecting bank data files.
     *
     * @param frame The final class InitialFrame object that will be the parent of the file chooser dialog.
     * @return A File object representing the selected bank data file, or null if the dialog is canceled.
     */
    public static File showDialog(InitialFrame frame)
    {
        BankChooser b = new BankChooser();
        int returnValue = b.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            return b.getSelectedFile();
        }
        return null;
    }
}
