package com.example.xiangwan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.os.Handler;
import android.util.Log;
import android.database.ContentObserver;
import android.widget.SeekBar;

public class SettingActivity extends AppCompatActivity {

    private SeekBar seekbar;
    private ContentObserver mVoiceObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //get media system service
        seekbar= (SeekBar) findViewById(R.id.seekBar);
        seekbar.setMax(15);//set max sound volume
        seekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        // current sound volume
        myRegisterReceiver();//sycn

        Log.i("lyj_ring", "mVoiceSeekBar max voluem = "
                +audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                Log.v("lyj_ring", "mVoiceSeekBar max progress = "+arg1);
                //sycn system and media sound volume
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, arg1, 0);
                audioManager.setStreamVolume(3, arg1, 0);
                //  3 = AudioManager.STREAM_MUSIC
            }
        });
        mVoiceObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                seekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
            }
        };
    }

    private void myRegisterReceiver(){
        MyVolumeReceiver  mVolumeReceiver = new MyVolumeReceiver() ;
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.media.VOLUME_CHANGED_ACTION") ;
        registerReceiver(mVolumeReceiver, filter) ;
    }

    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Change the location of seekbar if the volume changes.
            if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;
                // Current media volume.
                seekbar.setProgress(currVolume) ;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button.
        int id = item.getItemId();
        if (id == R.id.action_Menu) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_Game) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_Settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_GameProgress) {
            Intent intent = new Intent(this, GameProgressActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void GotoLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}