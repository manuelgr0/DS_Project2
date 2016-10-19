package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;

public class XmlSensor extends AbstractSensor {
    private final String targeturl = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private final String urlParameters = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <S:Header/>\n" +
            "    <S:Body>\n" +
            "        <ns2:getDiscoveredSpots xmlns:ns2=\"http://webservices.vslecture.vs.inf.ethz.ch/\"/>\n" +
            "    </S:Body>\n" +
            "</S:Envelope>";

    @Override
    public String executeRequest() throws Exception {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //connection.setRequestProperty("Content-type", "text/xml; charset=utf-8");
            connection.setRequestProperty("Content-Type", "text/xml");

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is;
            Log.i("response", "code=" + connection.getResponseCode());
            if (connection.getResponseCode() <= 400) {
                is = connection.getInputStream();
            } else {
              /* error from server */
                is = connection.getErrorStream();
            }
            // is= connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            Log.i("response", "" + response.toString());
            return response.toString();
        } catch (Exception e) {
            Log.e("error https", "", e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }

}

