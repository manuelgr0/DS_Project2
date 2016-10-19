package ch.ethz.inf.vs.a2.solution.sensor;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;


/*TODO USE THE GUIDE : https://code.tutsplus.com/tutorials/consuming-web-services-with-ksoap--mobile-21242
 *TODO in the guide he used VER11 instead of VER12 (just in case of a error) */

public class SoapSensor extends AbstractSensor {
    @Override
    public String executeRequest() throws Exception {
        return null;
    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }
}
