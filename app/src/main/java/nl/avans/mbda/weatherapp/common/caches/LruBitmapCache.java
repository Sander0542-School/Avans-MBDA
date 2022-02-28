package nl.avans.mbda.weatherapp.common.caches;

import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.android.volley.toolbox.ImageLoader;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        return maxMemory / 8;
    }

    public LruBitmapCache() {
        super(getDefaultLruCacheSize());
    }

    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    @Nullable
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
