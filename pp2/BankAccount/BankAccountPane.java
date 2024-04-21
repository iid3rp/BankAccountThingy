package BankAccountThingy.pp2.BankAccount;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;

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

    @Intention(reason = "accessing to the initialFrame and add listeners afterward.")
    private JPanel info;
    public @Intention JLabel titleList;
    public @Intention JTextField search;
    public @Intention JLabel closeBank;
    public @Intention JLabel closeApplication;

    public BankAccountPane(InitialFrame frame, BankAccountList list)
    {
        super();
        initializeComponent();
        reference = frame;
        pane = createPane(list);
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

    private BankAccountListPane createPane(BankAccountList b)
    {
        if(b != null)
        {
            b.ba = b.sort(BankAccountList.Sort.LAST_NAME, null);
            return new BankAccountListPane(b);
        }
        else return new BankAccountListPane();
    }

    private void initializeComponent()
    {
        setLayout(null);
        setBounds(0, 0, 1030, 720);
        setDoubleBuffered(true);
        setBackground(new Color(200, 200, 200));

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
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
                {
                    pane.search(searchBar.getText()); // Call this method whenever text is changed
                }
                else pane.restore();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                if(!(searchBar.getText().equals("[/] to Search   ") && searchBar.getText().isBlank() && !searchBar.getText().isEmpty()))
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
                reference.dispose();
            }
        });
        return label;
    }
}
