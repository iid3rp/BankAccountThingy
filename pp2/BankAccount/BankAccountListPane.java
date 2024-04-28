package BankAccountThingy.pp2.BankAccount;
import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.Utils.Sort;
import BankAccountThingy.pp2.BankAccount.Utils.SortType;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

import java.awt.*;
import javax.swing.*;


public class BankAccountListPane extends JScrollPane
{
    private static final int width = BankAccountInterface.WIDTH;
    private static final int height = BankAccountInterface.HEIGHT;
    @Intention InitialFrame frame;

    private Sort currentSort = Sort.LAST_NAME;
    private SortType currentSortType = SortType.SORT_ASCENDING;

    public BankAccountList ba;
    private JPanel container;
    private int size;

    /**<editor-fold desc="Description">
     * you need to get the panel's preferred size "daw" because that's going
     * to be the reference dimension of the JScrollPane (which is weird but whatever)
     * xd - derp.
     * <p></p>
     * REFER THE CONSTRUCTOR BELOW
     *</editor-fold>
     * */
    public BankAccountListPane(BankAccountPane pane, BankAccountList b, InitialFrame frame, Sort sort, SortType type)
    {
        super();
        if(b != null)
        {
            size = b.getLength();
            ba = new BankAccountList(b);
            this.frame = frame;
            currentSort = sort;
            currentSortType = type;
            initializeComponent(frame, pane);
            container.setSize(new Dimension(width, ((height + 1) * b.getLength()) + (2 * b.getLength()) + 2));
            container.setPreferredSize(new Dimension(width, ((height + 1) * b.getLength()) + (2 * b.getLength()) + 2));
            // add components into the container here :3
            addComponents();


            setViewportView(container);
            getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
            setSize(new Dimension(width, 720));
            setDoubleBuffered(true);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            addMouseWheelListener(e ->
            {
                // Increase scroll sensitivity by multiplying the scroll distance
                double unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
                JScrollBar verticalScrollBar = getVerticalScrollBar();
                verticalScrollBar.setValue((int) (verticalScrollBar.getValue() + unitsToScroll));
            });
        }
    }
    
     // default constructor
    public BankAccountListPane()
    {
        size = 0;
    }
    
    public void initializeComponent(InitialFrame frame, BankAccountPane pane)
    {
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setBackground(Color.white);
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
        for(BankAccount bank : ba.ba)
        {
            if((bank.getFirstName().toLowerCase().trim().contains(query.toLowerCase().trim()) || query.contains(bank.getFirstName().toLowerCase().trim())) || 
               (bank.getMiddleName().toLowerCase().trim().contains(query.toLowerCase().trim()) || query.contains(bank.getMiddleName().toLowerCase().trim())) ||
               (bank.getLastName().toLowerCase().trim().contains(query.toLowerCase().trim()) || query.contains(bank.getLastName().toLowerCase().trim())) ||
               ((bank.getAccountNumber() + "").contains(query))) 
            {
                // making sure the size will accurately affect the whole list each search, and not crop one BankAccountInterface...
                container.setSize(new Dimension(1030, 104 * (index + 1) + 1));
                container.setPreferredSize(new Dimension(1030, 104 * (index + 1) + 1));
                BankAccountInterface bankInterface = new BankAccountInterface(frame,bank, this);
                bankInterface.setBounds(0, ((bankInterface.getHeight() + 1) * index++), bankInterface.getWidth(), bankInterface.getHeight());
                container.add(bankInterface);
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
        if(ba.ba != null)
        {
            for(BankAccount bank : ba.sort(currentSort, currentSortType))
            {
                BankAccountInterface bankInterface = new BankAccountInterface(frame, bank, this);
                bankInterface.setBounds(0, ((bankInterface.getHeight() + 1) * index++), bankInterface.getWidth(), bankInterface.getHeight());
                container.add(bankInterface);
            }
        }
    }
    
    public BankAccount replaceAccount(BankAccount b)
    {
        if(b != null)
        {
            for(int i = 0; i < ba.ba.length; i++)
            {
                if(b.getAccountNumber() == ba.ba[i].getAccountNumber())
                {
                    ba.ba[i] = b;
                }
            }
            restore();
            return b;
        }
        else return null;
    }
    
    public void restore()
    {
        container.removeAll();
        container.setSize(new Dimension(width, ((height + 1) * ba.getLength()) + (3 * ba.getLength()) + 1));
        container.setPreferredSize(new Dimension(width, ((height + 1) * ba.getLength()) + (3 * ba.getLength()) + 1));
        // add the components to the panel to be put into the scrollPane...
        addComponents();
        container.repaint();
        container.validate();
        repaint();
        validate();
        frame.pane.repaint();
        frame.pane.validate();
        System.out.println("restored " + ba.getLength());

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