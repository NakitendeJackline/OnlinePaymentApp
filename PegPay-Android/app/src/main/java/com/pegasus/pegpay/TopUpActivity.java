package com.pegasus.pegpay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gc.materialdesign.R.layout.dialog;
import static com.pegasus.pegpay.AccountManager.saveAccountBalance;

/**
 * Created by Zed on 5/10/2016.
 */
public class TopUpActivity extends AppCompatActivity {
    private final int PICK_CONTACT = 2;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
   // TextView RequestField1, RequestField2, RequestField3;
//    Context context = getApplicationContext();

    AQuery aq;
    private static final String url = "https://test.pegasus.co.ug:8019/TestAppApi/Service.asmx?WSDL";

    HashMap<String, Object> params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up2);
        aq = new AQuery(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        EditText RequestField2 = aq.id(R.id.top_phone_number).getEditText();
       EditText RequestField1 = aq.id(R.id.top_merchant_id).getEditText();
       EditText RequestField3 = aq.id(R.id.top_amount).getEditText();


        aq.id(R.id.toptop_submit).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit(){
        HashMap<String, Object> params = new HashMap<>();

        String RequestField2 = aq.id(R.id.top_phone_number).getText().toString().trim();
        String RequestField1 = aq.id(R.id.top_merchant_id).getText().toString().trim();
        String RequestField3 = aq.id(R.id.top_amount).getText().toString().trim();


        if(RequestField2.length() < 10){
            showErrorToast("Please enter a valid phone number");
            return;
        }
        if(RequestField1.length() < 6){
            showErrorToast("Please enter a valid Merchant ID");
            return;
        }
        if(RequestField3.isEmpty()){
            showErrorToast("Please enter the amount");
            return;
        }

        AccountManager.savePhone(this, RequestField2 );
        AccountManager.saveMerchantId(this, RequestField1 );
        AccountManager.saveAmount(this, RequestField3 );

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        params.put("phone_number", RequestField2);
        params.put("merchant_id", RequestField1);
        params.put("amount", RequestField3);
        postTopUpData(params);


    }

    private void postTopUpData(final HashMap<String, Object> params) {

        AsyncTask<Void, Void, Void> execute = new AsyncTask<Void, Void, Void>() {
            SystemRequest req = new SystemRequest();
            PegPayResponse resp = new PegPayResponse();
            String AccessId = "PEGPAY_APP";
            String Password = "F3D05B8DC763605BCEEA27D11D0E8346";
            String BankCode = "PSSM";
            String BranchCode = "MAIN";

            @Override
            protected void onPreExecute() {

                String RequestField2 = params.get("phone_number").toString();
                String RequestField1 =  params.get("merchant_id").toString();
                String RequestField3 = params.get("amount").toString();

                req.setBankCode("PSSM");
                req.setBranchCode("MAIN");
                req.setPassword("F3D05B8DC763605BCEEA27D11D0E8346");
                req.setAccessId("PEGPAY_APP");
                req.setRequestField1(RequestField1);
                req.setRequestField2(RequestField2);
                req.setRequestField3(RequestField3);


                Log.d("Data", "Bd : "+ req.getRequestField3());
                super.onPreExecute();


            }

            @Override
            protected Void doInBackground(Void... params) {

                if (req != null) {
                    RequestMapper mapper = new RequestMapper();
                    resp = mapper.CustomerTopUp(req);
                    Log.d("Response", "StatusCode: "+ resp.getStatus());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vogella.com"));
                startActivity(i);


            }
        }.execute();

    }

    private void showConfirmation(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Intent i = new Intent(TopUpActivity.this, SelectPaymentMethodActivity.class);
                params.put("confirmed", true);
                i.putExtra("params", params);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Cancel", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showErrorToast(String error){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
