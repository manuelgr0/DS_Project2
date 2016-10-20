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

        //Creating the request
        HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
        ht.debug = true;
        try {
            //Sending the request
            ht.call(SOAP_ACTION, envelope);

            //Processing the response
            SoapObject response = (SoapObject) envelope.getResponse();

            return response.getProperty("temperature").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double parseResponse(String response) {
        System.out.println("temperatre is " + response);
        return Double.valueOf(response);
    }


}
