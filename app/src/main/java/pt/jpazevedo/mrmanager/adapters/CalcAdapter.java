package pt.jpazevedo.mrmanager.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaopedroazevedo11 on 26/03/17.
 */

public class CalcAdapter{

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonPoint;
    private Button buttonDel;

    private View context;
    private TextView tvValue;

    private static String int_value = "";

    private static String decimal_value = "00";

    private final static String point_value = ".";

    private static boolean intpart = true;

    public CalcAdapter(View context){
        this.context = context;
    }

    public CalcAdapter(View context, TextView tvValue){
        this.context = context;
        this.tvValue = tvValue;
    }

    public void initUI(){
        initButtons(context);
        clearValue();
    }

    private void initButtons(View context){

            button0 = (Button) context.findViewById(R.id.bNum0);
            button1 = (Button) context.findViewById(R.id.bNum1);
            button2 = (Button) context.findViewById(R.id.bNum2);
            button3 = (Button) context.findViewById(R.id.bNum3);
            button4 = (Button) context.findViewById(R.id.bNum4);
            button5 = (Button) context.findViewById(R.id.bNum5);
            button6 = (Button) context.findViewById(R.id.bNum6);
            button7 = (Button) context.findViewById(R.id.bNum7);
            button8 = (Button) context.findViewById(R.id.bNum8);
            button9 = (Button) context.findViewById(R.id.bNum9);
            buttonDel = (Button) context.findViewById(R.id.bNumDel);
            buttonPoint = (Button) context.findViewById(R.id.bNumPoint);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("0");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChar("9");
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearValue();
            }
        });

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalcAdapter.intpart = false;
            }
        });
    }

    private void addChar(String s){

        if(intpart){
            CalcAdapter.int_value = CalcAdapter.int_value.concat(s);
        }
        else{
            char aux = CalcAdapter.decimal_value.charAt(1);
            CalcAdapter.decimal_value = aux + s;
        }

        if(tvValue != null){
            tvValue.setText(CalcAdapter.int_value+CalcAdapter.point_value+CalcAdapter.decimal_value + " €");
        }
    }

    private void clearValue(){
        if(tvValue != null){
            CalcAdapter.intpart = true;
            tvValue.setText("00.00 €");
            CalcAdapter.decimal_value = "00";
            CalcAdapter.int_value = "";
        }
    }

}
