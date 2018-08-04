package com.me.notify.lightsoff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.WindowManager;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private WakeLock mWakeLock;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       try{

            // Get an instance of the SensorManager
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            // Get an instance of the PowerManager
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

            // Get an instance of the WindowManager
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.getDefaultDisplay();

            // Create a bright wake lock
            mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                    .getName());

           textView = (TextView)findViewById(R.id.textView1);
           button = (Button)findViewById(R.id.button1);

           button.setOnClickListener(mButtonStopListener);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("onCreate", e.getMessage());
        }
    } // END onCreate

    View.OnClickListener mButtonStopListener = new OnClickListener() {
        public void onClick(View v) {
            try {
                mWakeLock.release();
                textView.setText("mWakeLock.release()");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e("onPause",e.getMessage());
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
	        /*
	         * when the activity is resumed, we acquire a wake-lock so that the
	         * screen stays on, since the user will likely not be fiddling with the
	         * screen or buttons.
	         */

        try {
            mWakeLock.acquire();
            textView.setText("mWakeLock.acquire()");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("onResume", e.getMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // and release our wake-lock
        try {
            mWakeLock.release();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("onPause",e.getMessage());
        }
    }
}




