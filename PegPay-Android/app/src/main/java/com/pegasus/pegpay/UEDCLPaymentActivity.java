package com.pegasus.pegpay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;

/**
 * Created by Zed on 5/9/2016.
 */
public class UEDCLPaymentActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    AQuery aq;
    String url = "http://vpointconsultancy.com/pegpay/api.php?action=confirm";

    HashMap<String, Object> params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uedcl_payment_activity);
        aq = new AQuery(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        EditText amountText = aq.id(R.id.txt_amount).getEditText();
        amountText.addTextChangedListener(new NumberTextWatcherForThousand(amountText));

        aq.id(R.id.btn_submit).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit(){
        Utils.hideKeyboard(this);

        String meterNumber = aq.id(R.id.txt_account_number).getText().toString().trim();
        String amount = NumberTextWatcherForThousand.trimCommaOfString(aq.id(R.id.txt_amount).getText().toString().trim());

        if(meterNumber.isEmpty()){
            showErrorToast("Please enter your account number");
            return;
        }
        if(amount.isEmpty()){
            showErrorToast("Please enter the amount");
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        params = new HashMap<>();
        params.put("meter_number", meterNumber);
        params.put("amount", amount);
        params.put("recipient", "UEDCL");
        params.put("ref", "Electricity Payment for " + meterNumber);

        aq.ajax(url, params, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                progressDialog.dismiss();
                showConfirmation(response);
            }
        });
    }

    private void showConfirmation(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Intent i = new Intent(UEDCLPaymentActivity.this, SelectPaymentMethodActivity.class);
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
