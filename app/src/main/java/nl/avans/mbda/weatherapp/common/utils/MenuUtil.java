package nl.avans.mbda.weatherapp.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.Locale;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.models.onecall.OneCall;

public class MenuUtil {
    public static boolean openMaps(Activity activity, final int itemId, final OneCall oneCall) {
        if (itemId == R.id.menu_item_maps && oneCall != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.US, "geo:%f,%f", oneCall.getLat(), oneCall.getLon())));
            intent.setPackage("com.google.android.apps.maps");
            activity.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean changeBackground(BackgroundUtil backgroundUtil, final int itemId) {
        if (itemId == R.id.menu_item_background) {
            backgroundUtil.change();
            return true;
        }
        return false;
    }
}
