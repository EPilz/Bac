package inso.revex_app_resty.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Elisabeth on 09.04.2015.
 */
public class PropertiesFileUtility {

    public static String getValue(String filename, String key) throws IOException {
        String result = "";
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
            return prop.getProperty(key);
        }

        return null;
    }
}
