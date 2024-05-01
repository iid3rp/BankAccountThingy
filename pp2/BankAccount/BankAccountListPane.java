package BankAccountThingy.pp2.BankAccount;
import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.Utils.Sort;
import BankAccountThingy.pp2.BankAccount.Utils.SortType;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
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
    private boolean nothing;

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
            container.setSize(new Dimension(width, ((height) * b.getLength()) + b.getLength()));
            container.setPreferredSize(new Dimension(width, ((height) * b.getLength()) + b.getLength()));
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

        if(ba.ba != null)
        {
            for(BankAccount2 bank : ba.ba)
            {
                // if condition with 4 searching-type-containing method algorithms:
                if((bank.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                    query.contains(bank.getFirstName().toLowerCase()) ||

                   (bank.getMiddleName().toLowerCase().contains(query.toLowerCase()) ||
                    query.contains(bank.getMiddleName().toLowerCase())) ||

                   (bank.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                    query.contains(bank.getLastName().toLowerCase())) ||

                   (bank.getAccountNumber() + "").contains(query)))

                {
                    // making sure the size will accurately affect the whole list each search, and not crop one BankAccountInterface...
                    container.setSize(new Dimension(1030, ((BankAccountInterface.HEIGHT) * (index + 1)) + index));
                    container.setPreferredSize(new Dimension(1030, ((BankAccountInterface.HEIGHT) * (index + 1)) + index));
                    BankAccountInterface bankInterface = new BankAccountInterface(frame,bank, this);
                    bankInterface.setBounds(0, ((BankAccountInterface.HEIGHT * index++)), BankAccountInterface.WIDTH, BankAccountInterface.HEIGHT);
                    container.add(bankInterface);
                }
            }
            if(index == 0)
            {
                setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
                container.setSize(new Dimension(1030, 700));
                container.setPreferredSize(new Dimension(1030, 700));
                container.add(createNotFound(-1, 100, 60, Font.BOLD, "No Bank Account Found!"));
                container.add(createNotFound(-1, 200, 20, Font.PLAIN, "Maybe search with something else?"));
                container.add(addBankDesign());
                setViewportView(container);
            }
        }
        container.repaint();
        container.validate();
        repaint();
        validate();
    }

    public JLabel addBankDesign()
    {
        BufferedImage x;
        try {
            x = ImageIO.read(Objects.requireNonNull(BankAccount2.class.getResource("Resources/bankie.png")));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(x, 0, 0, null);
            }
        };
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBounds((1080 / 2) - (x.getWidth() / 2) + 10, 300, x.getWidth(), x.getHeight());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
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
        label.setLayout(null);
        label.setText(text);
        label.setFont(new Font("Segoe UI", font, size));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        if(x == -1)
        {
            if(y == -1)
            {
                label.setBounds((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
            }
            else
            {
                label.setBounds((getWidth() / 2) - (width / 2), y, width, height);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        else
        {
            if(y == -1)
            {
                label.setBounds((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
                label.setVerticalAlignment(SwingConstants.CENTER);
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

    public void setCurrentSort(Sort currentSort)
    {
        this.currentSort = currentSort;
    }

    public void setCurrentSortType(SortType currentSortType)
    {
        this.currentSortType = currentSortType;
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
            if(frame.pane != null)
            {
                frame.pane.sortTypes.setEnabled(true);
                frame.pane.search.setEnabled(true);
            }
            frame.menu.add(frame.deposit);
            frame.menu.add(frame.withdraw);
            frame.menu.add(frame.interest);

            nothing = false;
            container.repaint();
            repaint();
            frame.menu.repaint();
        }
        else
        {
            container.add(createAddAccount());
            container.add(createEmptyList());
            container.add(addBankDesign());
            if(frame.pane != null)
            {
                frame.pane.sortTypes.setEnabled(false);
                frame.pane.search.setEnabled(false);
                frame.menu.remove(frame.deposit);
                frame.menu.remove(frame.withdraw);
                frame.menu.remove(frame.interest);
                nothing = true;
                container.repaint();
                repaint();
                frame.menu.repaint();
            }
        }
    }

    private JLabel createAddAccount()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("<html><u>Add an account to fill it up!</u></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds((1030 / 2) - (width / 2), 180, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                addBankAccount();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.black);
            }
        });
        return label;
    }

    private void addBankAccount()
    {
        frame.addBankAccount();
    }

    public JLabel createEmptyList()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("This bank seems empty!");
        label.setFont(new Font("Segoe UI", Font.BOLD, 55));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds((container.getWidth() / 2) - (width / 2), 100, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVisible(true);
        return label;
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
        container.setSize(new Dimension(width, (BankAccountInterface.HEIGHT * ba.getLength()) + (ba.getLength())));
        container.setPreferredSize(new Dimension(width, (BankAccountInterface.HEIGHT * ba.getLength()) + (ba.getLength())));
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