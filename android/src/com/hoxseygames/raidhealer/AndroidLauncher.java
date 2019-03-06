package com.hoxseygames.raidhealer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements AdHandler,RewardedVideoAdListener{

	private static final String TAG = "AndroidLauncher";
	private RewardedVideoAd rewardedVideoAd;
	private InterstitialAd interstitialAd;
	protected AdView adView;
	private final int HIDE_AD = 0;
	private final int SHOW_INTERSTITIAL_AD = 1;
	private final int SHOW_REWARD_AD = 2;
	private final int LOAD_INTERSTITIAL_AD = 3;
	private final int LOAD_REWARD_AD = 4;
	private Player player;


	@SuppressLint("HandlerLeak")
	Handler handler = new Handler()	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)	{
				case SHOW_REWARD_AD:
					if (rewardedVideoAd.isLoaded()) {
						rewardedVideoAd.show();
						Toast.makeText(getApplicationContext(), "SHOW REWARD!", Toast.LENGTH_SHORT).show();
					}
					break;
				case SHOW_INTERSTITIAL_AD:
					if(interstitialAd.isLoaded())	{
						interstitialAd.show();
						Toast.makeText(getApplicationContext(), "SHOW INTERSTITIAL", Toast.LENGTH_SHORT).show();
					}
					break;
				case LOAD_REWARD_AD:
					Toast.makeText(getApplicationContext(), "LOAD REWARD ", Toast.LENGTH_SHORT).show();
					loadRewardedVideoAd();
					break;
				case LOAD_INTERSTITIAL_AD:
					Toast.makeText(getApplicationContext(), "LOAD INTERSTITIAL", Toast.LENGTH_SHORT).show();
					loadInterstitialAd();
					break;
				case HIDE_AD:
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//RelativeLayout layout = new RelativeLayout(this);
		MobileAds.initialize(this, "ca-app-pub-9873789688498401~4137204680");

		rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedVideoAd.setRewardedVideoAdListener(this);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-9873789688498401/8504836869");
		//loadInterstitialAd();

		//loadRewardedVideoAd();
		// This is the Raid Healer's ad id
		//rewardedVideoAd.setAdUnitId("ca-app-pub-9873789688498401~4137204680");

		// This is the test ID
		//rewardedVideoAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		//AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
		//rewardedVideoAd.loadAd(request);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//View gameView = initializeForView(new RaidHealer(this));
		//layout.addView(gameView);

		initialize(new RaidHealer(this), config);
	}

	private void loadRewardedVideoAd()	{
		rewardedVideoAd.loadAd("ca-app-pub-9873789688498401/3227780832", new AdRequest.Builder().build());
		//rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
	}

	private void loadInterstitialAd()	{
		interstitialAd.loadAd(new AdRequest.Builder().build());
		//interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
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
	public void onRewarded(RewardItem reward) {
		Toast.makeText(this, "Healer's talents are now cleared.", Toast.LENGTH_SHORT).show();
		// Reward the user.
		player.getTalentTree().reset();

	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
		Toast.makeText(this, "onRewardedVideoAdLeftApplication",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdClosed() {
		Toast.makeText(this, "Reward Ad closed. Reload next Reward Ad.", Toast.LENGTH_SHORT).show();
		loadRewardedVideoAd();
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int errorCode) {
		Toast.makeText(this, "onRewardedVideoAdFailedToLoad - Error Code: "+errorCode, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		Toast.makeText(this, "Reward Ad is now loaded!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdOpened() {
		Toast.makeText(this, "Reward Ad is now opened.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoStarted() {
		Toast.makeText(this, "Reward Ad has now started.", Toast.LENGTH_SHORT).show();
	}
}
