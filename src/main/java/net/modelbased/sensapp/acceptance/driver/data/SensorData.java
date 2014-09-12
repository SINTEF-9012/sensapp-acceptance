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

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.modelbased.sensapp.util.dbc.Contracts.require;
import static org.hamcrest.Matchers.*;

/**
 * The events associated with a given sensor
 */
public class SensorData {

    private final String sensorId;
    private final long baseTime;
    private final IanaUnit unit;
    private final List<Event> events;

    public SensorData(String sensorId, long baseTime, IanaUnit unit, Event... events) {
        require(sensorId, is(not(nullValue())));
        require(baseTime, is(not(nullValue())));
        require(baseTime, is(greaterThanOrEqualTo(0L)));
        require(unit, is(not(nullValue())));
        require(events, is(not(nullValue())));

        this.sensorId = sensorId;
        this.baseTime = baseTime;
        this.unit = unit;
        this.events = Arrays.asList(events);
    }

    public String getSensorId() {
        return sensorId;
    }

    public long getBaseTime() {
        return baseTime;
    }

    public IanaUnit getUnit() {
        return unit;
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public String toSenMl() {
        final StringBuilder buffer = new StringBuilder();
        buffer
                .append("{")
                .append("\"bn\":\"").append(sensorId).append("\",")
                .append("\"bt\":").append(baseTime).append(",")
                .append("\"bu\":\"").append(unit.getSymbol()).append("\",")
                .append("\"e\":[");
        for (int eventIndex = 0; eventIndex < events.size(); eventIndex++) {
            buffer.append(events.get(eventIndex).toSenMl());
            if (eventIndex < (events.size() - 1)) {
                buffer.append(",");
            }
        }
        buffer.append("]")
                .append("}");

        return buffer.toString();
    }
    
    public static SensorData fromSenMl(ObjectNode root) {
       throw new UnsupportedOperationException("To be done!");
                
   }

}
