import pp2.BankAccount.BankAccount;
import java.util.*;

public class BankAccountList extends ArrayList<BankAccount>
{
    private static Scanner s = new Scanner(System.in);
    public BankAccountList()
    {
        super();
    }
    
    public static void main(String[] args)
    {
        for(;;)
        {
            System.out.println("Welcome to my bank!");
            System.out.println("Select Options");
            System.out.println("[A]dd Account");
            System.out.println("[D]eposit money");
            System.out.println("[W]ithdraw money");
            System.out.println("[E]xit");
            String input = s.nextLine();
            char c = input.toLowerCase().charAt(0);
            switch(c)
            {
                case 'a': 
                    break;
            }
        }   
    }
}   