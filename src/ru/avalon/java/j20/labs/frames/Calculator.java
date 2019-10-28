package ru.avalon.java.j20.labs.frames;

import javax.swing.*;
import java.awt.*;

public class Calculator extends AbstractFrame {
    JLabel result = new JLabel("0");
    JPanel buttons = new JPanel();
    int elemHeight;

    public Calculator() throws HeadlessException {
        super("Calculator");
        initElems();
    }

    private void initElems() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setMinimumSize(new Dimension(20, 100));
        top.add(result);
        add(top, BorderLayout.PAGE_START);

        buttons.setLayout(new GridLayout(4, 4));
        initButtons();
        add(buttons, BorderLayout.CENTER);

        add(createButton("="), BorderLayout.PAGE_END);
    }

    private void initButtons() {
        buttons.add(createButton("0"));
        buttons.add(createButton("1"));
        buttons.add(createButton("2"));
        buttons.add(createButton("3"));
        buttons.add(createButton("4"));
        buttons.add(createButton("5"));
        buttons.add(createButton("6"));
        buttons.add(createButton("7"));
        buttons.add(createButton("9"));
        buttons.add(createButton("10"));
    }

    private int getButHeight() {
        return createButton("size").getHeight();
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setContentAreaFilled(false);
        return button;
    }
}
