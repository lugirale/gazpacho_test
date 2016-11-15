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


public class FrenchOrderFragment extends Fragment {

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
    private TextView mIngredientsView;
    private TextView mTradicionalView;

    private String mSize;
    private String mTakeaway;
    private String mSpicy;

    private String mTradicional;
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
        View v = inflater.inflate(R.layout.french_fragment_order, parent, false);

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
        Locale locFR = new Locale("fr","FS");
        DateFormat dfFR = DateFormat.getDateInstance(DateFormat.FULL, locFR);

        DateFormat df = DateFormat.getTimeInstance();
        //df.setTimeZone(TimeZone.getTimeZone("gmt")); // or whatever timezone
        df.setTimeZone(TimeZone.getDefault());
        final String gmtTime = df.format(new Date());
        final String gmtTime2 =dfFR.format(date);

        mDate = (TextView) v.findViewById(R.id.date_view);
        //mDate.setText("Fecha: "+mCalendar.getDate().getTime() );
        mDate.setText("Date: "+dfFR.format(date)+" " +gmtTime);
/***********************************************************************************/

/***********************************************************************************/
        mSizeView = (TextView) v.findViewById(R.id.size_view);
        mSize= Singleton.getInstance().getAnswerSize();
        //mSizeView.setText(mData);
        //mData = getArguments().getString(SizeFragment.EXTRA);
        mSizeView.setText("Taille: "+ mSize);

/***********************************************************************************/

/***********************************************************************************/
        mIngredientsView = (TextView) v.findViewById(R.id.ingredients_view);

        mIngredients = Singleton.getInstance().getIngredients();

        StringBuilder builder = new StringBuilder();
        for (String s: mIngredients) {
            builder.append(s+"\n");
        }
        if(mIngredients.size() == 0){
            mIngredientsView.setText("traditionnel gazpacho");
        }else
            mIngredientsView.setText("ingrédients: \n\n"+ builder.toString());

/***********************************************************************************/

/***********************************************************************************/
        mSpicyView = (TextView) v.findViewById(R.id.spicy_view);
        mSpicy= Singleton.getInstance().getAnswerSpice();
        mSpicyView.setText(mSpicy);

/***********************************************************************************/

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

                        fecha = "Date: "+ gmtTime2 +" "+gmtTime+"\n" ;
                        usbCtrl.sendMsg(fecha, "GBK", dev);

                        /****************************************/
                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);

                        StringBuilder builder = new StringBuilder();
                        for (String s: mIngredients) {
                            builder.append(s+"\n");
                        }
                        if(mIngredients.size() == 0){
                            ingredientes="traditionnel gazpacho";
                            usbCtrl.sendMsg(ingredientes, "GBK", dev);

                        }else {
                            ingredientes = "ingrédients: \n\n" + builder.toString();
                            usbCtrl.sendMsg(ingredientes, "GBK", dev);
                        }
                        /*******************************************/

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        chile = mSpicy;
                        usbCtrl.sendMsg(chile, "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        parallevar = mTakeaway;
                        usbCtrl.sendMsg(parallevar, "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        tamaño = mSize ;
                        usbCtrl.sendMsg(tamaño, "GBK", dev);


                        /*************************************************/

                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg("------------------------------\n", "GBK", dev);
                        cmd[2] |= 0x10;

                        /*************************************************/

                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg("COPIA\n", "GBK", dev);

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);

                        /*************************************************/


                        Locale locES = new Locale("es","ES");
                        DateFormat dfES = DateFormat.getDateInstance(DateFormat.FULL, locES);
                        DateFormat df = DateFormat.getTimeInstance();
                        df.setTimeZone(TimeZone.getDefault());
                        final String gmtTime = df.format(new Date());
                        final String gmtTime2 =dfES.format(date);

                        fecha = "Fecha: "+ gmtTime2 +" "+gmtTime+"\n" ;
                        usbCtrl.sendMsg(fecha, "GBK", dev);

                        /****************************************/
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
                        /*******************************************/

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        chile = mSpicy;
                        String chile2 = "avec épicé";
                        String withSpicy = "con chile";
                        String nonSpicy = "sin chile";

                        int spicy = chile.compareTo(chile2);

                        // prints the return value of the comparison
                        if (spicy == 0) {
                            usbCtrl.sendMsg(withSpicy, "GBK", dev);
                        }else {
                            usbCtrl.sendMsg(nonSpicy, "GBK", dev);
                        }

                        /*******************************************/

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        parallevar = mTakeaway;
                        String parallevar2 = "A emporter";
                        String eatHere = "para aqui";
                        String takeAway= "para llevar";

                        int paraLLevar = parallevar.compareTo(parallevar2);

                        if(paraLLevar == 0) {
                            usbCtrl.sendMsg(takeAway, "GBK", dev);

                        }else   {
                            usbCtrl.sendMsg(eatHere, "GBK", dev);
                        }


                        /*******************************************/

                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);
                        tamaño = mSize ;
                        String tamaño2 = "Grande \n\nPrix total: $ 20.00 (Vingt pesos mexicains 00/100 M.N) \n\n";
                        String big = "grande \n\nTotal a pagar: $20.00(Veinte pesos 00/100 M.N) \n\n";
                        String small = "chico \n\nTotal a pagar: $10.00(Diez pesos 00/100 M.N) \n\n";

                        int size = tamaño.compareTo(tamaño2);
                        if (size == 0) {
                            usbCtrl.sendMsg(big, "GBK", dev);
                        } else {
                            usbCtrl.sendMsg(small, "GBK", dev);
                        }

                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg("------------------------------\n", "GBK", dev);
                        cmd[2] |= 0x10;
                    }

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