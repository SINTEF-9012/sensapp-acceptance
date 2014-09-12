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
package net.modelbased.sensapp.acceptance.driver.dispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import net.modelbased.sensapp.acceptance.driver.Service;
import net.modelbased.sensapp.acceptance.driver.configuration.EndPoints;
import net.modelbased.sensapp.acceptance.driver.data.JsonCodec;
import net.modelbased.sensapp.acceptance.driver.data.SensorData;
import net.modelbased.sensapp.util.http.HttpMethod;
import net.modelbased.sensapp.util.http.HttpRequest;

import static net.modelbased.sensapp.util.dbc.Contracts.require;
import static org.hamcrest.Matchers.*;

/**
 * A proxy to the 'dispatcher' service that directly interact through HTTP
 */
public class RestDispatcher implements Dispatcher {

    private final URL url;

    public RestDispatcher(EndPoints endPoints) throws MalformedURLException {
        require(endPoints, is(not(nullValue())));

        this.url = new URL(endPoints.of(Service.DISPATCHER));
    }

    @Override
    public void push(SensorData data) {
        final JsonCodec codec = new JsonCodec();
        final HttpRequest request = new HttpRequest(url, HttpMethod.PUT);
        final String response = request.send(codec.encodeSensorData(data));
        System.out.println(response);
    }

}
