package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainOptionsActivity extends Activity {
	int leftPadding = 0, rightPadding = 0, topPadding = 0, bottomPadding = 0;
	LinearLayout mainRelativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainoptions);
		mainRelativeLayout = (LinearLayout) findViewById(R.id.mainRelativeLayout);
		displayMainOptions();
	}

	public void displayMainOptions() {
		for (int i = 0; i < 9; i++) {
			ImageView mainOptionImageView = new ImageView(
					MainOptionsActivity.this);
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			int width = size.x;
			int height = size.y;
			LayoutParams mainOptionsLayoutParams = new LayoutParams(
					(width / 3) - 100, (width / 3) - 100);
			mainOptionsLayoutParams.setMargins(leftPadding, topPadding,
					rightPadding, bottomPadding);

			if (i == 0) {
				leftPadding = 50;
				rightPadding = 50;
				topPadding = 10;
				mainOptionsLayoutParams.setMargins(leftPadding, topPadding,
						rightPadding, bottomPadding);
				mainOptionImageView.setLayoutParams(mainOptionsLayoutParams);

			} else if (i == 1) {
				leftPadding = 50;
				rightPadding = 50;
				topPadding = 10;
				mainOptionsLayoutParams.setMargins(leftPadding, topPadding,
						rightPadding, bottomPadding);
				mainOptionImageView.setLayoutParams(mainOptionsLayoutParams);

			} else if (i == 2) {
				leftPadding = 50;
				rightPadding = 50;
				topPadding = 10;
				mainOptionsLayoutParams.setMargins(leftPadding, topPadding,
						rightPadding, bottomPadding);
				mainOptionImageView.setLayoutParams(mainOptionsLayoutParams);
			}
			mainRelativeLayout.addView(mainOptionImageView);
		}
	}
}
