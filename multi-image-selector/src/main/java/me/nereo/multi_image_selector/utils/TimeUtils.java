package me.nereo.multi_image_selector.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具
 * Created by Nereo on 2015/4/8.
 */
public class TimeUtils {

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }

    private static String formatPhotoDate(long time) {
        return timeFormat(time);
    }

    private static String timeFormat(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_PATTERN, Locale.getDefault());
        return format.format(new Date(timeMillis));
    }
}
