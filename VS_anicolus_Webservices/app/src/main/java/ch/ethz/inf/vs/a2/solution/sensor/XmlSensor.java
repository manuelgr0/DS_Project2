package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;


/*TODO USE THE GUIDE : https://code.tutsplus.com/tutorials/consuming-web-services-with-ksoap--mobile-21242
 *TODO in the guide he used VER11 instead of VER12 (just in case of a error */

public class XmlSensor extends AbstractSensor {
    private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    private static final String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";

    @Override
    public String executeRequest() throws Exception {
        String methodname = "FahrenheitToCelsius";
        SoapObject request = new SoapObject(NAMESPACE, methodname);
        // Example of a addProperty
        //request.addProperty("Fahrenheit", fValue);
        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        ht.call(SOAP_ACTION, envelope);

        SoapPrimitive resultsString = (SoapPrimitive)envelope.getResponse();
        Log.d("Unparsed Response", resultsString.toString());

        return null;
    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }

    //TODO (manuelgr) check why private final isnt possible... something false?
    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        return envelope;
    }
    private final HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,MAIN_REQUEST_URL,60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }
}

