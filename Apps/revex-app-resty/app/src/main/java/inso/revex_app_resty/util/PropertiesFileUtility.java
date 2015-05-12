package inso.revex_app_resty.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import inso.revex_app_resty.R;

/**
 * Created by Elisabeth on 09.04.2015.
 */
public class PropertiesFileUtility {

    private final static long MINUTE_MILLIS = 60000;
    private final static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private final static String FILE_NAME = "config.properties";

    public static String getValue(Context context, String key)  {
        try {
            InputStream inputStream = context.getResources().getAssets().open("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setValue(Context context, String key, String value) {
        try {
            InputStream inputStream = context.getResources().getAssets().open("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            properties.setProperty(key, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int minutesDiffFromToken(Context context) {
        String tokenTime = PropertiesFileUtility.getValue(context, "tokenTime");
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date tokenDate = formatter.parse(tokenTime);
            Date currentDate = new Date();

            return (int)((currentDate.getTime()/MINUTE_MILLIS) - (tokenDate.getTime()/MINUTE_MILLIS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setCurrentDate(Context context, String key) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        setValue(context, key, formatter.format(new Date()));
    }
}
