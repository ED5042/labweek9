package ie.ul.android.lab_week9;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FreeFallService extends Service {

	
	@Override
	public void onCreate() {

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		// replace below return statement with correct one
		return 0;
	}

	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
