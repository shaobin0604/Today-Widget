package com.qq.mail.shaobin0604.today;

import java.util.Arrays;
import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * A BroadcastReceiver that listens for updates for the ExampleAppWidgetProvider.  This
 * BroadcastReceiver starts off disabled, and we only enable it when there is a widget
 * instance created, in order to only receive notifications when we need them.
 */
public class TodayBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = TodayBroadcastReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		

        // For our example, we'll also update all of the widgets when the timezone
        // changes, or the user or network sets the time.
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)
                || action.equals(Intent.ACTION_TIME_CHANGED)) {
        	
        	Log.d(TAG, "intent=" + intent);
        	
        	Calendar calendar = Calendar.getInstance();
    		
    		int month = calendar.get(Calendar.MONTH);
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        	
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, TodayAppWidgetProvider.class);
            
            Log.d(TAG, "Component name -- " + context.toString());
			int[] appWidgetIds = gm.getAppWidgetIds(componentName);
            Log.d(TAG, Arrays.toString(appWidgetIds));
            
            int size = appWidgetIds.length;
            for (int i=0; i < size; i++) {
                TodayAppWidgetProvider.updateAppWidget(context, gm, appWidgetIds[i], month, weekday, day);
            }
        }
	}

}
