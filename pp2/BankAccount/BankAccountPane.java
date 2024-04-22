package BankAccountThingy.pp2.BankAccount;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankMaker;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
import BankAccountThingy.pp2.BankAccount.Utils.SortType;
import BankAccountThingy.pp2.BankAccount.Utils.Sort;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BankAccountPane extends JPanel
{
    public BankAccountListPane pane;

    @Intention(isPublic = false, design = "reference pointing...")
    private InitialFrame reference;
    private JPanel info;
    private JLabel titleList;
    private JTextField search;
    private JLabel closeBank;
    private JLabel closeApplication;

    public BankAccountPane(InitialFrame frame, BankAccountList list)
    {
        super();
        initializeComponent(frame);
        reference = frame;
        pane = createPane(list, frame);
        pane.setLocation(0, 40);
        info = createInfo();

        //JLabels for the header
        titleList = createTitleList(list.getTitle());
        search = createSearch();
        closeBank = createCloseBank();
        closeApplication = createCloseApp();

        info.add(titleList);
        info.add(search);
        info.add(closeBank);
        info.add(closeApplication);

        add(info);
        add(pane);
    }

    private BankAccountListPane createPane(BankAccountList b, InitialFrame frame)
    {
        if(b != null)
        {
            return new BankAccountListPane(b, frame, Sort.LAST_NAME, SortType.SORT_ASCENDING);
        }
        else return new BankAccountListPane();
    }

    private void initializeComponent(InitialFrame frame)
    {
        setLayout(null);
        setBounds(0, 0, 1030, 720);
        setDoubleBuffered(true);
        setBackground(new Color(200, 200, 200));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.search.setFocusable(false);
                frame.search.setText("[/] to Search   ");
                pane.restore();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    frame.isDragging = true;
                    frame.offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    frame.isDragging = false;
                }
            }

        });
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (frame.isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - frame.offset.x - 250;
                    int deltaY = currentMouse.y - frame.offset.y;

                    setLocation(deltaX, deltaY);
                }
            }
        });
    }

    public JPanel createInfo()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1030, 40);
        panel.setBackground(Color.WHITE);
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                reference.offset = e.getPoint();
                reference.isDragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                reference.isDragging = false;
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (reference.isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - reference.offset.x - 250;
                    int deltaY = currentMouse.y - reference.offset.y;

                    reference.setLocation(deltaX, deltaY);
                }
            }
        });

        return panel;
    }

    public JLabel createTitleList(String title)
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText(title);
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());


        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 10, width, height);
        label.setVisible(true);
        return label;

    }

    public JTextField createSearch()
    {
        JTextField searchBar = new JTextField();

        searchBar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchBar.setBounds(300, 5, 400, 30);
        searchBar.setText("[/] to Search   ");
        searchBar.setEditable(true);
        searchBar.setFocusable(false);
        searchBar.setHorizontalAlignment(SwingConstants.RIGHT);

        searchBar.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }
        });

        searchBar.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                search.setEditable(true);
                search.setFocusable(true);
                searchBar.setText(""); // Set back
                search.requestFocus();
                pane.restore();
            }
        });
        searchBar.setVisible(true);

        return searchBar;
    }


    public JLabel createCloseBank()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("closeBank");
        label.setForeground(Color.BLACK);

        FontMetrics metrics = getFontMetrics(label.getFont());


        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(1030 - width - 30 - 100, 10, width, height);
        label.setVisible(true);
        return label;
    }


    public JLabel createCloseApp()
    {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setLayout(null);
        label.setText("X");
        label.setForeground(Color.RED);

        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(1030 - width - 20, 10, width, height);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(reference.confirmClose())
                {
                    BankMaker.rewriteFile(reference.getReferenceFile(), pane.ba);
                    reference.dispose();
                }
            }
        });
        return label;
    }
}
