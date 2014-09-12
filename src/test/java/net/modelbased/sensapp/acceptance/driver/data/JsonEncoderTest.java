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
/*
 */
package net.modelbased.sensapp.acceptance.driver.data;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the JSON encoder
 */
public class JsonEncoderTest {

    private final String sensorId = "my-sensor";
    private final long baseTime = 0L;
    private final IanaUnit unit = IanaUnit.DEGREE_CELSIUS;
    private final double eventValue = 12D;
    private final long eventTime = 3L;
    private final String jsonText = "{"
            + "\"bn\":\"" + sensorId + "\","
            + "\"bt\":" + baseTime + ","
            + "\"bu\":\"" + unit.getSymbol() + "\","
            + "\"e\":["
            + "{"
            + "\"v\":" + eventValue + ","
            + "\"t\":" + eventTime
            + "}]"
            + "}";

    ;
    
    private final JsonCodec jsonCodec;

    public JsonEncoderTest() {
        jsonCodec = new JsonCodec();
    }

    @Test
    public void sensorDataShouldBeProperlyDecoded() {
        SensorData data = jsonCodec.decodeSensorData(jsonText);

        assertThat(data.getSensorId(), is(equalTo(sensorId)));
        assertThat(data.getBaseTime(), is(equalTo(baseTime)));
        assertThat(data.getUnit(), is(equalTo(unit)));
        assertThat(data.getEvents(), hasSize(1));
        final Event event = data.getEvents().get(0);
        assertThat(event, is(not(nullValue())));
        assertThat(event.getTime(), is(equalTo(eventTime)));
        assertThat(event.getValue(), is(equalTo(eventValue)));
    }

    @Test
    public void sensorDataShouldBeCorrectlyEncoded() {
        final SensorData data = new SensorData(sensorId, baseTime, unit, new Event(eventValue, eventTime));

        final String encoded = jsonCodec.encodeSensorData(data);

        assertThat(encoded, is(equalTo(jsonText)));

    }

}
