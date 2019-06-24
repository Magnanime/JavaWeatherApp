import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Date;

public class JsonHandler{

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String getWeather(String id) throws IOException, JSONException{
        String weather = "weather";
        JSONObject json = readJsonFromUrl(
                "http://api.openweathermap.org/data/2.5/weather?id="+id+"&APPID=fee14863bb3e9f2f3214db343025a85f");
        weather = getDate(json) + ":" + getCity(json) + ":" + getTemp(json) + ":" + getHumidity(json);
        return weather;
    }

    public static String getDate(JSONObject localJson) throws JSONException {
        String dt = localJson.get("dt").toString();
        Date date = Date.from( Instant.ofEpochSecond( Integer.parseInt(dt) ) );
        return date.toString();
    }

    public static String getCity(JSONObject localJson) throws JSONException {
        String city = localJson.get("name").toString();
        return city;
    }

    public static String getTemp(JSONObject localJson) throws JSONException {
        JSONObject jsonTmp = (JSONObject) localJson.get("main");
        String temp = jsonTmp.get("temp").toString();
        Float tempC = Float.parseFloat(temp) - (float)273.15;
        return String.format("%.1f C", tempC);
    }

    public static String getHumidity(JSONObject localJson) throws JSONException {
        JSONObject jsonTmp = (JSONObject) localJson.get("main");
        String humidity = jsonTmp.get("humidity").toString();
        return humidity + "%";
    }
}