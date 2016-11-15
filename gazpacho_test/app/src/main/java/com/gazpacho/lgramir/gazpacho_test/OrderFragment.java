package com.gazpacho.lgramir.gazpacho_test;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zj.usbsdk.UsbController;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class OrderFragment extends Fragment {

    private Button mHomeButton;
    private Button mOrderButton = null;

    private TextView mDetails;

    private TextView mDate;
    private Gazpacho mGazpacho;

    private Calendar mCalendar;
    //private Gazpacho mCalendar;

    private TextView mSizeView;
    private TextView mTakeawayView;
    private TextView mSpicyView;
    private TextView mSaltView;
    private TextView mIngredientsView= null;
    private TextView mTradicionalView= null;

    private String mSize;
    private String mTakeaway;
    private String mSpicy;
    private String mSalt;
    private String mTradicional= null;

    private ArrayList<String> mIngredients= null;

    private int[][] u_infor;
    UsbController usbCtrl = null;
    UsbDevice dev = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, parent, false);

        usbCtrl = new UsbController(getActivity(),mHandler);
        u_infor = new int[6][2];
        u_infor[0][0] = 0x1CBE;
        u_infor[0][1] = 0x0003;
        u_infor[1][0] = 0x1CB0;
        u_infor[1][1] = 0x0003;
        u_infor[2][0] = 0x0483;
        u_infor[2][1] = 0x5740;
        u_infor[3][0] = 0x0493;
        u_infor[3][1] = 0x8760;
        u_infor[4][0] = 0x0416;
        u_infor[4][1] = 0x5011;
        u_infor[5][0] = 0x0416;
        u_infor[5][1] = 0xAABB;

        mDetails = (TextView) v.findViewById(R.id.details_label_view);

/***********************************************************************************/
        mCalendar = Calendar.getInstance();
        //mCalendar = new Gazpacho(Calendar.getInstance());
        final Date date = mCalendar.getTime();
        Locale locES = new Locale("es","ES");
        DateFormat dfES = DateFormat.getDateInstance(DateFormat.FULL, locES);

        DateFormat df = DateFormat.getTimeInstance();
        //df.setTimeZone(TimeZone.getTimeZone("gmt")); // or whatever timezone
        df.setTimeZone(TimeZone.getDefault());
        final String gmtTime = df.format(new Date());
        final String gmtTime2 =dfES.format(date);

        mDate = (TextView) v.findViewById(R.id.date_view);
        //mDate.setText("Fecha: "+mCalendar.getDate().getTime() );
        mDate.setText("Fecha: "+dfES.format(date)+" " +gmtTime);
/***********************************************************************************/

/***********************************************************************************/
        mSizeView = (TextView) v.findViewById(R.id.size_view);
        mSize= Singleton.getInstance().getAnswerSize();
        //mSizeView.setText(mData);
        //mData = getArguments().getString(SizeFragment.EXTRA);
        mSizeView.setText("tamaño: "+ mSize);

/***********************************************************************************/


/***********************************************************************************/

        mIngredientsView = (TextView) v.findViewById(R.id.ingredients_view);
        mIngredients = Singleton.getInstance().getIngredients();

        StringBuilder builder = new StringBuilder();
        for (String s: mIngredients) {
            builder.append(s+"\n");
        }
        if(mIngredients.size() == 0){
            mIngredientsView.setText("Gazpacho Tradicional (con todo)");
        }else
            mIngredientsView.setText("ingredientes: \n" + builder.toString());
        /***********************************************************************************/

/***********************************************************************************
        mSaltView = (TextView) v.findViewById(R.id.salt_view);
        mSalt= Singleton.getInstance().getAnswerSalt();
        mSaltView.setText(mSalt);

        mSpicyView = (TextView) v.findViewById(R.id.spicy_view);
        mSpicy= Singleton.getInstance().getAnswerSpice();
        mSpicyView.setText(mSpicy);

***********************************************************************************/

/***********************************************************************************/

        mTakeawayView = (TextView) v.findViewById(R.id.takeaway_view);
        mTakeaway= Singleton.getInstance().getAnswerTakaway();
        mTakeawayView.setText(mTakeaway);


