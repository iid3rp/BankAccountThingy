package BankAccountThingy.pp2.BankAccount.StreamIO;
import BankAccountThingy.pp2.BankAccount.BankAccountList;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import BankAccountThingy.pp2.BankAccount.BankAccount2;

public class ImageMaker
{
    public ImageMaker() {}

    /**
     * optional: mag-kuha ug image sa BankAccount with the use of the bank account's number as the file name:
     */
    public static BufferedImage parseImage(BankAccount2 bank, BankAccountList list, int length)
    {
        BufferedImage bf = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
        try
        {
            @Intention(design = "Apparently, it throws an IOException bc the file readers cannot" +
                    "be read when using File.separator for some reason... i guess we go hard-code..")
            File path = new File(BankMaker.picturesNonUnix + list.getSerial() + "/" + bank.getAccountNumber() + ".png");
            URL defaultImageURL = Objects.requireNonNull(BankAccount2.class.getResource("Resources/default-image.jpg"));

            Image image =  path.exists() ?
                    ImageIO.read(path) : ImageIO.read(defaultImageURL);

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
                BankAccount2.class.getResource("Resources/blob-reference.png")).getPath()));

        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
        g2d.drawImage(i, 0, 0, null);
        g2d.dispose();
    }

    public static void createImage(BankAccount2 bank, JLabel label, Image image, BankAccountList list) throws IOException
    {
        int x = label.getX();
        int y = label.getY();
        BufferedImage b = new BufferedImage(180, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(image, x, y, null);
        g2d.dispose();
        ImageIO.write(b, "png", new File(BankMaker.pictures + list.getSerial() + File.separator + bank.getAccountNumber() + ".png"));
    }
}
