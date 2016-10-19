package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.Proxy;

import ch.ethz.inf.vs.a2.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.sensor.AbstractSensor;

public class SoapSensor extends AbstractSensor {
    private static final String NAMESPACE = "http://webservices.vslecture.vs.inf.ethz.ch/";
    private static final String MAIN_REQUEST_URL = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private static final String SOAP_ACTION = "http://webservices.vslecture.vs.inf.ethz.ch/getSpot";
    private static final String METHOD_NAME = "getSpot";


    @Override
    public String executeRequest() throws Exception {
        //Creating/Configuring the envelope
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("id", "Spot3");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        Log.d("request:     ", request.toString());

        //Creating the request
        HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
        ht.debug = true;
        try {
            //Sending the request
            ht.call(SOAP_ACTION, envelope);

            Log.d("hallo", "-------------");

            //Processing the response
            SoapObject response = (SoapObject) envelope.getResponse();

            Log.d("This is the response:  ", response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double parseResponse(String response) {
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


}
