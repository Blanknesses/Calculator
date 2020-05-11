package GUI.Calculator;

import GUI.WindowListener;

import java.awt.*;

public class Calculator {
    private Frame frame = new Frame("Calculator");
    MenuBar menuBar = new MenuBar();
    private Menu file = new Menu("file");
    private Menu setting = new Menu("setting");
    private TextArea resultArea = new TextArea(1,30);
    private Panel resultPanel = new Panel();
    private Panel buttonsPanel = new Panel();
    private TextArea historyArea = new TextArea(6,20);
    private Panel historyPanel = new Panel();
    private Button[] numButtons = new Button[20];
    private String[] numButtonsLabel = {"(", ")", "=", "C", "1", "2", "3", "+", "4", "5",
            "6", "-", "7", "8" ,"9", "*", ".", "0", "Del", "/"};
    //文件的菜单选项
    private MenuItem open = new MenuItem("open");
    private MenuItem close = new MenuItem("close");
    //设置的菜单选项
    private MenuItem copy = new MenuItem("copy");
    private MenuItem paste = new MenuItem("paste");
    private InputListener inputListener = new InputListener(resultArea, historyArea);

    public void init(){
        menuBar.add(file);
        menuBar.add(setting);
        file.add(open);
        file.add(close);
        setting.add(copy);
        setting.add(paste);
        //frame.setMenuBar(menuBar);
        resultArea.setEditable(false);
        resultArea.setFont(new Font(null,0,30));
        resultPanel.add(resultArea);
        frame.add(resultPanel, BorderLayout.NORTH);
        frame.add(buttonsPanel);
        historyArea.setEditable(false);
        historyArea.setFont(new Font(null,0,20));
        historyPanel.add(historyArea);
        frame.add(historyPanel, BorderLayout.EAST);
        buttonsPanel.setLayout(new GridLayout(5,5,4,4));
        initNumButtons();
        frame.setResizable(false);
        //添加windows事件监听
        frame.addWindowListener(new WindowListener());
        //添加TextArea事件监听
        frame.pack();
        frame.setVisible(true);
    }

    public void initNumButtons(){
        for (int i = 0; i < numButtons.length; i++) {
            numButtons[i] = new Button(numButtonsLabel[i]);
            buttonsPanel.add(numButtons[i]);
            numButtons[i].addActionListener(inputListener);
        }
    }
}
