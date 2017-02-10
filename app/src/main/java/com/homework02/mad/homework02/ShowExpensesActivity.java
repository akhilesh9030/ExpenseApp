package com.homework02.mad.homework02;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class ShowExpensesActivity extends AppCompatActivity {
    TextView name_value, category_value, amount_value, date_value;
    ImageView rctImage;
    ImageButton firstExp, prevExp, nextExp, lastExp;
    Button fin;
    ArrayList<Expense> expenses;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Show Expenses");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        name_value = (TextView) findViewById(R.id.nameValue);
        category_value = (TextView) findViewById(R.id.categoryValue);
        amount_value = (TextView) findViewById(R.id.amountValue);
        date_value = (TextView) findViewById(R.id.dateValue);

        rctImage = (ImageView) findViewById(R.id.receiptImageView);

        firstExp = (ImageButton) findViewById(R.id.imageButtonFirst);
        prevExp = (ImageButton) findViewById(R.id.imageButtonPrev);
        nextExp = (ImageButton) findViewById(R.id.imageButtonNext);
        lastExp = (ImageButton) findViewById(R.id.imageButtonLast);

        fin = (Button) findViewById(R.id.buttonFinish);

        expenses=(ArrayList<Expense>)getIntent().getSerializableExtra("expenses");
        //Collections.sort(expenses);


        if(expenses.isEmpty()){
            Toast.makeText(ShowExpensesActivity.this,"No Expenses",Toast.LENGTH_LONG).show();
        }
        else{



            //String temp = Uri_to_bit(expenses.get(0).getReceipt());
           // rctImage.setImageBitmap(BitmapFactory.decodeFile(temp));
        }

        firstExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expenses.isEmpty()){
                    Toast.makeText(ShowExpensesActivity.this,"No Expenses",Toast.LENGTH_LONG).show();
                }
                else {
                    index = 0;
                    dataDisplay(index);
                }
            }
        });

        lastExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expenses.isEmpty()){
                    Toast.makeText(ShowExpensesActivity.this,"No Expenses",Toast.LENGTH_LONG).show();
                }
                else {
                    int lastindex = expenses.size() - 1;
                    dataDisplay(lastindex);
                    index = lastindex;
                }
            }
        });

        prevExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expenses.isEmpty()){
                    Toast.makeText(ShowExpensesActivity.this,"No Expenses",Toast.LENGTH_LONG).show();
                }
                else {
                    if (index == 0) {
                    }
                    else {
                        int previndex = index - 1;
                        dataDisplay(previndex);
                        index = previndex;
                    }
                }
            }
        });

        nextExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expenses.isEmpty()){
                    Toast.makeText(ShowExpensesActivity.this,"No Expenses",Toast.LENGTH_LONG).show();
                }
                else {
                    if (index == expenses.size() - 1) {
                    } else {
                        int nextindex = index + 1;
                        dataDisplay(nextindex);
                        index = nextindex;
                    }
                }
            }
        });

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

   public void dataDisplay(int i){
       name_value.setText(expenses.get(i).getName());
       category_value.setText(expenses.get(i).getCategory());
       amount_value.setText(""+expenses.get(i).getAmt());
       date_value.setText(expenses.get(i).getDate());

       if(expenses.get(i).getReceipt() != null){
           final InputStream ip3;
           try{
               ip3 = getContentResolver().openInputStream(expenses.get(i).getReceipt());
               final Bitmap bm3 = BitmapFactory.decodeStream(ip3);
               rctImage.setImageBitmap(bm3);
           }
           catch(Exception e1){
               e1.printStackTrace();
           }
       }
   }
}
