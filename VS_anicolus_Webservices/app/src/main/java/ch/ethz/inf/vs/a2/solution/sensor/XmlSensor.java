package ch.ethz.inf.vs.a2.solution.sensor;

import org.ksoap2.serialization.SoapObject;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;


public class XmlSensor extends AbstractSensor {
    @Override
    public String executeRequest() throws Exception {
        SoapObject soap = null;
        return null;
    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }
}
