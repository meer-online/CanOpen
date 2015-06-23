package com.soundsoftware.canopen;

import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class CanOpen extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {

		PluginResult pr;
		boolean isPackageName = false;
		if(args.length > 1)
			isPackageName = args.getBoolean(1);

		if(isPackageName){
			List<ApplicationInfo> packages = null;
        	PackageManager packageManager = null;
        	boolean isIntentSafe = false;
        	packageManager = cordova.getActivity().getPackageManager();
        	packages = packageManager.getInstalledApplications(0);
        	for (ApplicationInfo packageInfo : packages) {
        		if(packageInfo.packageName.equals(args.getString(0)))
        			isIntentSafe = true;
        	}
        	pr = new PluginResult(Status.OK, isIntentSafe);
        	callbackContext.sendPluginResult(pr);
        	return false;
		}else{
			Uri uriToTest = Uri.parse(args.getString(0) + "://");
			Intent intent = new Intent(Intent.ACTION_DEFAULT, uriToTest);
			PackageManager packageManager = cordova.getActivity().getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
			boolean isIntentSafe = activities.size() > 0;
			pr = new PluginResult(Status.OK, isIntentSafe);
			callbackContext.sendPluginResult(pr);
			return false;
		}
	}
}
