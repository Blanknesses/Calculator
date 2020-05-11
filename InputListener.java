package GUI.Calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;

import static java.lang.Math.max;

public class InputListener implements ActionListener {
    private TextArea textArea;
    private TextArea historyArea;
    //指向最近一次匹配符号的下标
    private int point;
    //识别计算是否完成
    private boolean flag = false;
    //识别当前小数点是否可输入
    private boolean dotFlag = true;
    //标记当前是否可以输入数字,true表示可输入数字
    private boolean numInputFlag = true;
    //储存用户输入小数点后位数
    private int size;
    //储存用户输入小数点后最大位数,默认至少保留6位作为除法精确度
    private int maxSize;
    //储存（数量，保证左右括号匹配
    private int openParenNum;
    private ArrayDeque<BigDecimal> numStack = new ArrayDeque();
    private ArrayDeque<String> charStack = new ArrayDeque();
    public InputListener(TextArea textArea, TextArea historyArea){
        this.textArea = textArea;
        this.historyArea = historyArea;
        point = 0;
        size = 0;
        maxSize = 6;
        openParenNum = 0;
    }

    /**
     * 每次输入符号时，将之前的数字转化为BigDecimal存入栈中
     * @return 成功存入返回true，否则返回false
     */
    public boolean putNumToStack(){
        String text = textArea.getText();
        //连续输入运算符号

        if (!getLastChar().matches("[0-9]") && text.length() == point){
            if (text.isEmpty())
                return false;
            textArea.setText(text.substring(0, point - 1));
            point--;
            return true;
        }
        //数字存入栈
        else {
            String inputNum = text.substring(point);
            point = point + inputNum.length();
            numStack.addLast(new BigDecimal(inputNum));
            return true;
        }
    }

    /**
     * 新进入操作符为加减操作
     * @param c 字符栈栈顶操作符+或-
     * @return 当除数为0时返回false
     */
    public boolean calculate(String c){
        //数字栈中存有2个以上数字，且字符栈栈顶字符为+
        if (numStack.size() > 1 && c == "+"){
            numStack.addLast(numStack.pollLast().add(numStack.pollLast()));
        }
        //数字栈中存有2个以上数字，且字符栈栈顶字符为-
        else if (numStack.size() > 1 && c == "-"){
            BigDecimal minus = numStack.pollLast();
            numStack.addLast(numStack.pollLast().subtract(minus));
        }
        else if (numStack.size() > 1 && c == "*"){
            numStack.addLast(numStack.pollLast().multiply(numStack.pollLast()));
            calculate(charStack.pollLast());
        }
        else if (numStack.size() > 1 && c == "/"){
            BigDecimal divisor = numStack.pollLast();
            if (divisor.equals(new BigDecimal("0"))){
                textArea.setText("ERROR");
                return false;
            }
            else {
                numStack.addLast(numStack.pollLast().divide(divisor, maxSize, RoundingMode.UP));
                calculate(charStack.pollLast());
            }
        }
        else if (c == "("){
            charStack.addLast("(");
        }
        return true;
    }

    /**
     * 检验当前TextArea内容是否为ERROR或者是否计算结束
     * @return true表示存在运算错误或运算结束
     */
    public boolean checkErrorOrEnd(){
        if (textArea.getText().equals("ERROR") || flag) {
            textArea.setText("");
            numStack.clear();
            charStack.clear();
            point = 0;
            flag = false;
            return true;
        }
        else
            return false;
    }

    /**
     * 专属运算符的checkErrorOrEnd方法，在使用=号获取结果后若继续输入运算符则继续运算
     * @return 返回值与checkErrorOrEnd方法同理
     */
    public boolean charCheckErrorOrEnd() {
        if (flag){
            charStack.clear();
            point = 0;
            flag = false;
            return false;
        }
        else
            return checkErrorOrEnd();
    }

