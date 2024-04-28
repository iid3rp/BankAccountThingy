package BankAccountThingy.pp2.BankAccount;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
import BankAccountThingy.pp2.BankAccount.Utils.Region;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

/**
 * BankAccouny :3
 */
public class BankAccount
{
    private String accountName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String givenName;
    private long accountNumber;
    private double balance;
    private static double interestRate = .15;
    
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
    @Deprecated
    public BankAccount(String name, long num, double balance)
    {
        accountName = name;
        accountNumber = num;
        this.balance = balance;
    }
    
    @Intention(design = "reading a BankAccount to its .csv file.")
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

    @Region(value = "prior constructor.")
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
        balance = bank.getBalance(); // how did i forget about this??????????? :sob:
    }
    
    // citation: Mrs. Kim Vera Tequin (from her `makeCopy()` method)
    // i didn't realize i kinda needed this method now qwq
    public void referenceFromBank(BankAccount bank)
    {
        firstName = bank.getFirstName();
        middleName = bank.getMiddleName();
        lastName = bank.getLastName();
        givenName = bank.getLastName();
        accountName = (givenName.isEmpty()? " " : givenName + ", ") + firstName + " " + (middleName.isEmpty()? " " : middleName.toUpperCase().charAt(0) + ".");
        accountNumber = bank.getAccountNumber();
        balance = bank.getBalance();
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
    
    // mag-kuha ug account number:
    // ayaw ka confuse.. pwede man ug int, base sa inyoha
    public long getAccountNumber()
    {
        return accountNumber;
    }
    
    // mag-kuha ug account name
    public String getAccountName()
    {
        return accountName;
    }
    
    // kuha-on ang balance sa imohang account
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
    
    // mag set ug interest sa bank account:
    public static void setInterestRate(double rate)
    {
        interestRate = rate;
    }
    
    // mag get sa interest
    public static double getInterestRate()
    {
        return interestRate;
    }    
    
    @Deprecated
    public void addInterestRate()
    {
        balance += balance * interestRate;
    }

    // withdrawing process
    public void withdraw(double money)
    {
        balance = balance >= money? balance - money : balance;
    }
    
    // String equivalent sa imohang account
    public String toString()
    {
        return "Account Name: " + getAccountName() + "\n" +
               "Account Number: " + getAccountNumber() + "\n" +
               "Account Balance: " + balance;
    }
    
    // optional: shallow copy
    public BankAccount getCopy()
    {
        return this;
    }
    
    // optional: pag identify kung ang duha ka BankAccount class kay equal sila...
    public boolean equals(BankAccount ba)
    {
        return this == ba;
    }


    @Deprecated
    public BankAccount setEmpty()
    {
        return null;
    }

}