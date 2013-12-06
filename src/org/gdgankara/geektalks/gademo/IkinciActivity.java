package org.gdgankara.geektalks.gademo;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class IkinciActivity extends Activity {

	private WebView webEngine;
	private EditText input;
	private Button buttonBrowse;
	private Button hataYap;
	private String webAdress;
	private Tracker tracker;

	private long startTime, endTime, duration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ikinci);

		// Create a Tracker object
		tracker = GoogleAnalytics.getInstance(this).getTracker("UA-46237883-1");

		// Setting up view objects;
		webEngine = (WebView) findViewById(R.id.web);
		input = (EditText) findViewById(R.id.webAdressInput);
		buttonBrowse = (Button) findViewById(R.id.btnWebClick);
		hataYap = (Button) findViewById(R.id.hataButton);

		webEngine.getSettings().setJavaScriptEnabled(true);
		webEngine.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				startTime = System.currentTimeMillis();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				tracker.send(MapBuilder.createEvent("Second View",
						"Web View Loaded", url, (long) 0).build());
				endTime = System.currentTimeMillis();

				duration = endTime - startTime;
				tracker.send(MapBuilder.createTiming("Second View", duration,
						"Web Load Time", url).build());
			}

		});

		buttonBrowse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webAdress = input.getText().toString();
				webAdress = "http://" + webAdress;

				tracker.send(MapBuilder.createEvent("Second View",
						"Button Click", buttonBrowse.getText().toString(),
						(long) 0).build());
				webEngine.loadUrl(webAdress);

			}
		});

		hataYap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int[] array = { 45, 23, 54698, 5321, 78, 555 };

				try {
					for (int i = 0; i < array.length + 1; i++) {
						Log.i("GA DEMO", String.valueOf(array[i]));
					}
				} catch (Exception e) {
					tracker.send(MapBuilder.createException(e.getMessage(),
							true).build());
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ikinci, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();

		tracker.send(MapBuilder.createAppView()
				.set(Fields.SCREEN_NAME, "Ikinci Activity").build());

	}

	@Override
	protected void onStop() {
		super.onStop();

		EasyTracker.getInstance(this).activityStop(this);

	}

}
