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

package net.modelbased.sensapp.acceptance.driver.data;



import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



/**
 * Specification of the sensor data
 */
public class SensorDataTest {

    @Test
    public void eventShouldBeProperlyConvertedToSenML() {
        Event event = new Event(123D, 0L);
        assertThat("wrong SenML", event.toSenMl(), is(equalTo("{\"v\":123.0,\"t\":0}")));
    }
    
    
    @Test
    public void sensorDataShouldBeProperlyConvertedToSenML() {
        SensorData data = new SensorData("foo", 1L, (IanaUnit.METER), 
                new Event(10D, 1L)
        );
        
        final String expectedSenML = 
                "{"
                + "\"bn\":\"foo\","
                + "\"bt\":1,"
                + "\"bu\":\"m\","
                + "\"e\":["
                + "{\"v\":10.0,"
                + "\"t\":1}]"
                + "}";
        
        assertThat("wrong SenML", data.toSenMl(), is(equalTo(expectedSenML)));
    }

}