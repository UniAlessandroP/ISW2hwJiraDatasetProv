package app.com.isw2ticketjira.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetUtils {

    public NetUtils() {
    }

    public static String readAllIntoString(Reader r) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = r.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String getStringFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAllIntoString(rd);
        } finally {
            is.close();
        }
    }

    public static JSONObject getJsonFromUrl(String url) throws IOException, JSONException {
        return new JSONObject(getStringFromUrl(url));
    }

    public static JSONArray getJsonArrayFromUrl(String url) throws IOException, JSONException {
        return new JSONArray(getStringFromUrl(url));
    }
}
