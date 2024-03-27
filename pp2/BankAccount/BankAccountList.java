package pp2.BankAccount;

public class BankAccountList
{
    public int size;
    public BankAccount[] ba;
    public BankAccountList()
    {
        size = 0;
    }
    
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
            for(int i = 0; i < ba.length; i++)
            {
                reference[i] = ba[i];
            }
            ba = reference;
        }
        ba[size - 1] = b;
        return true;    
    }
    
    // addition sa bank account sa array (but including index to specifically put it)
    public boolean add(int index, BankAccount b)
    {
        if(index >= 0 || index <= size)
        {
            // ibutang sa ang existing inputs first sa reference
            BankAccount[] reference = new BankAccount[++size];
            for(int i = 0; i < index; i++)
            {
                reference[i]  = ba[i];   
            }
            
            // and then add tong index based sa katong part
            reference[index] = b;
            
            // and then iterate the remaining section afterwards..
            for(int i = index; i < size - 1; i++)
            {
                reference[i + 1] = ba[i];
            }
            // then call the index after
            ba = reference;
            return true;
        }
        else return false;  
    }
    
    // removes the part...
    public boolean removeBankAccount(BankAccount b)
    {
        // mangita siya sa BankAccount, one by one, in order.
        for(int i = 0; i < size; i++)
        {
            // pag equal siya sa iyahang target removal, call the remove(int) method...
            if(ba[i].getAccountNumber() == b.getAccountNumber())
            {
                return removeBankAccount(i);
            }
        }
        return false; // Return false if BankAccount not found    
    }
    
    // much simpler than the remove(BankAccount)
    public boolean removeBankAccount(int index)
    {
        if(index >= 0 && index < size)
        {
            BankAccount[] reference = new BankAccount[--size];
            // iterate the first loops of the existing ones
            for(int i = 0; i < index; i++)
            {
                reference[i] = ba[i];
            }
            // then i-skip tong index na idelete, then iterate and add the rest
            for(int i = index + 1; i < size + 1; i++)
            {
                reference[i - 1] = ba[i];
            }
            // then point it back...
            ba = reference;
            return true;
        }
        else return false;
    }
    
    // getting the length of the list..
    public int getLength()
    {
        return ba.length;
    }
    
    // same sa searchByName() na method, pero dapat exact number ang ibutang...
    @Deprecated
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
