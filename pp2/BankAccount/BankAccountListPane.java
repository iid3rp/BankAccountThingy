package pp2.BankAccount;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BankAccountListPane
{
    // katong bank account na array
    private JScrollPane pane;
    private BankAccountList ba;
    private JPanel container;
    private int size;
    
    public BankAccountListPane(BankAccountList b)
    {
        size = b.getLength();
        ba = new BankAccountList(b);
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setBackground(new Color(200, 200, 200));
        container.setSize(new Dimension(1030, 104 * b.getLength()));
        container.setPreferredSize(new Dimension(1030, 104 * b.getLength()));
        
        int index = 0;
        // we put the components here instead
        for(BankAccount bank : ba.getList())
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
    public BankAccountListPane()
    {
        size = 0;
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
        pane.repaint();
        pane.validate();
        return true;
    }
    
    public boolean restore()
    {
        container.removeAll();
        int index = 0; 
        for(BankAccount bank : ba.getList())
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
    
    public JScrollPane getPane()
    {
        return pane;
    }
}