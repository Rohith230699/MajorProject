package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PaymentActivity extends AppCompatActivity {


    TextInputEditText Name, UpiId, Amount, Note;
    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    Button pay;
    Uri uri;
    public static String payerName, upiId, msgNote, sendAmount, status;
    String approvalRefNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Name = findViewById(R.id.gpayname);
        UpiId = findViewById(R.id.gpayuid);
        Amount = findViewById(R.id.gpayamount);
        Note = findViewById(R.id.gpaynote);

        pay = findViewById(R.id.buttonPay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                payerName = Name.getText().toString();
                upiId = UpiId.getText().toString();
                msgNote = Note.getText().toString();
                sendAmount = Amount.getText().toString();

                if(!payerName.equals("") && !upiId.equals("") && !msgNote.equals("") && !sendAmount.equals("")){

                    uri = getUpiPaymentUri(payerName, upiId, msgNote, sendAmount);
                    payWithGpay(GPAY_PACKAGE_NAME);

                }
                else {
                    Toast.makeText(PaymentActivity.this,"Fill all above details and try again.", Toast.LENGTH_SHORT).show();


                }



            }
        });

    }
    private static Uri getUpiPaymentUri(String name, String upiId, String note, String amount){
        return  new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",note)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR")
                .build();
    }

    private void payWithGpay(String packageName){

        if(isAppInstalled(this,packageName)){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(packageName);
            startActivityForResult(intent,0);

        }
        else{
            Toast.makeText(PaymentActivity.this,"Google pay is not installed. Please istall and try again.", Toast.LENGTH_SHORT).show();
        }

    }


    public static boolean isAppInstalled(Context context, String packageName){
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
            approvalRefNo = data.getStringExtra("txnRef");
        }
        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(PaymentActivity.this, "Transaction successful. "+approvalRefNo, Toast.LENGTH_SHORT).show();

        }

        else{
            Toast.makeText(PaymentActivity.this, "Transaction cancelled or failed please try again.", Toast.LENGTH_SHORT).show();
        }

    }

}