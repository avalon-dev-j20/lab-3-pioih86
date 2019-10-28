package ru.avalon.java.j20.labs.frames;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractFrame extends JFrame {
    public AbstractFrame(String title) throws HeadlessException {
        super(title);
        setVisible(true);
        setSize(300, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
