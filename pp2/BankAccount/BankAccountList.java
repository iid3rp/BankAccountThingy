package pp2.BankAccount;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BankAccountList
{
    // katong bank account na array
    private JScrollPane pane;
    private BankAccount[] ba;
    private JPanel container;
    private int size;
    
    public BankAccountList(BankAccount[] b)
    {
        size = b.length;
        ba = b;
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setBackground(Color.GRAY);
        container.setSize(new Dimension(1030, 104 * b.length));
        container.setPreferredSize(new Dimension(1030, 104 * b.length));
        
        int index = 0;
        // we put the components here instead
        for(BankAccount bank : b)
        {
            BankAccountInterface bankInterface = new BankAccountInterface(bank);
            bankInterface.setBounds(0, (101 * index++), 1030, 100);
            container.add(bankInterface);
        }
        
        pane = new JScrollPane(container);     
        pane.setSize(new Dimension(1030, 720));
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.addMouseWheelListener(new MouseWheelListener() 
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) 
            {
                // Increase scroll sensitivity by multiplying the scroll distance
                int unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
                JScrollBar verticalScrollBar = pane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });  
    }
    
     // default constructor
    public BankAccountList()
    {
        size = 0;
    }
    
    public boolean search(String queue)
    {
        container.removeAll();
        int index = 0; 
        for(BankAccount bank : ba)
        {
            if((bank.getFirstName().toLowerCase().trim().contains(queue.toLowerCase().trim()) || queue.contains(bank.getFirstName().toLowerCase().trim())) || 
               (bank.getMiddleName().toLowerCase().trim().contains(queue.toLowerCase().trim()) || queue.contains(bank.getMiddleName().toLowerCase().trim())) ||
               (bank.getLastName().toLowerCase().trim().contains(queue.toLowerCase().trim()) || queue.contains(bank.getLastName().toLowerCase().trim())) ||
               ((bank.getAccountNumber() + "").contains(queue))) 
            {
                container.setPreferredSize(new Dimension(1030, 104 * index + 1));
                BankAccountInterface bankInterface = new BankAccountInterface(bank);
                bankInterface.setBounds(0, (101 * index), 1030, 100);
                container.add(bankInterface);  
                index++;  
            }
        }
        container.validate();
        pane.validate();
        return true;
    }
    
    public boolean restore()
    {
        container.removeAll();
        int index = 0; 
        for(BankAccount bank : ba)
        {
            container.setPreferredSize(new Dimension(1030, 104 * index + 1));
            BankAccountInterface bankInterface = new BankAccountInterface(bank);
            bankInterface.setBounds(0, (101 * index), 1030, 100);
            container.add(bankInterface);   
            index++; 
        }
        container.validate();
        pane.validate();
        return true;
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
    
    public JScrollPane getPane()
    {
        return pane;
    }

    // search sa index
    public BankAccount searchByIndex(int i)
    {
        if(i >= 0 && i <= size)
        {
            return ba[i];
        }
        else return null;
    }   
    
    // search sa pangalan
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
    
    // same sa searchByName() na method, pero dapat exact number ang ibutang...
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
    
    // update katong account nimo..
    public void updateAccount(BankAccountInterface b, String name, long num)
    {
        b.b.setAccountName(name);
        b.b.setAccountNumber(num);
    }
    
    // getting the length of the list..
    public int getLength()
    {
        return ba.length;
    }
    
    // getting the ArrayList<BankAccount>
    public BankAccount[] getArray()
    {
        return this.ba;
    }
}