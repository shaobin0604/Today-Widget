package com.qq.mail.shaobin0604.today;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.RemoteViews;

public class TodayAppWidgetProvider extends AppWidgetProvider {
	private static final String TAG = TodayAppWidgetProvider.class.getSimpleName();

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.d(TAG, "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// When the last widget is removed, stop listening for the TIMEZONE_CHANGED and
        // TIME_CHANGED broadcasts.
		
        Log.d(TAG, "onDisabled");
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(context, TodayBroadcastReceiver.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
	}

	@Override
	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled");
        // When the first widget is created, register for the TIMEZONE_CHANGED and TIME_CHANGED
        // broadcasts.  We don't want to be listening for these if nobody has our widget active.
        // This setting is sticky across reboots, but that doesn't matter, because this will
        // be called after boot if there is a widget instance for this provider.
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(context, TodayBroadcastReceiver.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive intent -- " + intent);
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "onUpdate");
		
		Calendar calendar = Calendar.getInstance();
		
		
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		
        // For each widget that needs an update, get the text that we should display:
        //   - Create a RemoteViews object for it
        //   - Set the text in the RemoteViews object
        //   - Tell the AppWidgetManager to show that views object for the widget.
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i], month, weekday, day);
        }
	}

	/**
	 * 
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetId
	 * @param month zero-based the first month of year is 0
	 * @param weekday one-based the first day of week is 1(Sun.)
	 * @param day one-based the first day of month is 1
	 */
	static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId, int month, int weekday, int day) {
		String[] monthNames = context.getResources().getStringArray(R.array.month_names);
		String[] weekdayNames = context.getResources().getStringArray(R.array.weekday_names);
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		
        views.setTextViewText(R.id.month_of_year, monthNames[month]);
        views.setTextViewText(R.id.day_of_week, weekdayNames[weekday - 2 < 0 ? 6 : weekday - 2]);
        views.setTextViewText(R.id.day_of_month, String.valueOf(day));
        
        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);
	}

}
