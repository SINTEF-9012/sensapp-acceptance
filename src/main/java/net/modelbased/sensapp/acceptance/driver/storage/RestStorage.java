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
package net.modelbased.sensapp.acceptance.driver.storage;

import java.net.MalformedURLException;
import java.net.URL;
import net.modelbased.sensapp.acceptance.driver.Service;
import net.modelbased.sensapp.acceptance.driver.configuration.EndPoints;
import net.modelbased.sensapp.acceptance.driver.data.JsonCodec;
import net.modelbased.sensapp.acceptance.driver.data.SensorData;
import net.modelbased.sensapp.util.http.HttpMethod;
import net.modelbased.sensapp.util.http.HttpRequest;

import static org.hamcrest.Matchers.*;
import static net.modelbased.sensapp.util.dbc.Contracts.require;

/**
 * Access the storage service directly through its REST API
 */
public class RestStorage implements Storage {

    private final URL url;
    private final JsonCodec codec;

    public RestStorage(EndPoints endPoints) {
        require(endPoints, is(not(nullValue())));

        codec = new JsonCodec();
        url = endPoints.getUrlOf(Service.STORAGE);
    }

    @Override
    public SensorData get(String sensorId) {
        final HttpRequest request = new HttpRequest(makeUrlFor(sensorId), HttpMethod.GET);
        final String response = request.send(EMPTY_BODY);
        return codec.decodeSensorData(response);
    }
    

    private URL makeUrlFor(String sensorId) {
        try {
            return new URL(url.toString() + "/" + sensorId);
            
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Wrong URL derived for the storage of sensor " + sensorId, ex);
        
        }
    }

    private static final String EMPTY_BODY = "";

}
