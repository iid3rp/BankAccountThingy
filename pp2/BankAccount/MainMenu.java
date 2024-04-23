package BankAccountThingy.pp2.BankAccount;

import BankAccountThingy.InitialFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MainMenu extends JPanel
{
    InitialFrame frame;
    public MainMenu(InitialFrame frame)
    {
        super();
        this.frame = frame;
        initializeComponent();

    }

    private void initializeComponent()
    {
        setSize(new Dimension(1280, 720));
        setDoubleBuffered(true);
        setBackground(Color.WHITE);
        setAutoscrolls(true);
        addMouseListener(new MouseAdapter()
        {
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
}
