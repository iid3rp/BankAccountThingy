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
            container.setSize(new Dimension(width,(int) ((height + 1.5) * b.getLength())));
            container.setPreferredSize(new Dimension(width, (int) ((height + 1.5) * b.getLength())));
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
        for(BankAccount2 bank : ba.ba)
        {
            // if condition with 5 searching-type-containing method algorithms:
            if((bank.getFirstName().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                query.contains(bank.getFirstName().toLowerCase().trim())) ||

               (bank.getMiddleName().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                query.contains(bank.getMiddleName().toLowerCase().trim())) ||

               (bank.getLastName().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                query.contains(bank.getLastName().toLowerCase().trim())) ||

               (bank.getAccountName().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                query.contains(bank.getAccountName().toLowerCase().trim())) ||

               (bank.getAccountNumber() + "").contains(query))

            {
                // making sure the size will accurately affect the whole list each search, and not crop one BankAccountInterface...
                container.setSize(new Dimension(1030, BankAccountInterface.HEIGHT * (index + 1)));
                container.setPreferredSize(new Dimension(1030, BankAccountInterface.HEIGHT * (index + 1)));
                BankAccountInterface bankInterface = new BankAccountInterface(frame,bank, this);
                bankInterface.setBounds(0, ((BankAccountInterface.HEIGHT * index++)), BankAccountInterface.WIDTH, BankAccountInterface.HEIGHT);
                container.add(bankInterface);
            }
        }
        // else if nothing was searched from the given query
        if(index == 0)
        {
            setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            container.setSize(new Dimension(1030, 700));
            container.setPreferredSize(new Dimension(1030, 700));
            container.add(createNotFound(-1, 200, 60, Font.BOLD, "No Bank Account Found!"));
            container.add(createNotFound(-1, 300, 20, Font.PLAIN, "Maybe search with something else?"));
            setViewportView(container);
        }
        container.repaint();
        container.validate();
        repaint();
        validate();
    }

    public void requestAdd(BankAccount2 b)
    {
        ba.add(b);
        restore();
    }

    public void requestEdit(BankAccount2 b)
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

    private JLabel createNotFound(int x, int y, int size, int font, String text)
    {
        JLabel label = new JLabel();
        label.setForeground(Color.BLACK);
        label.setText(text);
        label.setFont(new Font("Segoe UI", font, size));
        label.setBounds(x, y, width, height);
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        if(x == -1)
        {
            if(y == -1)
            {
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setBounds((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
            }
            else
            {
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBounds((getWidth() / 2) - (width / 2), y, width, height);
            }
        }
        else
        {
            if(y == -1)
            {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setBounds((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
            }
            else
            {
                label.setBounds(x, y, width, height);
            }

        }

        return label;
    }

    @Intention(reason = "Different method purposes")
    public void requestDeposit(BankAccount2 b)
    {
        requestEdit(b);
    }

    @Intention(reason = "Different method purposes")
    public void requestWithdraw(BankAccount2 b)
    {
        requestEdit(b);
    }


    public void requestRemove(BankAccount2 b)
    {
        ba.removeBankAccount(b);
        restore();
    }

    public void addComponents()
    {
        int index = 0; // iterator
        if(ba.ba != null)
        {
            for(BankAccount2 bank : ba.sort(currentSort, currentSortType))
            {
                BankAccountInterface bankInterface = new BankAccountInterface(frame, bank, this);
                bankInterface.setBounds(0, (BankAccountInterface.HEIGHT * index++), bankInterface.getWidth(), bankInterface.getHeight());
                container.add(bankInterface);
            }
        }
    }
    
    public BankAccount2 replaceAccount(BankAccount2 b)
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
        container.setSize(new Dimension(width, (height) * ba.getLength()));
        container.setPreferredSize(new Dimension(width, (height) * ba.getLength()));
        // add the components to the panel to be put into the scrollPane...
        addComponents();
        setViewportView(container);
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