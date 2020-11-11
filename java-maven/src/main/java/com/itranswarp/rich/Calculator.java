package com.itranswarp.rich;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener,KeyListener{  //继承监听事件接口
    static final int WIDTH=300;  //Frame窗口宽300
    static final int HEIGHT=400;  //Frame窗口高400
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");  //JDK自带的类可以实现调用JS的功能，可以实现执行字符串中的运算公式的功能
    private JFrame frame;
    private JPanel Panel;
    private JTextField resultText;
    private String[] KEYS={"mc","ms","mr","m+","m-","AC","+/-","/","*","<—","7","8","9","-","4","5","6","+","1","2","3","0",".","="};
                            //0  1     2   3     4    5   6     7   8   9    10  11  12  13  14   15 16  17  18  19  20  21  22  23
    private JButton keys[]=new JButton[KEYS.length];
    private double resultNum=0.0;
    public Calculator() {
        super("计算器");
        Panel=new JPanel();
        Panel.setLayout(null);  //使用空布局
        resultText=new JTextField("0");
        resultText.setBounds(5, 8, 285, 50);
        resultText.setColumns(25);
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setEditable(false);
        resultText.setAutoscrolls(true);//自动换行
        resultText.setBackground(Color.white);
        for(int i=0;i<KEYS.length;i++) {
            keys[i]=new JButton(KEYS[i]);
        }
        keys[0].setBounds(5, 70, 52, 40);  //自定义控件位置（空布局）
        keys[1].setBounds(62,70,52,40);
        keys[2].setBounds(121,70,52,40);
        keys[3].setBounds(180,70,52,40);
        keys[4].setBounds(239,70,52,40);
        keys[5].setBounds(5,125,52,40);
        keys[6].setBounds(62,125,52,40);
        keys[6].setFont(new Font("宋体", Font.PLAIN, 12));
        keys[7].setBounds(121,125,52,40);
        keys[7].setFont(new Font("宋体", Font.PLAIN, 16));
        keys[8].setBounds(180,125,52,40);
        keys[8].setFont(new Font("宋体", Font.PLAIN, 16));
        keys[9].setBounds(239,125,52,40);
        keys[9].setFont(new Font("宋体", Font.PLAIN, 12));
        keys[10].setBounds(5,180,60,40);
        keys[11].setBounds(80,180,60,40);
        keys[12].setBounds(153,180,60,40);
        keys[13].setBounds(230,180,60,40);
        keys[13].setFont(new Font("宋体", Font.PLAIN, 16));
        keys[14].setBounds(5,235,60,40);
        keys[15].setBounds(80,235,60,40);
        keys[16].setBounds(153,235,60,40);
        keys[17].setBounds(230,235,60,40);
        keys[17].setFont(new Font("宋体", Font.PLAIN, 16));
        keys[18].setBounds(5,290,60,40);
        keys[19].setBounds(80,290,60,40);
        keys[20].setBounds(153,290,60,40);
        keys[21].setBounds(5,345,135,40);
        keys[22].setBounds(153,345,60,40);
        keys[22].setFont(new Font("宋体", Font.PLAIN, 16));
        keys[23].setBounds(230,290,60,95);
        for(int i=0;i<5;i++) {
            keys[i].setBackground(Color.black);  //设置控件背景色
            keys[i].setForeground(Color.WHITE);
        }
        for(int i=5;i<10;i++) {
            keys[i].setBackground(Color.gray);
            keys[i].setForeground(Color.WHITE);
        }
        keys[13].setBackground(Color.gray);
        keys[13].setForeground(Color.WHITE);
        keys[17].setBackground(Color.gray);
        keys[17].setForeground(Color.WHITE);
        keys[23].setBackground(Color.red);
        keys[23].setForeground(Color.WHITE);
        for(int i=0;i<KEYS.length;i++) {
            keys[i].addActionListener(this);  //添加监听事件
            keys[i].addKeyListener(this);
        }
        Panel.add(resultText);
        for(int i=0;i<keys.length;i++) {
            Panel.add(keys[i]);
        }
        resultText.addKeyListener(new KeyAdapter() {  //监听resultText
            public void keyPressed(KeyEvent e){
                char label=e.getKeyChar();
                String k=String.valueOf(label);
                if(label==KeyEvent.VK_ENTER) {  //如果按下回车键 则将结果展示
                    handleResult();
                }
                else {  //否则处理该算式
                    handleEquation(k);
                }
            }});
        this.add(Panel);
        this.setSize(300,430);
        this.setLocation(300,300);
        this.setResizable(false);
        this.setVisible(true);
        centered(this);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {  //按键监听
        char label=e.getKeyChar();
        String k=String.valueOf(label);
        if(label==KeyEvent.VK_ENTER) {
            handleResult();
        }
        else {
            handleEquation(k);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        String label=e.getActionCommand();			//获取该按键内容
        if(label.equals("mc")) {
            handleMc();
        }
        else if(label.equals("ms")) {
            handleMs();
        }
        else if(label.equals("mr")) {
            handleMr();
        }
        else if(label.equals("m+")) {
            handleIncrease();
        }
        else if(label.equals("m-")) {
            handleDecrease();
        }
        else if(label.equals("=")) {
            handleResult();
        }
        else if(label.equals("AC")) {
            handleAC();
        }
        else if(label.equals("<—")) {
            handleDel();
        }
        else if(label.equals("+/-")) {
            handleOppositeNumber();
        }
        else {
            handleEquation(label);
        }
    }
    //mc方法
    public void handleMc() {  //将存储的数据归零
        resultNum=0.0;
    }
    //ms放法
    public void handleMs(){  //给存储的数据赋值
        double a=Double.valueOf(resultText.getText());
        if(!resultText.getText().equals("0")) {
            resultNum = a;
        }
    }
    //mr方法
    public void handleMr() {  //读取存储的数值
        String str=String.valueOf(resultNum);
        resultText.setText(str);
    }
    //m+方法
    private void handleIncrease() {
        double a=Double.valueOf(resultText.getText());
        if(!resultText.getText().equals("0")) {
            resultNum+=a;
        }
    }
    //m-方法
    private void handleDecrease() {
        double a=-(Double.valueOf(resultText.getText()));
        if(!resultText.getText().equals("0")) {
            resultNum+=a;
        }
    }
    //算式处理方法
    public void handleEquation(String label) {
        if(resultText.getText().equals("0")) {
            resultText.setText(label);
        }
        else {
            resultText.setText(resultText.getText()+label);
        }
    }

    public void handleResult() {
        String text=resultText.getText();
        try {
            Object a=jse.eval(text);
            text=String.valueOf(a);
            resultText.setText(text);
        }
        catch (ScriptException e) {
            e.printStackTrace();
            resultText.setText("算式格式不正确!");
        }
    }

    public void handleAC() {
        resultText.setText("0");
    }

    public void handleDel() {
        String temp = resultText.getText();
        if(temp==null || temp.equals("")){}
        else {
            temp = temp.substring(0,temp.length()-1);
            resultText.setText(temp);
        }

    }

    public void handleOppositeNumber() {
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String strText = resultText.getText();
        Matcher isNum = pattern.matcher(strText);
        if(isNum.matches()) {
            int a = Integer.parseInt(strText);
            a=a*(-1);
            strText=String.valueOf(a);
            resultText.setText(strText);
        }
    }

    //自适应居中
    public void centered(Container container) {
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        int w=container.getWidth();
        int h=container.getHeight();
        container.setBounds((screenSize.width-w)/2, (screenSize.height-h)/2, w, h);
    }

    // Main
    // public static void main(String[] args) {
    //    com.itranswarp.rich.Calculator c=new com.itranswarp.rich.Calculator();
    //}
}
