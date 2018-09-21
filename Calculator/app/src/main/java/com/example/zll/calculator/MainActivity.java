package com.example.zll.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button zero;
    Button ones;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button cheng;
    Button chu;
    Button jian;
    Button jia;
    Button point;
    Button equal;
    Button left;
    Button right;
    TextView in_put;
    String  problem = "",find = "";
    private void init() {
        zero = findViewById(R.id.zero);
        ones = findViewById(R.id.ones);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        point = findViewById(R.id.point);
        cheng = findViewById(R.id.cheng);
        jia = findViewById(R.id.jia);
        jian = findViewById(R.id.jian);
        chu = findViewById(R.id.chu);
        equal = findViewById(R.id.equal);
        in_put = findViewById(R.id.in_put);
        ones.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
        chu.setOnClickListener(this);
        cheng.setOnClickListener(this);
        equal.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        point.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onClick(View view) {
        String str = in_put.getText().toString();
        switch (view.getId()) {
            case R.id.ones:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
            case R.id.zero:
            case R.id.jia:
            case R.id.jian:
            case R.id.left:
            case R.id.right:
            case R.id.point:
                problem += ((Button)view).getText();
                find += ((Button)view).getText();
                in_put.setText(str + ((Button)view).getText());break;
            case R.id.cheng:
                problem += '*';
                find += ((Button)view).getText();
                in_put.setText(str + ((Button)view).getText());break;
            case R.id.chu:
                problem += '/';
                find += ((Button)view).getText();
                in_put.setText(str + ((Button)view).getText());break;
            case R.id.equal:
                find += ((Button)view).getText();
                gearalt(problem);
                break;
        }
    }
    private void gearalt(String s) {
        Queue<Object> queue = new LinkedList<Object>();
        Stack<Character> stack = new Stack<Character>();
        double x = 0;
        boolean isInt =false;
        boolean flag = true;
        double cnt = 10;
        for(int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if(ch>='0'&&ch<='9'){
                if(flag){
                    x = x*10 + ch-'0';
                }else{
                    x = x + (ch-'0')/cnt;
                    cnt = cnt*10.0;
                }
                isInt = true;
            }else if(ch == '.'){
                flag = false;
            }
            else{
                if(isInt)
                    queue.add((double)x);
                x = 0;
                isInt = false;
                flag = true;
                cnt = 10.0;
                if(ch=='('){
                    stack.push(ch);
                }else if(ch==')'){
                    while(stack.peek()!='('){
                        queue.add(stack.pop());
                    }
                    stack.pop();
                }else{
                    while(!stack.empty() && rank(stack.peek())>=rank(ch)){
                        queue.add(stack.pop());
                    }
                    stack.push(ch);
                }

            }
        }
        if(x!=0) queue.add(x);
        while(!stack.empty()) queue.add(stack.pop());
//			计算逆波兰表达式
        Stack<Double> integers = new Stack<Double>();
        for(Object object : queue){
            if(object instanceof Double){
                integers.push((Double) object);
            }else{
                double b = integers.pop();
                double a = integers.pop();
                char op = (Character)object;
                if(op=='+') integers.push(a+b);
                else if(op=='-') integers.push(a-b);
                else if(op=='*') integers.push(a*b);
                else integers.push(a/b);
            }
        }
        in_put.setText(find + integers.peek() + '\n');
        problem = "";
    }
    private int rank(char ch){
        if(ch=='+'||ch=='-'){
            return 1;
        }else if(ch=='*' || ch=='/'){
            return 2;
        }else{	//( )
            return 0;	//( ) 的优先级应该跟高，但这里为了代码的简洁，将其设为最小
        }
    }
}
