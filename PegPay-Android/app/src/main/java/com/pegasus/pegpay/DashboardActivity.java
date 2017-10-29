package com.pegasus.pegpay;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.Calendar;
import java.util.HashMap;

import static android.app.PendingIntent.getActivity;

/**
 * Created by Zed on 4/16/2016.
 */
public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    AQuery aq;
    NavigationView navigationView;
    HashMap<String, Object> params;
    private Bundle bundle = new Bundle();
    private ImageView bg;
    private Intent i = null;
    TextView balance;
    BroadcastReceiver updateReceiver;
    Context cxt;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        aq = new AQuery(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bundle = getIntent().getExtras();

        aq.id(R.id.txt_transations).clicked(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isMiniStatement", true);
                        Intent intent = new Intent(DashboardActivity.this, RecentTransactions.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

        // Button btn_pay_bill_merchant = (Button)findViewById(R.id.btn_pay_bill_merchant);
        aq.id(R.id.btn_pay_bill_merchant).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, PayBillActivity.class));

            }
        });

        Button btn_receive_payment = (Button)findViewById(R.id.btn_receive_payment);
        aq.id(R.id.btn_receive_payment).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ReceivePaymentActivity.class));

            }
        });
        // Button btn_pay_bill = (Button)findViewById(R.id.btn_pay_bill);
        //btn_pay_bill.setOnClickListener
        aq.id(R.id.btn_pay_bill).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, PayBillActivity.class));

            }
        });

        /*aq.id(R.id.btn_top_up).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopupPopup();

            }
        });
*/
        aq.id(R.id.btn_top_up).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TopUpActivity.class));

            }
        });
        aq.id(R.id.btn_pay_merchant).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, OtherPaymentsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        aq.id(R.id.btn_buy_airtime).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, BuyAirtimeActivity.class));
            }
        });

        aq.id(R.id.btn_send_money).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send_money = new Intent(DashboardActivity.this, SendMoneyActivity.class);
                send_money.putExtras(bundle);
                startActivity(send_money);

            }
        });
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.txt_username);
        name.setText("Welcome " + AccountManager.getFullName(this)  + "! - " );
//        Toast.makeText(DashboardActivity.this, " TEST FULL NAME : " + AccountManager.getFullName(this), Toast.LENGTH_LONG).show();

        aq.id(R.id.txt_welcome).text("Welcome " +AccountManager.getFullName(this)+"!");
       TextView merchantId = (TextView)findViewById(R.id.display_merchant);
        merchantId.setText("" +AccountManager.getMerchantId(this));
        balance = (TextView)findViewById(R.id.txt_account_balance);
        balance.setText("UGX "+AccountManager.getAccountBalance(this));

        if(AccountManager.getAccountType(this) != 2){
            aq.id(R.id.merchant_payment_ro_actions).gone();
        }else{
            aq.id(R.id.btn_pay_bill).gone();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateReceiver);
    }



    @Override
    protected void onResume() {
        super.onResume();
        params = new HashMap<>();
        params.clear();
    }

    private void logout(){
        AccountManager.logout(this);
        Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, LandingActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
        this.finish();
    }

    private void openTopupPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Topup");
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.topup_pegpay_activity, null);
        final EditText amountTextView = (EditText) view.findViewById(R.id.txt_amount);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String amount = amountTextView.getText().toString().trim();
                if(amount.isEmpty()){
                    Toast.makeText(DashboardActivity.this, "Please enter topup amount", Toast.LENGTH_SHORT).show();
                }else{
                    params = new HashMap<>();
                    params.put("amount", amount);
                    params.put("recipient", "PegPay");
                    params.put("ref", "PegPay Topup");

                    Intent i = new Intent(DashboardActivity.this, SelectPaymentMethodActivity.class);
                    params.put("confirmed", true);
                    i.putExtra("params", params);
                    i.putExtra("hide_peg_pay", true);
                    startActivity(i);
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_my_pegpay:
                break;
            case R.id.nav_activity:
                startActivity(new Intent(this, RecentTransactions.class));
                break;
            case R.id.nav_pay_bill:
                startActivity(new Intent(this, PayBillActivity.class));
                break;
            case R.id.nav_topup:
                openTopupPopup();
                break;
            case R.id.nav_artime:
                startActivity(new Intent(this, BuyAirtimeActivity.class));
                break;
            case R.id.nav_send_money:
                startActivity(new Intent(this, SendMoneyActivity.class));
                break;
            case R.id.nav_pay_merchant:
                startActivity(new Intent(this, OtherPaymentsActivity.class));
                break;
            case R.id.nav_inbox:
                startActivity(new Intent(this, InboxActivity.class));
                break;
            case R.id.nav_my_account:
                Intent i = new Intent(this, SingupLoginActivity.class);
                i.putExtra("tab", 0);
                i.putExtra("is_edit", true);
                startActivity(i);
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            Utils.log("Refresh clicked");
        }
        return super.onOptionsItemSelected(item);
    }
}
