/**
 * This file is part of SensApp Acceptance.
 *
 * SensApp Acceptance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SensApp Acceptance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SensApp Acceptance.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.modelbased.sensapp.util.http;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

/**
 * Helper class to hide technicalities related to HTTP connection
 */
public class HttpRequest {

    private final URL targetUrl;
    private final HttpMethod method;
    private final String contentType;

    public HttpRequest(URL targetUrl, HttpMethod method) {
        this.targetUrl = targetUrl;
        this.method = method;
        this.contentType = "application/json";
    }

    public String send(String body) {
        try {
            final HttpURLConnection connection = connect();
            sendContent(connection, body);
            final String response = readResponse(connection);
            connection.disconnect();
            return response;

        } catch (IOException ex) {
            throw new RuntimeException("Error while connecting to " + targetUrl.toString() + " (" + ex.getMessage() + ")");

        }
    }

    private HttpURLConnection connect() throws ProtocolException, IOException {
        final HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod(method.name());
        return connection;
    }

    private void sendContent(final HttpURLConnection connection, String body) throws IOException {
        if (!body.isEmpty()) {
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-type", contentType);
            OutputStreamWriter request = null;
            try {
                request = new OutputStreamWriter(connection.getOutputStream(), Charset.defaultCharset());
                request.write(body);
                request.flush();
                
            } finally {
                if (request != null) {
                    request.close();
                }
            }
        }
    }

    private String readResponse(final HttpURLConnection connection) throws IOException {
        BufferedReader response = null;
        try {
            response = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));
            final StringBuilder buffer = new StringBuilder();
            String line = response.readLine();
            while (line != null) {
                buffer.append(line);
                line = response.readLine();
            }
            return buffer.toString();

        } finally {
            if (response != null) {
                response.close();
            }
        }

    }
    private static final String EMPTY_LINE = "";

}
