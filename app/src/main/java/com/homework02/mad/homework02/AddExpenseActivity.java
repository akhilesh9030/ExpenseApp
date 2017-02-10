package com.homework02.mad.homework02;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    EditText expname;
    String category;
    Spinner spinner;
    EditText amt,date_value;
    Expense expense = new Expense();
    Button addexp;
    ImageButton date_button;
    Uri selectedImage;
    ImageView image_View;


    final static int REQ_CODE_RECEIPT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Add Expense");

        expname= (EditText) findViewById(R.id.expname);
        spinner = (Spinner) findViewById(R.id.addspin);
        date_button = (ImageButton) findViewById(R.id.imageButtonDate);
        date_value = (EditText) findViewById(R.id.dateValue);
        image_View = (ImageView) findViewById(R.id.imageView);


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
                new DatePickerDialog(AddExpenseActivity.this,d1,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_receipt = new Intent(Intent.ACTION_PICK);
                intent_receipt.setType("image/*");
                startActivityForResult(intent_receipt,REQ_CODE_RECEIPT);

//                Intent in = new Intent(Intent.ACTION_PICK);
//                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                String pictureDirectoryPath = pictureDirectory.getPath();
//                Uri data = Uri.parse(pictureDirectoryPath);
//                in.setDataAndType(data, "image/*");
//                startActivityForResult(in,REQ_CODE_RECEIPT);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         amt = (EditText) findViewById(R.id.expamt);
        addexp = (Button) findViewById(R.id.expbutton);

        addexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("demo","line--------------1");
                try{
                    Log.d("demo","line--------------try");
                    expense.setAmt(Double.parseDouble(amt.getText().toString()));
                    Log.d("demo","line--------------1");
                    if(expname.getText().toString() != "" && category != "" && date_value.getText().toString() != ""){
                        Log.d("demo","line--------------try if");
                        expense.setName(expname.getText().toString());
                        expense.setCategory(category);
                        expense.setDate(date_value.getText().toString());
                        Log.d("demo",expense.getName()+expense.getCategory()+expense.getDate() +expense.getAmt());
                    }
                    else{
                        Log.d("demo","line--------------try else");
                        Toast.makeText(AddExpenseActivity.this,"Invalid Values",Toast.LENGTH_LONG);
                    }
                    if(selectedImage != null){
                        Log.d("demo","line--------------try else if");
                       // expense.setReceipt(selectedImage);
                    }

                    Log.d("demo","line--------------try3");

                    Intent intent =new Intent();
                         Log.d("demo","line--------------try4");
                    intent.putExtra("expense",expense);
                         Log.d("demo","line--------------try5");
                    setResult(1,intent);
                          Log.d("demo","line--------------try6");


                           Log.d("demo","line--------------try7");
                    finish();
                }
                catch(Exception e){
                    Toast.makeText(AddExpenseActivity.this,"Invalid Values",Toast.LENGTH_LONG);
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_RECEIPT && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
            Bitmap bitmapImage = null;
            try {
                DecodeBitmap obj=new DecodeBitmap();
                bitmapImage = obj.decodeBitmap (AddExpenseActivity.this,selectedImage );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
                image_View.setImageBitmap(bitmapImage);



        }
    }

    }