    public String getLastChar(){
        String text = textArea.getText();
        if (text.isEmpty())
            return "";
        return text.substring(text.length() - 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        switch (buttonName){
            case "1": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("1");
                if (!dotFlag)
                    size++;
            }break;
            case "2": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("2");
                if (!dotFlag)
                    size++;
            }break;
            case "3": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("3");
                if (!dotFlag)
                    size++;
            }break;
            case "4": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("4");
                if (!dotFlag)
                    size++;
            }break;
            case "5": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("5");
                if (!dotFlag)
                    size++;
            }break;
            case "6": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("6");
                if (!dotFlag)
                    size++;
            }break;
            case "7": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("7");
                if (!dotFlag)
                    size++;
            }break;
            case "8": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("8");
                if (!dotFlag)
                    size++;
            }break;
            case "9": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("9");
                if (!dotFlag)
                    size++;
            }break;
            case "0": {
                checkErrorOrEnd();
                if (numInputFlag)
                    textArea.append("0");
                if (!dotFlag)
                    size++;
            }break;
            case "+": {
                charCheckErrorOrEnd();
                if (getLastChar().equals(")") || putNumToStack()) {
                    calculate(charStack.pollLast());
                    charStack.addLast("+");
                    textArea.append("+");
                    point++;
                    dotFlag = true;
                    numInputFlag = true;
                    maxSize = max(maxSize, size);
                }
            }break;
            case "-": {
                charCheckErrorOrEnd();
                if (getLastChar().equals(")") || putNumToStack()) {
                    calculate(charStack.pollLast());
                    charStack.addLast("-");
                    textArea.append("-");
                    point++;
                    dotFlag = true;
                    numInputFlag = true;
                    maxSize = max(maxSize, size);
                }
            }break;
            case "*": {
                charCheckErrorOrEnd();
                if (getLastChar().equals(")") || putNumToStack()){
                    charStack.addLast("*");
                    textArea.append("*");
                    point++;
                    dotFlag = true;
                    numInputFlag = true;
                    maxSize = max(maxSize, size);
                }
            }break;
            case "/": {
                charCheckErrorOrEnd();
                if (getLastChar().equals(")") || putNumToStack()){
                    charStack.addLast("/");
                    textArea.append("/");
                    point++;
                    dotFlag = true;
                    numInputFlag = true;
                    maxSize = max(maxSize, size);
                }
            }break;
            case ".": {
                if (dotFlag && getLastChar().matches("\\d")) {
                    textArea.append(".");
                    dotFlag = false;
                }
            }break;
            case "=": {
                checkErrorOrEnd();
                //若输入等号时，左右括号不匹配，自动补充右括号
                if (getLastChar().equals(")") || putNumToStack()) {
                    while (openParenNum > 0) {
                        while (charStack.getLast() != "(")
                            calculate(charStack.pollLast());
                        openParenNum--;
                        textArea.append(")");
                        charStack.pollLast();
                    }
                    while (!charStack.isEmpty())
                        calculate(charStack.pollLast());
                    flag = true;
                    historyArea.append(textArea.getText() + "=" + numStack.getLast().toString() + "\n");
                    textArea.setText(numStack.pollLast().toString());
                }
            }break;
            case "C": {
                flag = true;
                checkErrorOrEnd();
            }break;
            case "Del": {
                if (!checkErrorOrEnd()) {
                    String text = textArea.getText();
                    String textEnd = text.substring(text.length() - 1);
                    //仅对数字进行删除，因为只有在输入运算符号时才会将上一个数字存入数字栈
                    //故不需操作point指针
                    if (!text.isEmpty() && textEnd.matches("\\d")) {
                        textArea.setText(text.substring(0, text.length() - 1));
                    }
                }
            }break;
            case "(": {
                checkErrorOrEnd();
                if (textArea.getText().isEmpty() || !getLastChar().matches("[0-9.]")){
                    charStack.addLast("(");
                    //第一个输入为（或连续输入（，指针后移一位
                    point ++;
                    openParenNum ++;
                    textArea.append("(");
                }
            }break;
            case ")": {
                checkErrorOrEnd();
                if (openParenNum > 0 && getLastChar().matches("[0-9)]")){
                    if (getLastChar().equals(")") || putNumToStack()){
                        while (charStack.getLast() != "(")
                            calculate(charStack.pollLast());
                    }
                    point ++;
                    openParenNum --;
                    charStack.pollLast();
                    numInputFlag = false;
                    textArea.append(")");
                }
            }break;
            /*case "π": {
                checkErrorOrEnd();
                if (!getLastChar().matches("[0-9)]")){
                    //Math.PI = 3.14159265358979323846,存的double类型
                    numStack.addLast(new BigDecimal("3.14159265358979323846"));
                    textArea.append("π");
                    point ++;
                    numInputFlag = false;
                }
            }break;*/
        }
    }
}
