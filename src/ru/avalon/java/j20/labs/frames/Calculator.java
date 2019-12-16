package ru.avalon.java.j20.labs.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ru.avalon.java.j20.labs.core.Actions;

public class Calculator extends JFrame {
    private static final int GAP = 10;
    private static final GridLayout GRID = new GridLayout(1, 1);

    static {
        GRID.setVgap(GAP);
        GRID.setHgap(GAP);
    }

    private JLabel result = new JLabel("0");
//    private JButton result = new JButton("0");
    private JLabel memoryLabel = new JLabel("");
    private String memoryNum = "0";
    private Actions memoryAct = Actions.getResult;
    private Actions currentAct = Actions.getResult;
    //flag shows if the active number is integer
    private boolean isInt = true;
    //flag shows end of calculation, if true new calculation must be started
    private boolean calculated = false;
    //memory panel text to remember
    private String memLabelText = "";
    private String currentVal = "0";
    private String memoryVal = "0";
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();


    public Calculator() throws HeadlessException {
        super("Calculator");
        setSize(300, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initElems();
        setVisible(true);
    }

    private void initElems() {
        //init main panel, configure side padding and padding between rows
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        GridLayout gridAll = new GridLayout(7, 1);
        gridAll.setVgap(GAP);
        mainPanel.setLayout(gridAll);

        //add button rows
        mainPanel.add(makeMemoryRow());
        mainPanel.add(makeResultRow());
        mainPanel.add(makeRow("7", "8", "9", Actions.sum));
        mainPanel.add(makeRow("4", "5", "6", Actions.substruct));
        mainPanel.add(makeRow("1", "2", "3", Actions.multiply));
        mainPanel.add(makeRow("CE", "0", ".", Actions.division));
        mainPanel.add(makeRow(Actions.getResult));
        add(mainPanel);
    }

    /**
     * create four buttons row
     *
     * @return panel with four buttons
     */
    private JPanel makeRow(String button1, String button2, String button3, Actions button4) {
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
     *
     * @return panel with one button
     */
    private JPanel makeRow(Actions act) {
        JPanel row = new JPanel();
        row.setLayout(GRID);
        row.add(createButton(act));
        return row;
    }

    /**
     * create and configure result panel
     *
     * @return panel with result
     */
    private JPanel makeResultRow() {
        Font font = new Font("TimesRoman", Font.PLAIN, 30);
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        result.setFont(font);
//        result.setBorderPainted(false);
//        result.setOpaque(false);
//        result.setContentAreaFilled(false);
//        result.addActionListener(this::onResultClick);
        result.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                onResultClick(mouseEvent);
            }
        });
        JPanel row = new JPanel();
        row.setLayout(GRID);
        row.add(result);
        return row;
    }

    /**
     * create panel with previous actions
     *
     * @return panel with actions
     */
    private JPanel makeMemoryRow() {
        Font font = new Font("TimesRoman", Font.ITALIC, 10);
        memoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        memoryLabel.setFont(font);
        JPanel row = new JPanel();
        row.setLayout(GRID);
        row.add(memoryLabel);
        return row;
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setActionCommand(label);
        button.setContentAreaFilled(false);
        button.addActionListener(this::setValue);
        return button;
    }

    private JButton createButton(Actions action) {
        JButton button = new JButton(action.getActName());
        button.setActionCommand(action.getActName());
        button.setContentAreaFilled(false);
        button.addActionListener(this::execAction);
        return button;
    }

    /**
     * add digit to number by clicking digit button
     *
     * @param e digit button activation
     */
    private void setValue(ActionEvent e) {
        if (currentVal.length() > Actions.SCALE) {
            return;
        }
        if ("CE".equals(e.getActionCommand())) {
            updateMemoryPanel("");
            currentVal = "0";
            memoryVal = "0";
        } else if (".".equals(e.getActionCommand())) {
            if (isInt && !calculated) {
                currentVal += ".";
                isInt = false;
            }
        } else if ("0".equals(currentVal) || calculated) {
            if (calculated) {
                updateMemoryPanel("");
            }
            calculated = false;
            currentVal = e.getActionCommand();
            isInt = true;
        } else {
            currentVal += e.getActionCommand();
        }
        result.setText(currentVal);
    }

    private void execAction(ActionEvent e) {
        currentAct = Actions.getActionByName(e.getActionCommand());

        updateMemoryPanel(currentVal + " " + currentAct.getActName());

        if (memoryAct == Actions.getResult) {
            memoryVal = currentVal;
            memoryAct = currentAct;
            if (currentAct != Actions.getResult) {
                currentVal = "0";
                calculated = false;
            } else {
                calculated = true;
            }
        } else if (currentAct == Actions.getResult) {
            currentVal = memoryAct.getAction().act(currentVal, memoryVal);
            memoryAct = Actions.getResult;
            calculated = true;
        } else {
            memoryVal = memoryAct.getAction().act(currentVal, memoryVal);
            memoryAct = currentAct;
            currentVal = "0";
            calculated = false;
        }
        currentVal = cutLastZeroes(currentVal);
        result.setText(currentVal);
    }

    private String cutLastZeroes(String str) {
        if (str.length() < 2) {
            return str;
        }
        String lastCh = str.substring(str.length() - 1);
        while (lastCh.equals("0") || lastCh.equals(".")) {
            str = str.substring(0, str.length() - 1);
            lastCh = str.substring(str.length() - 1);
        }
        return str;
    }

    private void updateMemoryPanel(String str) {
        if ("".equals(str)) {
            memoryLabel.setText("");
        } else {
            memoryLabel.setText(memoryLabel.getText() + " " + str);
        }
    }

    private void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }

    private void onResultClick(MouseEvent mouseEvent) {
        String text = result.getText();
        if (!isBlank(text)) {
            copyToClipboard(text);
        }
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
