package com.gazpacho.lgramir.gazpacho_test;


import android.app.Activity;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zj.usbsdk.UsbController;


public class UsbConnect extends Activity {



    private Button btn_conn = null;
    private Button btn_test = null;

    private int[][] u_infor;
    UsbController usbCtrl = null;
    UsbDevice dev = null;
    private View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_usb);


        btn_conn = (Button)findViewById(R.id.btnSearch);
        btn_test = (Button)findViewById(R.id.btn_start);

        btn_conn.setOnClickListener(new ClickEvent());
        btn_test.setOnClickListener(new ClickEvent());

        btn_test.setEnabled(false);

        usbCtrl = new UsbController(this,mHandler);
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
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_getpermission),
                            Toast.LENGTH_SHORT).show();
                    btn_test.setEnabled(true);
                    btn_conn.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };


    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if( v == btn_conn ){
                /*

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
                        Toast.makeText(getApplicationContext(), getString(R.string.msg_getpermission),
                                Toast.LENGTH_SHORT).show();
                        btn_test.setEnabled(true);
                        btn_conn.setEnabled(false);
                    }
                }
                */
                Intent i = new Intent(UsbConnect.this, ChoiceGazpachoActivity.class);
                startActivity(i);

            }else if( v == btn_test ){
                Intent i = new Intent(UsbConnect.this, LanguageActivity.class);
                startActivity(i);
            }
        }
    }

}