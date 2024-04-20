package BankAccountThingy.pp2.BankAccount;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

import java.awt.*;
import javax.swing.*;

public class BankAccountListPane extends JScrollPane
{
    private static final int width = BankAccountInterface.WIDTH;
    private static final int height = BankAccountInterface.HEIGHT;

    public BankAccountList ba;
    private JPanel container;
    private int size;
    
    public BankAccountListPane(BankAccountList b)
    {
        super();
        size = b.getLength();
        ba = new BankAccountList(b);
        initializeComponent();
        container.setSize(new Dimension(width, ((height+ 1) * b.getLength()) + (2 * b.getLength()) + 1));
        
        // you need to get the panel's preferred size "daw" because that's going
        // to be the reference dimension of the JScrollPane (which is weird but whatever)
        // xd - derp
        container.setPreferredSize(new Dimension(width, ((height + 1) * b.getLength()) + (2 * b.getLength()) + 1));
        //
        
        // add components into the container here :3
        addComponents();
        
        
        setViewportView(container);   
        setSize(new Dimension(1030, 720));
        setDoubleBuffered(true);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addMouseWheelListener(e ->
        {
            // Increase scroll sensitivity by multiplying the scroll distance
            int unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
        });
    }
    
     // default constructor
    @Deprecated
    public BankAccountListPane()
    {
        size = 0;
    }
    
    public void initializeComponent()
    {
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setBackground(new Color(200, 200, 200));
        container.setDoubleBuffered(true);
    }

    @Intention(reason = "getter of the BankAccountList: uses within deposit and withdraw...")
    public BankAccountList getBankList()
    {
        return ba;
    }
    
    // search query,, diri ang process sa hybrid searching..
    public void search(String query)
    {
        container.removeAll();
        int index = 0; 
        for(BankAccount bank : ba.getList())
        {
            if((bank.getFirstName().toLowerCase().trim().contains(query.toLowerCase().trim()) || query.contains(bank.getFirstName().toLowerCase().trim())) || 
               (bank.getMiddleName().toLowerCase().trim().contains(query.toLowerCase().trim()) || query.contains(bank.getMiddleName().toLowerCase().trim())) ||
               (bank.getLastName().toLowerCase().trim().contains(query.toLowerCase().trim()) || query.contains(bank.getLastName().toLowerCase().trim())) ||
               ((bank.getAccountNumber() + "").contains(query))) 
            {
                container.setPreferredSize(new Dimension(1030, 104 * index + 1));
                BankAccountInterface bankInterface = new BankAccountInterface(bank, this);
                bankInterface.setBounds(0, (101 * index), 1030, 100);
                bankInterface.repaint();
                bankInterface.validate();
                container.add(bankInterface);  
                index++;
            }
        }
        container.repaint();
        container.validate();
        repaint();
        validate();


    }

    public void requestAdd(BankAccount b)
    {
        ba.add(b);
        restore();
    }

    public void requestEdit(BankAccount b)
    {
        for(int i = 0; i < ba.ba.length; i++)
        {
            if(ba.ba[i].getAccountNumber() == b.getAccountNumber())
            {
                ba.ba[i] = b;
            }
        }
        restore();
    }

    @Intention(reason = "Different method purposes")
    public void requestDeposit(BankAccount b)
    {
        requestEdit(b);
    }

    @Intention(reason = "Different method purposes")
    public void requestWithdraw(BankAccount b)
    {
        requestEdit(b);
    }


    public void requestRemove(BankAccount b)
    {
        ba.removeBankAccount(b);
        restore();
    }

    public void addComponents()
    {
        int index = 0; // iterator
        for(BankAccount bank : ba.ba)
        {
            BankAccountInterface intf = new BankAccountInterface(bank, this);
            intf.setBounds(0, ((intf.getHeight() + 1) * index++), intf.getWidth(), intf.getHeight());
            container.add(intf);
        }
    }
    
    public void replaceAccount(BankAccount b)
    {
        for(int i = 0; i < ba.ba.length; i++)
        {
            if(b.getAccountNumber() == ba.ba[i].getAccountNumber())
            {
                ba.ba[i] = b;
            }
        }
        restore();
    }
    
    public void restore()
    {
        container.removeAll();
        container.setSize(new Dimension(width, ((height + 1) * ba.getLength()) + (2 * ba.getLength()) + 1));
        container.setPreferredSize(new Dimension(width, ((height + 1) * ba.getLength()) + (2 * ba.getLength()) + 1));
        // add the components to the panel to be put into the scrollPane...
        addComponents();
        container.validate();
        validate();
        System.out.println("restored");

    }

    @Deprecated
    public JScrollPane getPane()
    {
        return this;
    }

    @Deprecated
    public int getLength()
    {
        return size;
    }

    @Deprecated
    public void setSize(int size)
    {
        this.size = size;
    }
}