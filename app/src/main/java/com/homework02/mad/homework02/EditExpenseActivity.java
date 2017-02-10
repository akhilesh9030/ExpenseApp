package com.homework02.mad.homework02;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class EditExpenseActivity extends AppCompatActivity {

    Button selexp;
    EditText expname;
    EditText expamt;
    Spinner edit;
    Button save;
    Button cancel;
     CharSequence[] exp;
    String editedobject;
    String newcat;
    Uri selectedImage;

    ImageButton date_button;
    ImageView receiptImageView;
    EditText date_value;

    final static String CANC="CANCEL";
    final static int REQ_CODE_RECEIPT = 100;

    ArrayList<Expense> expenses ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Edit Expense");

        selexp = (Button) findViewById(R.id.delexp);
        expname=(EditText)findViewById(R.id.expname);
        edit = (Spinner) findViewById(R.id.edit);
        expamt = (EditText) findViewById(R.id.expamt);
        save =(Button) findViewById(R.id.delbut);
        cancel=(Button)findViewById(R.id.cancelbut);

        date_button = (ImageButton) findViewById(R.id.imageButtonDate);
        receiptImageView = (ImageView) findViewById(R.id.imageView);
        date_value = (EditText) findViewById(R.id.dateValue);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        selexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expenses=(ArrayList<Expense>)getIntent().getSerializableExtra("expenses");
                ArrayList<String>  expnames=new ArrayList<String>();

                if(expenses.isEmpty()){
                    Toast.makeText(EditExpenseActivity.this,"No Expenses to be selected",Toast.LENGTH_LONG).show();
                    Log.d("demo",""+expenses);
                }
                else{
                    Log.d("demo","inside edit expense else");
                    for(Expense e:expenses){
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
                                                if(s.equals(e.getCategory()))
                                                    break;
                                            }
                                            edit.setSelection(i-1);
                                            expamt.setText(""+e.getAmt());
                                            date_value.setText(e.getDate());

                                            final InputStream ip2;
                                            try{
                                               ip2 = getContentResolver().openInputStream(e.getReceipt());
                                                final Bitmap bm2 = BitmapFactory.decodeStream(ip2);
                                                receiptImageView.setImageBitmap(bm2);
                                            }
                                            catch(Exception e1){
                                             e1.printStackTrace();
                                            }
                                            Log.d("demo","after uri");
                                        }
                                    }
                                }
                            }).create().show();
                }


            }
        });

        final Calendar myCal = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCal.set(Calendar.YEAR,i);
                myCal.set(Calendar.MONTH,i1);
                myCal.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                date_value.setText(sdf.format(myCal.getTime()));

            }
        };

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditExpenseActivity.this,d1,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        receiptImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_receipt = new Intent(Intent.ACTION_PICK);
                intent_receipt.setType("image/*");
                startActivityForResult(intent_receipt,REQ_CODE_RECEIPT);
            }
        });

        edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newcat=edit.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Expense e:expenses){
                    if(editedobject.equals(e.getName())){
                        e.setName(expname.getText().toString());
                        e.setCategory(newcat);
                        e.setAmt(Double.parseDouble(expamt.getText().toString()));
                        e.setDate(date_value.getText().toString());
                        if(selectedImage!= null){
                        e.setReceipt(selectedImage);}

                        Intent intent =new Intent();
                        intent.putExtra("expensenew",e);
                        intent.putExtra("editedexpense",editedobject);
                        setResult(2,intent);
                        finish();
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();

                intent.putExtra(CANC,"cancel");
                setResult(2,intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_RECEIPT && resultCode == RESULT_OK && null != data) {

            try{
                selectedImage = data.getData();
                final InputStream ip = getContentResolver().openInputStream(selectedImage);
                final Bitmap bm = BitmapFactory.decodeStream(ip);
                receiptImageView.setImageBitmap(bm);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