/***********************************************************************************/

        mHomeButton = (Button) v.findViewById(R.id.home_button);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LanguageActivity.class);
                startActivity(intent);
            }
        });

        mOrderButton = (Button) v.findViewById(R.id.order_button);
        mOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                byte isHasPaper;
                if( v == mOrderButton ){
                    usbCtrl.close();
                    int  i = 0;
                    for( i = 0 ; i < 6 ; i++ ){
                        dev = usbCtrl.getDev(u_infor[i][0],u_infor[i][1]);
                        if(dev != null)
                            break;
                    }

                    if( dev != null ){
                        if( !(usbCtrl.isHasPermission(dev))){
                            usbCtrl.getPermission(dev);
                        }else{
                            Toast.makeText(getActivity(), getString(R.string.msg_getpermission),
                                    Toast.LENGTH_SHORT).show();
                            //btn_test.setEnabled(true);
                            mOrderButton.setEnabled(false);
                        }
                    }

                    String fecha = "";
                    String tamaño = "";
                    String ingredientes = "";
                    String sal="";
                    String chile = "";
                    String parallevar = "";
                    String lang = getString(R.string.strLang);
                    byte[] cmd = new byte[3];
                    cmd[0] = 0x1b;
                    cmd[1] = 0x21;


                    isHasPaper = usbCtrl.revByte(dev);
                    if( isHasPaper == 0x38 ){
                        Toast.makeText(getActivity(), "la impresora no tiene papel",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if((lang.compareTo("en")) == 0){

                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg("GAZPACHOS!\n", "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);

                        fecha = "Fecha: "+ gmtTime2 +" "+gmtTime+"\n" ;
                        usbCtrl.sendMsg(fecha, "GBK", dev);


                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);

                        StringBuilder builder = new StringBuilder();
                        for (String s: mIngredients) {
                            builder.append(s+"\n");
                        }
                        if(mIngredients.size() == 0){
                            ingredientes="Gazpacho Tradicional";
                            usbCtrl.sendMsg(ingredientes, "GBK", dev);

                        }else {
                            ingredientes = "ingredientes: \n\n" + builder.toString();
                            usbCtrl.sendMsg(ingredientes, "GBK", dev);
                        }

                        /*
                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        sal = mSalt;
                        if((sal.compareTo("con sal")) == 0){
                            usbCtrl.sendMsg(sal, "GBK", dev);
                        }


                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        chile = mSpicy;
                        if((chile.compareTo("con chile")) == 0){
                            usbCtrl.sendMsg(chile, "GBK", dev);
                        }
                        */

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        parallevar = mTakeaway;
                        usbCtrl.sendMsg(parallevar, "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        tamaño = mSize ;
                        usbCtrl.sendMsg(tamaño, "GBK", dev);

                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg("------------------------------\n", "GBK", dev);

                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg("COPIA\n", "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);

                        fecha = "Fecha: "+ gmtTime2 +" "+gmtTime+"\n" ;
                        usbCtrl.sendMsg(fecha, "GBK", dev);


                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);

                        StringBuilder builder2 = new StringBuilder();
                        for (String s: mIngredients) {
                            builder2.append(s+"\n");
                        }
                        if(mIngredients.size() == 0){
                            ingredientes="Gazpacho Tradicional";
                            usbCtrl.sendMsg(ingredientes, "GBK", dev);

                        }else {
                            ingredientes = "ingredientes: \n\n" + builder2.toString();
                            usbCtrl.sendMsg(ingredientes, "GBK", dev);
                        }

                        /*
                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        sal = mSalt;
                        if((sal.compareTo("con sal")) == 0){
                            usbCtrl.sendMsg(sal, "GBK", dev);
                        }

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        chile = mSpicy;
                        if((chile.compareTo("con chile")) == 0){
                            usbCtrl.sendMsg(chile, "GBK", dev);
                        }
                        */

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        parallevar = mTakeaway;
                        usbCtrl.sendMsg(parallevar, "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        tamaño = mSize ;
                        usbCtrl.sendMsg(tamaño, "GBK", dev);
                    }

                    cmd[2] |= 0x10;
                    usbCtrl.sendByte(cmd, dev);
                    usbCtrl.sendMsg("------------------------------\n", "GBK", dev);
                    cmd[2] |= 0x10;
                }

            }


        });

        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        usbCtrl.close();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbController.USB_CONNECTED:
                    //Toast.makeText(getApplicationContext(), getString(R.string.msg_getpermission),
                    //      Toast.LENGTH_SHORT).show();
                    //                btn_test.setEnabled(true);
                    //               btn_conn.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}