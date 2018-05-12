package com.furalezipin.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity implements View.OnClickListener {

    GridLayout gridLayout;
    EditText display;
    ArrayList<Button> buttons;

    String txtButtons[][] =  { {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {".", "0", "=", "+"},
    };
    StringBuilder curOperand;
    ArrayList<String> opList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        display = (EditText) findViewById(R.id.display);
        opList = new ArrayList<String>();
        buttons = new ArrayList<Button>();
        curOperand = new StringBuilder();

        for (int i = 0; i < txtButtons.length; ++i) {
            for (int j = 0; j < txtButtons[i].length; ++j) {
                Button button = createButton(txtButtons[i][j]);
                buttons.add(button);
                gridLayout.addView(button);
            }
        }
    }

    public void calculate() {
        double operand1 = Double.parseDouble(opList.get(0));
        String operation = opList.get(1);
        double operand2= Double.parseDouble(opList.get(2));
        double result = 0;

        switch (operation) {
            case "+":
                result = operand1 + operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            default:
                return;
        }

        display.setText(Double.toString(result));

    }

    public Button createButton(String text) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setOnClickListener(this);

        LinearLayout.LayoutParams  lllp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        float dp = this.getResources().getDisplayMetrics().density;
        int size = (int)(80*dp);
        lllp.width = size;
        lllp.height = size;
        btn.setLayoutParams(lllp);

        return btn;
    }

    @Override
    public void onClick(View view) {
        Button button = (Button)view;
        String command = button.getText().toString();

        if (checkDigit(command)) {
            curOperand.append(command);
            display.setText(curOperand.toString());
        } else if (command.equals(".")) {
            if (curOperand.toString().contains(".")) return;
            curOperand.append(command);
            display.setText(curOperand);
        } else {
            if (opList.size() >= 2) {
                opList.add(curOperand.toString());
                calculate();
                opList.clear();
                curOperand.setLength(0);
                opList.add(display.getText().toString());
                if (!command.equals("=")) {
                    opList.add(command);
                }
            } else {
                opList.add(curOperand.toString());
                curOperand.setLength(0);
                opList.add(command);
            }
        }

    }

    public boolean checkDigit(String operand) {
        Pattern pattern = Pattern.compile("^[0-9]");
        Matcher matcher = pattern.matcher(operand);
        return matcher.matches();
    }
}
