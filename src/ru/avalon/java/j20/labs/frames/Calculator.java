package ru.avalon.java.j20.labs.frames;

import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    private static final int GAP = 10;
    private static final GridLayout GRID = new GridLayout(1, 1);
    static {
        GRID.setVgap(GAP);
        GRID.setHgap(GAP);
    }

    JLabel result = new JLabel("0");

    public Calculator() throws HeadlessException {
        super("Calculator");
        setSize(250, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initElems();
        setVisible(true);
    }

    private void initElems() {
        //init main panel, configure side padding and padding between rows
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        GridLayout gridAll = new GridLayout(6, 1);
        gridAll.setVgap(GAP);
        mainPanel.setLayout(gridAll);

        //add button rows
        mainPanel.add(makeResultRow());
        mainPanel.add(makeRow("7", "8", "9", "+"));
        mainPanel.add(makeRow("4", "5", "6", "-"));
        mainPanel.add(makeRow("1", "2", "3", "*"));
        mainPanel.add(makeRow("CE", "0", ".", "/"));
        mainPanel.add(makeRow("="));

        add(mainPanel);
    }

    /**
     * create four buttons row
     * @return panel with four buttons
     */
    private JPanel makeRow(String button1, String button2, String button3, String button4){
        JPanel row = new JPanel();
        row.setLayout(GRID);
        row.add(createButton(button1));
        row.add(createButton(button2));
        row.add(createButton(button3));
        row.add(createButton(button4));
        return row;
    }

    /**
     * create one button row
     * @return panel with one button
     */
    private JPanel makeRow(String button){
        JPanel row = new JPanel();
        row.setLayout(GRID);
        row.add(createButton(button));
        return row;
    }

    /**
     * create and configure result panel
     * @return panel with result
     */
    private JPanel makeResultRow(){
        Font font = new Font("TimesRoman", Font.PLAIN, 30);
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        result.setFont(font);
        JPanel row = new JPanel();
        row.setLayout(GRID);
        row.add(result);
        return row;
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setContentAreaFilled(false);
        return button;
    }
}
