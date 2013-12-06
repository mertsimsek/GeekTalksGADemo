package org.gdgankara.geektalks.gademo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class MainActivity extends Activity {

	private Button ilkButton;
	private Button ikinciButton;
	private Button btnGo;

	private Tracker tracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tracker = GoogleAnalytics.getInstance(this).getTracker("UA-46237883-1");

		ilkButton = (Button) findViewById(R.id.ilkEvent);
		ikinciButton = (Button) findViewById(R.id.ikinciEvent);
		btnGo = (Button) findViewById(R.id.goButton);

		ilkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EasyTracker.getInstance(MainActivity.this).send(
						MapBuilder.createEvent("Main View", "Button Click",
								ilkButton.getText().toString(), (long) 0)
								.build());

			}
		});

		ikinciButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EasyTracker.getInstance(MainActivity.this).send(
						MapBuilder.createEvent("Main View", "Button Click",
								ikinciButton.getText().toString(), (long) 0)
								.build());
			}
		});

		btnGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EasyTracker.getInstance(MainActivity.this).send(
						MapBuilder.createEvent("Main View", "Button Click",
								btnGo.getText().toString(), (long) 0).build());

				Intent i = new Intent(MainActivity.this, IkinciActivity.class);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();

		EasyTracker.getInstance(this).activityStart(this);

		Uri uri = getIntent().getData();
		tracker.set(Fields.SCREEN_NAME, "Main View");
		MapBuilder mb = new MapBuilder();

		if (uri != null) {
			if (uri.getQueryParameter("twitter") != null) {

				mb.setCampaignParamsFromUrl(uri.toString());

			} else if (uri.getAuthority() != null) {

				mb.set(Fields.CAMPAIGN_MEDIUM, "referral");
				mb.set(Fields.CAMPAIGN_SOURCE, uri.getAuthority());

			}
		}
		MapBuilder.createAppView().setAll(mb.build());

	}

	@Override
	protected void onStop() {
		super.onStop();

		EasyTracker.getInstance(this).activityStop(this);

	}

}
