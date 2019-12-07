package ir.tapsell.sdk;

import android.support.annotation.NonNull;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import ir.tapsell.plus.AdRequestCallback;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.TapsellPlus;

public class TapsellPlusCordovaInterface extends CordovaPlugin {

	private static final String TAG = "TapsellPlusCordova";
	public CordovaInterface cordova = null;

	@Override
	public void initialize(CordovaInterface initCordova, CordovaWebView webView) {
		Log.e(TAG, "initialize");
		cordova = initCordova;
		super.initialize(cordova, webView);
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		switch (action) {
			case "initialize":
				initialize(args, callbackContext);
				return true;
			case "requestAd":
				requestAd(args, callbackContext);
				return true;
			case "showAd":
				showAd(args, callbackContext);
				return true;
			case "setDebugMode":
				setDebugMode(args, callbackContext);
				return true;
		}
		return false;
	}

	private void setDebugMode(JSONArray args, CallbackContext callbackContext) throws JSONException {
		final int debug = args.getInt(0);
		TapsellPlus.setDebugMode(debug);
		callbackContext.success();
	}

	private void initialize(JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String appKey = args.getString(0);
		TapsellPlus.initialize(cordova.getActivity(), appKey);
		callbackContext.success();
	}

	private void requestAd(JSONArray args, final CallbackContext callbackContext) throws JSONException {
		String zoneId = args.getString(0);
		if (zoneId != null && (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase("")))
			zoneId = null;
		TapsellPlus.requestRewardedVideo(cordova.getActivity(), zoneId, new AdRequestCallback() {
			@Override
			public void response() {
				Log.d(TAG, "Ad Response");
				PluginResult resultado = new PluginResult(PluginResult.Status.OK);
				resultado.setKeepCallback(true);
				callbackContext.sendPluginResult(resultado);
			}

			@Override
			public void error(@NonNull String message) {
				Log.e("error", message);
				PluginResult resultado = new PluginResult(PluginResult.Status.ERROR, message);
				resultado.setKeepCallback(true);
				callbackContext.sendPluginResult(resultado);
			}
		});
	}

	private void showAd(JSONArray args, final CallbackContext callbackContext) throws JSONException {
		String zoneId = args.getString(0);
		if (zoneId != null && (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase("")))
			zoneId = null;
		TapsellPlus.showAd(cordova.getActivity(), zoneId,
				new AdShowListener() {
					@Override
					public void onOpened() {
						Log.d(TAG, "Ad Opened");
						PluginResult resultado = new PluginResult(PluginResult.Status.OK, "onOpened");
						resultado.setKeepCallback(true);
						callbackContext.sendPluginResult(resultado);
					}

					@Override
					public void onClosed() {
						Log.d(TAG, "Ad Closed");
						PluginResult resultado = new PluginResult(PluginResult.Status.OK, "onClosed");
						resultado.setKeepCallback(true);
						callbackContext.sendPluginResult(resultado);
					}

					@Override
					public void onRewarded() {
						Log.d(TAG, "Reward");
						PluginResult resultado = new PluginResult(PluginResult.Status.OK, "onRewarded");
						resultado.setKeepCallback(true);
						callbackContext.sendPluginResult(resultado);
					}

					@Override
					public void onError(String message) {
						Log.e(TAG, "Error:" + message);
						PluginResult resultado = new PluginResult(PluginResult.Status.ERROR, message);
						resultado.setKeepCallback(true);
						callbackContext.sendPluginResult(resultado);
					}
				});
	}
}
