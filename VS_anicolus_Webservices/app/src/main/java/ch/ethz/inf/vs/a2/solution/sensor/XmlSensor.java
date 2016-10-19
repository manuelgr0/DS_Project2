package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;

public class XmlSensor extends AbstractSensor {
    private final String targeturl = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private final String urlParameters = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <S:Header/>\n" +
            "    <S:Body>\n" +
            "        <ns2:getSpot xmlns:ns2=\"http://webservices.vslecture.vs.inf.ethz.ch/\">\n" +
            "            <id>Spot3</id>\n" +
            "        </ns2:getSpot>\n" +
            "    </S:Body>\n" +
            "</S:Envelope>";

    @Override
    public String executeRequest() throws Exception {
        URL url;
        HttpURLConnection connection = null;

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

        String ret = convertStreamToString(is);

            /* is= connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            Log.i("response", "" + response.toString());
            return response.toString(); */

        if (connection != null) {
            connection.disconnect();
        }

        return ret;


    }

    @Override
    public double parseResponse(String response) {
        Log.d("Parser is called : ", "starting parsing");
        Double ret = -1.0;
        int event;
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            xmlFactoryObject.setNamespaceAware(true);
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setInput(new StringReader(response));

            Log.d("parsing response", response);

            //Catch event
            event = myparser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                System.out.println("looping");
                if (event == XmlPullParser.START_TAG && myparser.getName().equals("temperature")) {
                    myparser.next();
                    ret = Double.valueOf(myparser.getText());
                    System.out.println("temperatre is " + ret);
                    break;
                }
                event = myparser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }


}

