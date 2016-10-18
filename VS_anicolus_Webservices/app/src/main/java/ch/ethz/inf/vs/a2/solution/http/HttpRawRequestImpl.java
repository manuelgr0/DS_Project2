package ch.ethz.inf.vs.a2.solution.http;

import ch.ethz.inf.vs.a2.http.HttpRawRequest;


public class HttpRawRequestImpl implements HttpRawRequest {
    @Override
    public String generateRequest(String host, int port, String path) {

        StringBuilder str = new StringBuilder(512);
        str.append("GET ").append(path).append( " HTTP/1.1\r\n")
                .append("Host: ").append(host).append(":").append(String.valueOf(port)).append("\r\n")
                .append("Accept: text/html\r\n")
                .append("Connection: close\r\n\r\n");
        return str.toString();

    }
}