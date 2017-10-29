package com.pegasus.pegpay;

/**
 * Created by Jackie on 18-Oct-17.
 */


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


public class QueryRequest implements KvmSerializable {


    private String BankCode;
    private String Password;
    private String AccessId;
    private String Field1;
    private String Field2;




    @Override
    public Object getProperty(int i) {

        Object obj = null;
        switch (i) {
            case 0:
                obj = this.BankCode;

                break;

            case 1:
                obj = this.Password;

                break;
            case 2:
                obj = this.AccessId;

                break;
            case 3:
                obj = this.Field1;
                break;
            case 4:
                obj = this.Field2;
                break;

        }
        return obj;
    }




    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int position, Object value) {
        switch (position) {
            case 0:
                BankCode = value.toString();
                break;

            case 1:
                Password = value.toString();
                break;
            case 2:
                AccessId = value.toString();
                break;
            case 3:
                Field1 = value.toString();
                break;
            case 4:
               Field2 = value.toString();
                break;

        }
    }


    @Override
    public void getPropertyInfo(int val, Hashtable hashtable, PropertyInfo info) {

        switch (val) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BankCode";
                break;

            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Password";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AccessId";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Field1";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Field2";
                break;

        }
    }


    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAccessId() {
        return AccessId;
    }

    public void setAccessId(String accessId) {
        AccessId = accessId;
    }

    public String getField1() {
        return Field1;
    }

    public void setField1(String field1) {
        Field1 = field1;
    }

    public String getField2() {
        return Field2;
    }

    public void setField2(String field2) {
        Field2 = field2;
    }
}
