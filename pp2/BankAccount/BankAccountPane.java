package BankAccountThingy.pp2.BankAccount;

import BankAccountThingy.InitialFrame;
import BankAccountThingy.pp2.BankAccount.StreamIO.BankMaker;
import BankAccountThingy.pp2.BankAccount.Utils.Intention;
import BankAccountThingy.pp2.BankAccount.Utils.Log;
import BankAccountThingy.pp2.BankAccount.Utils.SortType;
import BankAccountThingy.pp2.BankAccount.Utils.Sort;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class BankAccountPane extends JPanel
{
    public BankAccountListPane pane;

    @Intention(isPublic = false, design = "reference pointing...")
    private InitialFrame frame;
    private JPanel info;
    private JLabel titleList;
    public JTextField search;
    private JLabel closeBank;
    private JLabel closeApplication;

    public BankAccountPane(InitialFrame frame, BankAccountList list)
    {
        super();
        initializeComponent(frame);
        this.frame = frame;
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

    public BankAccountPane() {}

    private BankAccountListPane createPane(BankAccountList b, InitialFrame frame)
    {
        if(b != null)
        {
            return new BankAccountListPane(this, b, frame, Sort.LAST_NAME, SortType.SORT_ASCENDING);
        }
        else return new BankAccountListPane();
    }

    private void initializeComponent(InitialFrame frame)
    {
        setLayout(null);
        setBounds(0, 0, 1080, 720);
        setDoubleBuffered(true);
        setBackground(new Color(200, 200, 200));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                search.setFocusable(false);
                search.setText("[/] to Search   ");
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
        panel.setBounds(0, 0, 1080, 40);
        panel.setBackground(Color.WHITE);
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                frame.offset = e.getPoint();
                frame.isDragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                frame.isDragging = false;
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (frame.isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - frame.offset.x - 250;
                    int deltaY = currentMouse.y - frame.offset.y;

                    frame.setLocation(deltaX, deltaY);
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
        searchBar.setBounds(400, 5, 400, 30);
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
        label.setBounds(1080 - width - 30 - 100, 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.contentPanel.removeAll();
                frame.pane = null;
                frame.contentPanel.repaint();
                frame.contentPanel.validate();
                frame.repaint();
                frame.validate();
                try {
                    BankMaker.rewriteFile(frame.getReferenceFile(), pane.ba);
                    frame.logger.add(Log.CLOSE_BANK, pane.ba, null);
                }
                catch(IOException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setReferenceFile(null);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.BLACK);
            }
        });
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
        label.setBounds(1080 - width - 20, 10, width, height);
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(frame.confirmClose())
                {
                    try
                    {
                        BankMaker.rewriteFile(frame.getReferenceFile(), pane.ba);
                        frame.logger.add(Log.CLOSE_BANK, pane.ba, null);
                        frame.logger.add(Log.CLOSE_APPLICATION, null, null);
                        frame.logger.close();
                        System.out.println("fadkslakjd");
                    }
                    catch(IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.dispose();
                }
            }
        });
        return label;
    }
}
