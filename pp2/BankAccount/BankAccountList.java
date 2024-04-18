package BankAccountThingy.pp2.BankAccount;

public class BankAccountList
{
    private int size;
    public BankAccount[] ba; // this is public for certain reasons...
    
    // sorting enum kay pangit mugamit ug int lol
    public enum Sort
    {
        FIRST_NAME, MIDDLE_NAME, LAST_NAME, ACCOUNT_NUMBER
    }
    
    public enum SortType
    {
        SORT_ASCENDING, SORT_DESCENDING
    }
    
    // default construtor
    public BankAccountList()
    {
        size = 0;
    }
    
    // constructor with @param
    public BankAccountList(BankAccountList b)
    {
        size = b.getLength();
        ba = b.getList();
    }
    
    // addition sa bank account sa array
    public boolean add(BankAccount b)
    {
        if(ba == null)
        {
            ba = new BankAccount[++size];
        }
        else
        {
            BankAccount[] reference = new BankAccount[++size];
            System.arraycopy(ba, 0, reference, 0, ba.length);
            ba = reference;
        }
        ba[size - 1] = b;
        return true;    
    }
    
    // addition sa bank account sa array (but including index to specifically put it)
    @Deprecated
    public boolean add(int index, BankAccount b)
    {
        if(index >= 0 || index <= size)
        {
            // ibutang sa ang existing inputs first sa reference
            BankAccount[] reference = new BankAccount[++size];
            if(index >= 0) System.arraycopy(ba, 0, reference, 0, index);
            
            // and then add tong index based sa katong part
            reference[index] = b;
            
            // and then iterate the remaining section afterwards..
            if(size - 1 - index >= 0) System.arraycopy(ba, index, reference, index + 1, size - 1 - index);
            // then call the index after
            ba = reference;
            return true;
        }
        else return false;  
    }
    
    // removes the part...
    public void removeBankAccount(BankAccount b)
    {
        // mangita siya sa BankAccount, one by one, in order.
        for(int i = 0; i < size; i++)
        {
            // pag equal siya sa iyahang target removal, call the remove(int) method...
            if(ba[i].getAccountNumber() == b.getAccountNumber())
            {
                removeBankAccount(i);
                return;
            }
        }
    }
    
    // much simpler than the remove(BankAccount)
    @Deprecated
    public boolean removeBankAccount(int index)
    {
        if(index >= 0 && index < size)
        {
            BankAccount[] reference = new BankAccount[--size];
            // iterate the first loops of the existing ones
            System.arraycopy(ba, 0, reference, 0, index);
            // then i-skip tong index na idelete, then iterate and add the rest
            if(size + 1 - (index + 1) >= 0)
            {
                System.arraycopy(ba, index + 1, reference, index + 1 - 1, size + 1 - (index + 1));
            }
            // then point it back...
            ba = reference;
            return true;
        }
        else return false;
    }
    
    // sorting the whole stuff based on the enum Sort
    public BankAccount[] sort(Sort s, SortType t)
    {
        // the reference getting to actually return as a BankAccount[]
        BankAccount[] ref = ba;
        
        // this is going to sort chronologically ascending or descending
        // only using one for loop this time :3
        for(int i = 0; i < ref.length; i++)
        {
            for(int j = 0; j < ref.length - i - 1; j++)
            {
                // find the sorting type xd
                String a = s == Sort.FIRST_NAME? ref[j].getFirstName().toLowerCase()
                         : s == Sort.MIDDLE_NAME? ref[j].getMiddleName().toLowerCase()
                         : s == Sort.LAST_NAME? ref[j].getLastName().toLowerCase()
                         : s == Sort.ACCOUNT_NUMBER? String.valueOf(ref[j].getAccountNumber())
                         : "";
                
                // find the sorting type too         
                String b = s == Sort.FIRST_NAME? ref[j + 1].getFirstName().toLowerCase()
                         : s == Sort.MIDDLE_NAME? ref[j + 1].getMiddleName().toLowerCase()
                         : s == Sort.LAST_NAME? ref[j + 1].getLastName().toLowerCase()
                         : s == Sort.ACCOUNT_NUMBER? String.valueOf(ref[j + 1].getAccountNumber())
                         : "";
                 
                // ascending or descending if case         
                if(t == SortType.SORT_ASCENDING? a.compareTo(b) > 0
                                               : a.compareTo(b) < 0)
                {
                    // bali-balihon ang mga classes to sort the way we wanted xd
                    // pp1 days ww.,.,.,.,,,..,
                    BankAccount temp = ref[j];
                    ref[j] = ref[j + 1];
                    ref[j + 1] = temp;
                }
            }
        }
        return ref;
    }
    
    public void replace(BankAccount b)
    {
        for(BankAccount bank : ba)
        {
            if(bank.getAccountNumber() == b.getAccountNumber())
            {
                bank = b;
            }
        }
    }
    
    // getting the length of the list..
    public int getLength()
    {
        return ba.length;
    }
    
    // same sa searchByName() na method, pero dapat exact number ang ibutang...
    // not @Deprecated anymore
    public BankAccount searchByNumber(long num)
    {
        for(BankAccount b : ba)
        {
            if(b != null)
            {
                if(b.getAccountNumber() == num)
                {
                    return b;
                }
            }
        }
        return null;
    }

    @Deprecated
    public void requestChange()
    {
        
    }
    
    // search sa index
    @Deprecated
    public BankAccount searchByIndex(int i)
    {
        if(i >= 0 && i <= size)
        {
            return ba[i];
        }
        else return null;
    }   
    
    // search sa pangalan
    @Deprecated
    public BankAccount searchByName(String name)
    {
        for(BankAccount b : ba)
        {
            if(b != null) // pag dili null ang array (para dili mag error)
            {
                // para ma search imohang pangalan na mas better (dili case-sensitive, ug masearch ug dali)
                if(b.getAccountName().toLowerCase().contains(name.toLowerCase()) || name.toLowerCase().contains(b.getAccountName().toLowerCase()))
                {
                    return b;
                }
            }
        }
        return null;
    }
    
    @Deprecated
    // update katong account nimo..
    public void updateAccount(BankAccountInterface b, String name, long num)
    {
        b.b.setAccountName(name);
        b.b.setAccountNumber(num);
    }
    
    // getting the ArrayList<BankAccount>
    public BankAccount[] getList()
    {
        return this.ba;
    }
}