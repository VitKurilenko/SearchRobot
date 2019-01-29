package searchrobot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Crawler {
    public static int getHttpCode(String str) throws IOException {

        URL url = new URL(str);
        int i = 0;
        while (i < 3) {
            try {
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);

                connection.connect();
                int code = connection.getResponseCode();
                connection.disconnect();

                return code;
            } catch (java.net.SocketTimeoutException e) {
                return 522;
            } catch (java.net.UnknownHostException e) {
                return 522;
            } catch (java.net.SocketException e) {
                i++;
            }
        }
        return 522;
    }
}
