package pp2.BankAccount;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BankAccountList
{
    // katong bank account na array
    private JScrollPane pane;
    private BankAccountInterface[] ba;
    private JPanel container;
    private int size;
    
    public BankAccountList(BankAccount[] b)
    {
        size = b.length;
        
        // add a container to put stuff :3
        container = new JPanel(new GridLayout(0, 1, 0, 1));
        container.setLocation(0, 0);
        container.setPreferredSize(new Dimension(1030, 115 * b.length));
        
        // we put the components here instead
        for(BankAccount ba : b)
        {
            add(new BankAccountInterface(ba));
        }
        validate();
        
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
    
    public void validate()
    {
        for(BankAccountInterface b : ba)
        {
            container.add(b);
        }
    }
    
    // addition sa bank account sa array
    public boolean add(BankAccountInterface b)
    {
        if(ba == null)
        {
            ba = new BankAccountInterface[++size];
        }
        else
        {
            BankAccountInterface[] reference = new BankAccountInterface[++size];
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
    public boolean add(int index, BankAccountInterface b)
    {
        if(index >= 0 || index <= size)
        {
            // ibutang sa ang existing inputs first sa reference
            BankAccountInterface[] reference = new BankAccountInterface[++size];
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
    public boolean removeBankAccount(BankAccountInterface b)
    {
        // mangita siya sa BankAccount, one by one, in order.
        for(int i = 0; i < size; i++)
        {
            // pag equal siya sa iyahang target removal, call the remove(int) method...
            if(ba[i].b.getAccountNumber() == b.b.getAccountNumber())
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
            BankAccountInterface[] reference = new BankAccountInterface[--size];
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
    public BankAccountInterface searchByIndex(int i)
    {
        if(i >= 0 && i <= size)
        {
            return ba[i];
        }
        else return null;
    }   
    
    // search sa pangalan
    public BankAccountInterface searchByName(String name)
    {
        for(BankAccountInterface b : ba)
        {
            if(b != null) // pag dili null ang array (para dili mag error)
            {
                // para ma search imohang pangalan na mas better (dili case-sensitive, ug masearch ug dali)
                if(b.b.getAccountName().toLowerCase().contains(name.toLowerCase()) || name.toLowerCase().contains(b.b.getAccountName().toLowerCase()))
                {
                    return b;
                }
            }
        }
        return null;
    }
    
    // same sa searchByName() na method, pero dapat exact number ang ibutang...
    public BankAccountInterface searchByNumber(long num)
    {
        for(BankAccountInterface b : ba)
        {
            if(b != null)
            {
                if(b.b.getAccountNumber() == num)
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
    public BankAccountInterface[] getArray()
    {
        return this.ba;
    }
}