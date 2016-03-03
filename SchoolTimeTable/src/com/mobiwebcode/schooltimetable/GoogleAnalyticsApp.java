package com.mobiwebcode.schooltimetable;

import android.app.Application;

public class GoogleAnalyticsApp extends Application {

	// change the following line
	private static final String PROPERTY_ID = "UA-xxxxxxxxx-1";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
		APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
	}


	public GoogleAnalyticsApp() {
		super();
	}

}