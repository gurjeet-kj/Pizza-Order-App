package com.example.pizzaorderjuly26;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView price,qty,total;
    Spinner pizza;
    Button calc;
    RadioButton small,medium,large;
    SeekBar sb;
    CheckBox chs[]= new CheckBox[7];
    String names[]={"Florance Pizza","Margrita Pizza","Greek Pizza","Sicilian Pizza","Chicago Pizza"};
    double prices[]={9.75,11.99,10.5,7.99,9.5};
    static double smallSizePrice=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        price=findViewById(R.id.txvPrice);
        qty=findViewById(R.id.txvQty);
        total=findViewById(R.id.txvTotal);
        pizza=findViewById(R.id.spPizza);
        calc=findViewById(R.id.btnCalc);
        small=findViewById(R.id.rdbSmall);
        medium=findViewById(R.id.rdbMedium);
        large=findViewById(R.id.rdbLarge);
        sb=findViewById(R.id.sbQty);
        chs[0]=findViewById(R.id.chk0);
        chs[1]=findViewById(R.id.chk1);
        chs[2]=findViewById(R.id.chk2);
        chs[3]=findViewById(R.id.chk3);
        chs[4]=findViewById(R.id.chk4);
        chs[5]=findViewById(R.id.chk5);
        chs[6]=findViewById(R.id.chk6);

        //create array adapter depend on names array which has pizza names
        ArrayAdapter aa=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,names);
        pizza.setAdapter(aa);

        //implement the spinner event
        pizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                smallSizePrice=prices[index];
                double currentPrice=smallSizePrice;
                if(medium.isChecked())
                    currentPrice*=1.5;
                else if(large.isChecked())
                    currentPrice*=2;
                price.setText(String.format("%.2f",currentPrice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //set radiobuttons as listeners
        small.setOnClickListener(new ButtonsEvents());
        medium.setOnClickListener(new ButtonsEvents());
        large.setOnClickListener(new ButtonsEvents());
        calc.setOnClickListener(new ButtonsEvents());

        //set checkboxes as listeners
        for(int i=0;i<chs.length;i++)
            chs[i].setOnCheckedChangeListener(new CheckBoxEvents());
        //implement the seekbar
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                qty.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    private class ButtonsEvents implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rdbSmall:
                    price.setText(String.valueOf(smallSizePrice));
                    break;
                case R.id.rdbMedium:
                    price.setText(String.format("%.2f",1.5*smallSizePrice));
                    break;
                case R.id.rdbLarge:
                    price.setText(String.format("%.2f",2*smallSizePrice));
                    break;
                case R.id.btnCalc:
                    double currentPrice=Double.parseDouble(price.getText().toString());
                    int currentQty=Integer.parseInt(qty.getText().toString());
                    double amount=currentPrice*currentQty*1.13;
                    total.setText(String.format("%.2f",amount));
            }
        }
    }
    private class CheckBoxEvents implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            double currentPrice=Double.parseDouble(price.getText().toString());
            int count=0;

            for(int i=0;i<chs.length;i++)
                if(chs[i].isChecked())
                    count++;
            if(count>1)
                if (buttonView.isChecked())
                    currentPrice += 2;
                else
                    currentPrice-=2;
            else if(count==1)
                if(!buttonView.isChecked())
                currentPrice-=2;
            price.setText(String.valueOf(currentPrice));

        }

        }
    }
