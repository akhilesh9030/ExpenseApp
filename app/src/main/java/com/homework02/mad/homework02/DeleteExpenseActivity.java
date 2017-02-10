package com.homework02.mad.homework02;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class DeleteExpenseActivity extends AppCompatActivity {
    Button delexp;
    EditText expname;
    EditText expamt;
    Spinner edit;
    Button delete;
    Button cancel;
    CharSequence[] exp;
    String editedobject;
    String newcat;

    Uri selectedImage;

    ImageButton date_button;
    ImageView receiptImageView;
    EditText date_value;

    ArrayList<Expense> expenses  = new ArrayList<Expense>();

    final static String CANCEL_KEY="CANCEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Delete Expense");

        delexp = (Button) findViewById(R.id.delexp);
        expname=(EditText)findViewById(R.id.expname);
        edit = (Spinner) findViewById(R.id.edit);
        expamt = (EditText) findViewById(R.id.expamt);
        delete =(Button) findViewById(R.id.delbut);
        cancel=(Button)findViewById(R.id.cancelbut);

        date_button = (ImageButton) findViewById(R.id.imageButtonDate);
        receiptImageView = (ImageView) findViewById(R.id.imageView);
        date_value = (EditText) findViewById(R.id.dateValue);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        delexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expenses=(ArrayList<Expense>)getIntent().getSerializableExtra("expenses");
                ArrayList<String>  expnames=new ArrayList<String>();

                if(expenses.isEmpty()){
                    Toast.makeText(DeleteExpenseActivity.this,"No Expenses to be selected",Toast.LENGTH_LONG).show();
                }
                else{

                    for(Expense e:expenses)
                    {
                        String expname=e.getName();
                        expnames.add(expname);
                    }

                    Collections.sort(expnames);
                    exp = expnames.toArray(new CharSequence[expnames.size()]);
                    builder.setTitle("Pick an Expense")
                            .setItems(exp, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(Expense e:expenses) {
                                        if (exp[which].equals(e.getName())){
                                            editedobject=e.getName();
                                            expname.setText(e.getName());
                                            String[] catgor= getResources().getStringArray(R.array.category);
                                            int i=0;
                                            for(String s:catgor){

                                                i++;
                                                Log.d("s",s);
                                                Log.d("cat",e.getCategory());
                                                if(s.equals(e.getCategory()))
                                                    break;
                                            }
                                            edit.setSelection(i-1);
                                            expamt.setText(""+e.getAmt());

                                            date_value.setText(e.getDate());

                                            if(e.getReceipt()!=null){
                                                final InputStream ip3;
                                                try{
                                                    ip3 = getContentResolver().openInputStream(e.getReceipt());
                                                    final Bitmap bm3 = BitmapFactory.decodeStream(ip3);
                                                    receiptImageView.setImageBitmap(bm3);
                                                }
                                                catch(Exception e1){
                                                    e1.printStackTrace();
                                                }
                                            }

                                            expname.setEnabled(false);
                                            expamt.setEnabled(false);
                                            date_value.setEnabled(false);

                                            receiptImageView.setEnabled(false);
                                            date_button.setEnabled(false);
                                        }
                                    }
                                }
                            }).create().show();
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Expense e:expenses){
                    if(editedobject.equals(e.getName())){
                        Intent intent =new Intent();
                       // intent.putExtra("expensedeleted",e);
                        intent.putExtra("deletedexpense",editedobject);
                        setResult(3,intent);
                        finish();
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

                intent.putExtra(CANCEL_KEY,"cancel");
                setResult(3,intent);
                finish();
            }
        });
    }
}
