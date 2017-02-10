package com.homework02.mad.homework02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    Button add;
    Button edit;
    Button delete;
    Button show;

    ArrayList<Expense>  expenses =new ArrayList<Expense>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Expense App");


        add= (Button) findViewById(R.id.add);
        edit = (Button) findViewById(R.id.edit);
        delete= (Button) findViewById(R.id.delete);
        show = (Button) findViewById(R.id.show);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),AddExpenseActivity.class);
                startActivityForResult(i,1);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),EditExpenseActivity.class);
                i.putExtra("expenses",expenses);
                startActivityForResult(i,2);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getBaseContext(),DeleteExpenseActivity.class);
                i.putExtra("expenses",expenses);
                startActivityForResult(i,3);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getBaseContext(),ShowExpensesActivity.class);
                i.putExtra("expenses",expenses);
                startActivity(i);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("demo","111111111111");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            Log.d("demo","22222222222222");
            if(data!=null) {
                Expense newexp = (Expense) data.getSerializableExtra("expense");
                Log.d("demo","333333333");
                expenses.add(newexp);
            }
        }
        if(requestCode==2) {


            if(data!=null && data.getExtras().containsKey(EditExpenseActivity.CANC)){

            }
            else{
                if(data!=null) {

                    Expense editedexpense = (Expense) data.getSerializableExtra("expensenew");
                    String editedobj = data.getStringExtra("editedexpense");
                    for (Iterator<Expense> iterator = expenses.iterator(); iterator.hasNext(); ) {
                        Expense e = iterator.next();
                        if (editedobj.equals(e.getName())) {
                            iterator.remove();
                        }
                    }

                    expenses.add(editedexpense);
                }
            }
        }
        if(requestCode==3) {
            if(data != null && data.getExtras().containsKey(DeleteExpenseActivity.CANCEL_KEY)){

            }
            else {
                if (data != null) {
                    //  Expense deletedexpense = (Expense) data.getSerializableExtra("expensedeleted");
                    String delobj = data.getStringExtra("deletedexpense");

                    for (Iterator<Expense> iterator = expenses.iterator(); iterator.hasNext();) {
                        Expense e = iterator.next();
                        if (delobj.equals(e.getName())){
                            iterator.remove();
                        }
                    }
                }
            }
        }


        }


    }

