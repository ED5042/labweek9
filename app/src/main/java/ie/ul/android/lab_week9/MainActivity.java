/* Student name: 
 * Student id:
 * Partner name:
 * Partner id:
 */

package ie.ul.android.lab_week9;


import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ie.ul.android.lab_week9.FreeFallService;

public class MainActivity extends Activity {

    private SensorManager sensorMan;
    private boolean isFreeFalling = false;
    private boolean isPlaying = false;
    private boolean isServiceRunning = false;

    private static final float FREE_FALLING = 1; // below 1 m/s^2 is free falling
    private static String FREEFALLINGSNIPPET = "fall.wav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    final SensorEventListener accSensorListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // do nothing

        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float values[] = sensorEvent.values;
        }

    };

    private void playFreeFalling() {
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();
        if (!isPlaying) {
            try {
                AssetFileDescriptor descriptor = getAssets().openFd(FREEFALLINGSNIPPET);
                mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
                mp.prepare();
                mp.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer arg0) {
                        isPlaying = false;

                    }

                });
                mp.start();
                isPlaying = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (menu != null) {
            findServiceIfAlreadyRunning();
            if (isServiceRunning == true) {
                menu.findItem(R.id.startstop).setTitle(R.string.stop_service);
            } else {
                menu.findItem(R.id.startstop).setTitle(R.string.start_service);
            }
        }

        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.startstop:


                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Verifies if the service is already running. If this is
     * the case, we should update the UI
     */
    private void findServiceIfAlreadyRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(50);

        for (int i = 0; i < runningServices.size(); i++) {
            ActivityManager.RunningServiceInfo runningService = runningServices.get(i);

            if (runningService.service.getClassName().equalsIgnoreCase(FreeFallService.class.getName())) {
                isServiceRunning = true;
            }
        }
    }


}
