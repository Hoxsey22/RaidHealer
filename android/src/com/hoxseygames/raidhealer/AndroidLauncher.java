package com.hoxseygames.raidhealer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements AdHandler{

	private static final String TAG = "AndroidLauncher";
	protected AdView adView;
	private final int HIDE_AD = 0;
	private final int SHOW_INTERSTITIAL_AD = 1;
	private final int SHOW_REWARD_AD = 2;
	private final int LOAD_INTERSTITIAL_AD = 3;
	private final int LOAD_REWARD_AD = 4;
	private final String rewardLocation = CBLocation.LOCATION_ITEM_STORE;
	private final String interstitialLocation = CBLocation.LOCATION_LEVEL_COMPLETE;
	SharedPreferences sharedPreferences = null;
	private Player player;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler()	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)	{
				case SHOW_REWARD_AD:
					if (Chartboost.hasRewardedVideo(rewardLocation)) {
						onStart();
						Chartboost.showRewardedVideo(rewardLocation);
						//Toast.makeText(getApplicationContext(), "SHOW REWARD!", Toast.LENGTH_SHORT).show();
					}
					break;
				case SHOW_INTERSTITIAL_AD:
					if(Chartboost.hasInterstitial(interstitialLocation))	{
						onStart();
						Chartboost.showInterstitial(interstitialLocation);
						//Toast.makeText(getApplicationContext(), "SHOW INTERSTITIAL", Toast.LENGTH_SHORT).show();
					}
					break;
				case LOAD_REWARD_AD:
					//Toast.makeText(getApplicationContext(), "LOAD REWARD ", Toast.LENGTH_SHORT).show();
					Chartboost.cacheRewardedVideo(rewardLocation);
					//loadRewardedVideoAd();
					break;
				case LOAD_INTERSTITIAL_AD:
					//Toast.makeText(getApplicationContext(), "LOAD INTERSTITIAL", Toast.LENGTH_SHORT).show();
					Chartboost.cacheInterstitial(interstitialLocation);
					//loadInterstitialAd();
					break;
				case HIDE_AD:
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get user premission
		if (Build.VERSION.SDK_INT >= 23) {
			this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},123);
		} else {
			initialize();
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


		initialize(new RaidHealer(this), config);

	}

	protected void initialize()	{

		Chartboost.startWithAppId(this,"5c9f05f6bcc8d00cddb0c43a","034df1f77d8e58d7cb20c44691136e532e8d20c1" );
		setuptSdkWithCustomSettings();
		Chartboost.onCreate(this);
		Chartboost.setActivityAttrs(this);
		Chartboost.setActivityCallbacks(false);
		Chartboost.setDelegate(delegate);
		Chartboost.setLoggingLevel(CBLogging.Level.ALL);




	}

	private void setuptSdkWithCustomSettings()	{
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		Chartboost.setShouldPrefetchVideoContent(
				sharedPreferences.getBoolean("enableVideoPrefetch", true));
		Chartboost.setShouldRequestInterstitialsInFirstSession(
				sharedPreferences.getBoolean("interstitialInFirstSession", true));
		Chartboost.setAutoCacheAds(
				sharedPreferences.getBoolean("enableAutoCache", true));
	}

	@Override
	public void showAds(int controller) {
		handler.sendEmptyMessage(controller);
	}

	@Override
	public void importPlayer(Player player) {
		this.player = player;
	}


	@Override
	public void onStart() {
		super.onStart();
		Chartboost.onStart(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		Chartboost.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		Chartboost.onPause(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		Chartboost.onStop(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Chartboost.onDestroy(this);
	}

	@Override
	public void onBackPressed() {
		// If an interstitial is on screen, close it.
		if (Chartboost.onBackPressed())
			return;
		else
			super.onBackPressed();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		initialize();
	}


	/**
	 * Chartboost Delegates
	 */

	public ChartboostDelegate delegate = new ChartboostDelegate() {


		/** Interstitial methods **/

		// Called before requesting an interstitial via the Chartboost API server.
		public boolean shouldRequestInterstitial(String location)	{
			/*String message = "Should request interstitial at "+location+"?";
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
			return true;
		}

		// Called before an interstitial will be displayed on the screen.
		public boolean shouldDisplayInterstitial(String location)	{
			/*String message = "Should display interstitial at "+location+"?";
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
			return true;
		}

		// Called after an interstitial has been displayed on the screen.
		public void didDisplayInterstitial(String location)	{
			/*String message = "Interstitial displayed";
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after an interstitial has been loaded from the Chartboost API
		// servers and cached locally.
		public void didCacheInterstitial(String location)	{
			/*String message = "Interstitial cached at "+location+"?";
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after an interstitial has attempted to load from the Chartboost API
		// servers but failed.
		public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error)	{
			/*String message = "Interstitial failed to load at " + location + " with error: " + error.name();
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after an interstitial has been dismissed.
		public void didDismissInterstitial(String location)	{
			/*String message = "Interstitial has been dismissed";
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after an interstitial has been closed.
		public void didCloseInterstitial(String location)	{
			/*String message = "Interstitial has been closed";
			Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after an interstitial has been clicked.
		public void didClickInterstitial(String location)	{
			/*String message = "Interstitial has been clicked";
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		/** Reward methods**/

		// Called before a rewarded video will be displayed on the screen.
		public boolean shouldDisplayRewardedVideo(String location)	{
			/*String message = "Should request reward at "+location+"?";
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
			return true;
		}

		// Called after a rewarded video has been displayed on the screen.
		public void didDisplayRewardedVideo(String location)	{
			/*String message = "Should reward reward at "+location+"?";
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after a rewarded video has been loaded from the Chartboost API
		// servers and cached locally.
		public void didCacheRewardedVideo(String location)	{
			/*String message = "Reward cached at "+location+"?";
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after a rewarded video has attempted to load from the Chartboost API
		// servers but failed.
		public void didFailToLoadRewardedVideo(String location, CBError.CBImpressionError error)	{
			//String message = "Reward failed to load at " + location + " with error: " + error.name();
			player.getTalentTree().reset();
			/*Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after a rewarded video has been dismissed.
		public void didDismissRewardedVideo(String location)	{
			/*String message = "Reward displayed";
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after a rewarded video has been closed.
		public void didCloseRewardedVideo(String location)	{
			/*String message = "Reward has been closed";
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after a rewarded video has been clicked.
		public void didClickRewardedVideo(String location)	{
			/*String message = "Reward has been clicked";
			Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		// Called after a rewarded video has been viewed completely and user is eligible for reward.
		public void didCompleteRewardedVideo(String location, int reward)	{
//			String message = "Reward has been earned.";
			player.getTalentTree().reset();
			/*Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
			Log.i("Chartboost Delegate:",message);*/
		}

		@Override
		public void didInitialize() {
			/*String message = "Chartboost SDK is initialized and ready!";
			Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
			System.out.println(message);
			Log.i("Chartboost Delegate:",message);*/
		}
	};
}
