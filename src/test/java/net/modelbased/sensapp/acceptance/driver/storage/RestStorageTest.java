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

import java.io.FileInputStream;
import java.io.IOException;
import net.modelbased.sensapp.acceptance.driver.configuration.*;
import net.modelbased.sensapp.acceptance.driver.data.SensorData;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Single run of the RestStorage component.
 *
 * This test disabled as it is not independent of other tests (it require some
 * sensor data already exist in the database).
 */
//@Ignore
public class RestStorageTest {

    @Test
    public void storageShouldRespond() throws IOException, MissingEndPointException {
        final Storage storage = prepareRestStorage();

        final String sensorId = "sensor-OvO415g922";
        SensorData data = storage.get(sensorId);

        assertThat(data.getSensorId(), is(equalTo(sensorId)));
        assertThat(data.getEvents().size(), is(equalTo(1)));
    }

    private Storage prepareRestStorage() throws IOException, MissingEndPointException {
        EndPointsFactory factory = new EndPointsFactory();
        EndPoints endPoints = factory.fromProperties(new FileInputStream(MINICLOUD_ENDPOINTS));
        return new RestStorage(endPoints);
    }
    
    private static final String MINICLOUD_ENDPOINTS = "src/test/resources/endPoints-minicloud.properties";
}
