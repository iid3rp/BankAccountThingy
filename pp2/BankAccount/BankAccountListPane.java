package BankAccountThingy.pp2.BankAccount;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import BankAccountThingy.pp2.BankAccount.Dialogs.EditBankAccount;

public class BankAccountListPane extends JScrollPane
{
    // katong bank account na array
    private BankAccountInterface intf;
    public BankAccountList ba;
    private JPanel container;
    private int size;
    private int index = 0;
    
    public BankAccountListPane(BankAccountList b)
    {
        super();
        size = b.getLength();
        ba = new BankAccountList(b);
        initializeComponent();
        container.setSize(new Dimension(intf.WIDTH, ((intf.HEIGHT + 1) * b.getLength()) + (2 * b.getLength()) + 1));
        
        // you need to get the panel's preffered size "daw" because thats going
        // to be the reference dimension of the JScrollPane (which is weird but whateve)
        // xd - derp
        container.setPreferredSize(new Dimension(intf.WIDTH, ((intf.HEIGHT + 1) * b.getLength()) + (2 * b.getLength()) + 1));
        //
        
        // add components into the container here :3
        addComponents();
        
        
        setViewportView(container);   
        setSize(new Dimension(1030, 720));
        setDoubleBuffered(true);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addMouseWheelListener(new MouseWheelListener() 
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) 
            {
                // Increase scroll sensitivity by multiplying the scroll distance
                int unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
                JScrollBar verticalScrollBar = getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        });  
    }
    
     // default constructor
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
    
    // adding the list components here :3
    public void addList()
    {
    
    }
    
    // search query,, diri ang process sa hybrid searching..
    public boolean search(String query)
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
                BankAccountInterface bankInterface = new BankAccountInterface(bank);
                bankInterface.setBounds(0, (101 * index), 1030, 100);
                container.add(bankInterface);  
                index++;
            }
        }
        container.repaint();
        container.validate();
        repaint();
        validate();
        return true;
    }
    
    public boolean requestAdd(BankAccount b)
    {
        ba.add(b);
        restore();
        return true;
    }
    
    public void addComponents()
    {
        int index = 0; // iterator
        for(BankAccount bank : ba.ba)
        {
            intf = new BankAccountInterface(bank);
            intf.setBounds(0, ((intf.getHeight() + 1) * index++), intf.getWidth(), intf.getHeight());
            container.add(intf);
        }
    }
    
    public void replaceAccount(BankAccount b)
    {
        for(BankAccount bank : ba.ba)
        {
            if(bank.getAccountNumber() == (b.getAccountNumber()))
            {
                bank = new BankAccount(b); 
                restore();
                return;
            }
        }
    }
    
    public boolean restore()
    {
        container.removeAll();
        container.setSize(new Dimension(intf.WIDTH, ((intf.HEIGHT + 1) * ba.getLength()) + (2 * ba.getLength()) + 1));
        container.setPreferredSize(new Dimension(intf.WIDTH, ((intf.HEIGHT + 1) * ba.getLength()) + (2 * ba.getLength()) + 1));
        // add the components to the panel to be put into the scrollPane...
        addComponents();
        container.validate();
        validate();
        System.out.println("restored");
        return true;
    }
    
    public JScrollPane getPane()
    {
        return this;
    }
}