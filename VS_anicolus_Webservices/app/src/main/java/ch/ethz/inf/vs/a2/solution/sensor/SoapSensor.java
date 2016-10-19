package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;

import ch.ethz.inf.vs.a2.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.sensor.AbstractSensor;


//TODO USE THE GUIDE : https://code.tutsplus.com/tutorials/consuming-web-services-with-ksoap--mobile-21242

public class SoapSensor extends AbstractSensor {
    private static final String NAMESPACE = "http://webservices.vslecture.vs.inf.ethz.ch/";
    private static final String MAIN_REQUEST_URL = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private static final String SOAP_ACTION = "http://webservices.vslecture.vs.inf.ethz.ch/getSpot";
    private static final String METHOD_NAME = "getSpot";


    @Override
    public String executeRequest() throws Exception {
        String result;

        //Creating/Configuring the envelope
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("id", "Spot3");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        //envelope.implicitTypes = true;
        //envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        Log.d("request:     " , request.toString());

        //Creating the request
        HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
        ht.debug = true;
        //ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        try {
            //Sending the request
            ht.call(SOAP_ACTION, envelope);

            Log.d("hallo", "-------------");

            //Processing the response
            Log.d("before response" , envelope.bodyIn.toString());
            SoapPrimitive resultsString = (SoapPrimitive) envelope.getResponse();

            Log.d("eeeeeeeeeeee", "eeeeeeeeeeeeee");

            Log.d("This is the response:  ", resultsString.toString());
            result = resultsString.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }

}
