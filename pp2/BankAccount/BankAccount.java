package pp2.BankAccount; // packaging rani.. ayaw sundoga kung ang imohang java file kay same sa folder saimohang BankAccount java file

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
}