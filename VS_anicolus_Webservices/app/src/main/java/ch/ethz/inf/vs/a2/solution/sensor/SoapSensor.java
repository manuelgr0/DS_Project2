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


/*TODO USE THE GUIDE : https://code.tutsplus.com/tutorials/consuming-web-services-with-ksoap--mobile-21242
 *TODO in the guide he used VER11 instead of VER12 (just in case of a error) */

public class SoapSensor extends AbstractSensor {
    private static final String NAMESPACE = "http://webservices.vslecture.vs.inf.ethz.ch/";
    private static final String MAIN_REQUEST_URL = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private static final String SOAP_ACTION = "http://webservices.vslecture.vs.inf.ethz.ch/getSpot";

    @Override
    public String executeRequest() throws Exception {
        String result = null;

        //Creating/Configuring the envelope
        String methodname = "getSpot";
        SoapObject request = new SoapObject(NAMESPACE, methodname);
        request.addProperty("id", "Spot3");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        //envelope.implicitTypes = true;
        //envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        //Creating the request
        HttpTransportSE ht = new HttpTransportSE(MAIN_REQUEST_URL);
        ht.debug = true;
        //ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        try {
            //Sending the request
            ht.call(SOAP_ACTION, envelope);

            Log.d("hallo", "-------------");

            //Processing the response
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
