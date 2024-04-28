package BankAccountThingy.pp2.BankAccount.StreamIO;

import BankAccountThingy.pp2.BankAccount.Utils.Intention;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import BankAccountThingy.pp2.BankAccount.BankAccount;

public class ImageMaker
{
    public ImageMaker() {}

    /**
     * optional: mag-kuha ug image sa BankAccount with the use of the bank account's number as the file name:
     */
    public static BufferedImage tryImage(BankAccount bank, int length)
    {
        BufferedImage bf = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
        try
        {
            String path = BankAccount.class.getResource("Accounts/" + bank.getAccountNumber() + ".png") == null?
                    Objects.requireNonNull(BankAccount.class.getResource("Resources/default-image.jpg")).getPath() :  // true
                    Objects.requireNonNull(BankAccount.class.getResource("Accounts/" + bank.getAccountNumber() + ".png")).getPath(); // false

            Image image = ImageIO.read(new File(path));
            image = image.getScaledInstance(length, length, Image.SCALE_AREA_AVERAGING);
            paintImage(bf, image, length);
        }
        catch(NullPointerException | IOException e)
        {
            e.printStackTrace(System.out);
            System.exit(0);
        }
        return bf;
    }

    private static void paintImage(BufferedImage b, Image i, int length) throws IOException
    {
        @Intention(design = "Apparently, it throws an IOException bc the file [blob-reference] cannot" +
                "be read when using File.separator for some reason... i guess we go hard-code..")
        BufferedImage image = ImageIO.read(new File(Objects.requireNonNull(
                BankAccount.class.getResource("Resources/blob-reference.png")).getPath()));

        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
        g2d.drawImage(i, 0, 0, null);
        g2d.dispose();
    }

}
