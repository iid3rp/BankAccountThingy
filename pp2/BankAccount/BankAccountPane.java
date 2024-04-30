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
import java.awt.event.*;
import java.io.IOException;

public class BankAccountPane extends JPanel
{
    public BankAccountListPane pane;

    @Intention(isPublic = false, design = "reference pointing...")
    private InitialFrame frame;
    public JPanel info;
    public JLabel titleList;
    public JTextField search;
    public JLabel closeBank;
    public JLabel closeApplication;
    public JComboBox<String> sortTypes;

    @Intention(design = "theres such thing that  a pane must have to be the last" +
            "container to be initialized such that the other components will be used if" +
            "there will be a new/opened bank somehow..." +

            "REFER ON LINES 54-55!")
    public BankAccountPane(InitialFrame frame, BankAccountList list)
    {
        super();
        initializeComponent(frame);
        this.frame = frame;
        info = createInfo();

        //JLabels for the header
        titleList = createTitleList(list.getTitle());
        search = createSearch();
        closeBank = createCloseBank();
        closeApplication = createCloseApp();
        sortTypes = createSorting();

        info.add(titleList);
        info.add(search);
        info.add(closeBank);
        info.add(closeApplication);
        info.add(sortTypes);

        pane = createPane(list, frame);
        pane.setLocation(0, 40);

        add(info);
        add(pane);
    }

    public BankAccountPane()
    {
        super();
    }

    private BankAccountListPane createPane(BankAccountList b, InitialFrame frame)
    {
        if(b != null)
        {
            BankAccountListPane listPane = new BankAccountListPane(this, b, frame, Sort.LAST_NAME, SortType.SORT_ASCENDING);
            this.search.setEnabled(false);
            this.sortTypes.setEnabled(false);
            return listPane;
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
        searchBar.setBounds(300, 5, 300, 30);
        searchBar.setText("[/] to Search   ");
        searchBar.setEditable(true);
        searchBar.setFocusable(false);
        searchBar.setEnabled(false);
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
                if(search.isEnabled())
                {
                    search.setEditable(true);
                    search.setFocusable(true);
                    searchBar.setText(""); // Set back
                    search.requestFocus();
                    pane.restore();
                }
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
        label.setText("Close Bank");
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
                if(confirmCloseBank())
                {
                    frame.panel.remove(frame.contentPanel);
                    frame.contentPanel = frame.createContentPanel();
                    frame.contentPanel.setLocation(200, 0);
                    frame.panel.add(frame.contentPanel);
                    frame.contentPanel.repaint();
                    frame.contentPanel.validate();
                    frame.menu.remove(frame.addAccount);
                    frame.menu.remove(frame.deposit);
                    frame.menu.remove(frame.withdraw);
                    frame.menu.remove(frame.interest);
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
            }

            private boolean confirmCloseBank()
            {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to close this bank?",
                        "Closing Bank",
                        JOptionPane.YES_NO_OPTION
                );
                return result == JOptionPane.YES_OPTION;
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

    public JComboBox<String> createSorting()
    {
        String[] choices = {"Sort First Name A-Z", "Sort First Name Z-A",
                "Sort Middle Name A-Z", "Sort Middle Name Z-A",
                "Sort Last Name A-Z", "Sort Last Name Z-A",
                "Sort Account Number 0-9", "Sort Account Number 9-0"};
        JComboBox<String> box = new JComboBox<>(choices);
        box.setLayout(null);
        box.setBackground(Color.white);
        box.setEnabled(false);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        FontMetrics metrics = getFontMetrics(box.getFont());
        int width = metrics.stringWidth(choices[7]);
        int height = metrics.getHeight();
        box.setBounds(650, 10, width, height);
        box.addActionListener(e ->
        {
            assert box.getSelectedItem() == null;
            String choice = box.getSelectedItem().toString();
            switch(choice)
            {
                case "Sort First Name A-Z":
                    pane.setCurrentSort(Sort.FIRST_NAME);
                    pane.setCurrentSortType(SortType.SORT_ASCENDING);
                    break;
                case "Sort First Name Z-A":
                    pane.setCurrentSort(Sort.FIRST_NAME);
                    pane.setCurrentSortType(SortType.SORT_DESCENDING);
                    break;
                case "Sort Middle Name A-Z":
                    pane.setCurrentSort(Sort.MIDDLE_NAME);
                    pane.setCurrentSortType(SortType.SORT_ASCENDING);
                    break;
                case "Sort Middle Name Z-A":
                    pane.setCurrentSort(Sort.MIDDLE_NAME);
                    pane.setCurrentSortType(SortType.SORT_DESCENDING);
                    break;
                case "Sort Last Name A-Z":
                    pane.setCurrentSort(Sort.LAST_NAME);
                    pane.setCurrentSortType(SortType.SORT_ASCENDING);
                    break;
                case "Sort Last Name Z-A":
                    pane.setCurrentSort(Sort.LAST_NAME);
                    pane.setCurrentSortType(SortType.SORT_DESCENDING);
                    break;
                case "Sort Account Number 0-9":
                    pane.setCurrentSort(Sort.ACCOUNT_NUMBER);
                    pane.setCurrentSortType(SortType.SORT_ASCENDING);
                    break;
                case "Sort Account Number 9-0":
                    pane.setCurrentSort(Sort.ACCOUNT_NUMBER);
                    pane.setCurrentSortType(SortType.SORT_DESCENDING);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
            pane.restore();
        });
        return box;
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
