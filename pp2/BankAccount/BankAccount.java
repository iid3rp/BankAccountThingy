package pp2.BankAccount; // packaging rani.. ayaw sundoga kung ang imohang java file kay same sa folder saimohang BankAccount java file

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;
import java.io.File;
import javax.swing.JFileChooser;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

public class BankAccount
{
    // self-explanatory na mga variables
    private String accountName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String givenName;
    private long accountNumber;
    private double balance;
    private static double interestRate = 0; 
    
    // default constructor
    public BankAccount()
    {
        accountName = "";
        accountNumber = 0;
        balance = 0;
        firstName = "";
        middleName = "";
        lastName = "";
    }
    
    // constructor with parameters
    @Deprecated // @Deprecated means dili na ni siya magamit... for read-only purposes rani
    public BankAccount(String name, long num, double balance)
    {
        accountName = name;
        accountNumber = num;
        balance = balance;
    }
    
    public BankAccount(String first, String second, String last, long num, double b)
    {
        firstName = first;
        middleName = second;
        lastName = last;
        givenName = last == null? first : last;
        accountName = givenName + ", " + firstName + " " + middleName.toUpperCase().charAt(0) + ".";
        accountNumber = num;
        balance = b;
    }
    
    public BankAccount(String first, String second, String last, long num)
    {
        firstName = first;
        middleName = second;
        lastName = last;
        givenName = last == null? first : last;
        accountName = givenName + ", " + firstName + " " + middleName.toUpperCase().charAt(0) + ".";
        accountNumber = num;
    }
    
    public BankAccount(BankAccount bank)
    {
        firstName = bank.getFirstName();
        middleName = bank.getMiddleName();
        lastName = bank.getLastName();
        givenName = bank.getLastName();
        accountName = (givenName.isEmpty()? " " : givenName + ", ") + firstName + " " + (middleName.isEmpty()? " " : middleName.toUpperCase().charAt(0) + ".");
        accountNumber = bank.getAccountNumber();
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getMiddleName()
    {
        return middleName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    // magkuha ug account number:
    // ayaw ka confuse.. pede man ug int, base sa inyoha
    public long getAccountNumber()
    {
        return accountNumber;
    }
    
    // magkuha ug account name
    public String getAccountName()
    {
        return accountName;
    }
    
    // kuhaon ang balance sa imohang account
    public double getBalance()
    {
        return balance;
    }
    
    // mag change ka sa imohang past name
    public void setAccountName(String name)
    {
        accountName = name;
    }
    
    public void setFirstName(String name)
    {
        firstName = name;
    }
    
    public void setMiddleName(String name)
    {
        middleName = name;
    }
    
    public void setLastName(String name)
    {
        lastName = name;
    }
    
    // mag change ka ug imohang past account number
    public void setAccountNumber(long num)
    {
        accountNumber = num;
    }
    
    // deposit ka ug kwarta
    public void deposit(double money)
    {
        balance += money; 
    }
    
    // mag set ug interes sa bank account:
    public static void setInterestRate(double rate)
    {
        interestRate = rate;
    }
    
    // mag get sa interes
    public static double getInterestRate()
    {
        return interestRate;
    }    
    
    public void addInterestRate()
    {
        balance += balance * interestRate;
    }
    
    // kani ang katong withdrawing process
    public boolean withdraw(double money)
    {
        if(balance >= money) // katong pag ang imohang gusto na iwithdraw kay dili dako na insufficient funds
        {
            balance -= money; // i-minus siya sa imohang balance
            return true; // ibalik ang true value
        }
        else return false; // ibalik ang false value pag insufficient funds
    }
    
    // kanang String equivalent sa imohang account
    public String toString()
    {
        return "Account Name: " + getAccountName() + "\n" +
               "Account Number: " + getAccountNumber() + "\n" +
               "Account Balance: " + balance;
    }
    
    // optional: pagkuha aning class as shallow copy
    public BankAccount getCopy()
    {
        return this;
    }
    
    // optional: pag identify kung ang duha ka BankAccount class kay equal sila...
    public boolean equals(BankAccount ba)
    {
        if(this == ba)
        {
            return true;
        }
        else return false;
    }
    
    // optional: magkuha ug image sa bankaccount with the use of the bank account's number as the file name:
    public BufferedImage tryImage(String s)
    {
        BufferedImage bf = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);  
        try
        {     
            String path = this.getClass().getResource("Accounts/" + getAccountNumber() + ".png") == null? 
                          this.getClass().getResource("Resources/default-image.jpg").getPath() :  // true
                          this.getClass().getResource("Accounts/" + getAccountNumber() + ".png").getPath(); // false
                          
            Image image = ImageIO.read(new File(path));
            image = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH); 
            bf = paintImage(bf, image);
        }
        catch(NullPointerException | IOException e)
        {
            e.printStackTrace();
            System.out.print("hello WOrld!");
        }  
        return bf;
    }
    
    private BufferedImage paintImage(BufferedImage b, Image i)
    {
        Graphics2D g2d = b.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillOval(0, 0, 70, 70);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
        g2d.drawImage(i, 0, 0, null);
        g2d.dispose();
        return b;
    }
    
    public static void main(String[] a) {
        BankAccount bank = new BankAccount();
        System.out.print(bank.tryImage(""));
    }

}