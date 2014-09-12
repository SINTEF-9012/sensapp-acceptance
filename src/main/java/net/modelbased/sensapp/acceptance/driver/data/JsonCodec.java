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
/**
 * This file is part of SensApp Acceptance.
 *
 * SensApp Acceptance is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * SensApp Acceptance is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SensApp Acceptance. If not, see <http://www.gnu.org/licenses/>.
 */
package net.modelbased.sensapp.acceptance.driver.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Encode and decode sensor data into (from) JSON text
 */
public class JsonCodec implements Codec {

    private final ObjectMapper mapper;

    public JsonCodec() {
        mapper = new ObjectMapper();
    }

    @Override
    public SensorData decodeSensorData(String text) {
        require(text, is(not(nullValue())));
        require(text.length(), is(greaterThan(0)));

        try {
            final JsonNode root = mapper.readTree(text);
            final String baseName = root.get(BASE_NAME).asText();
            final long baseTime = root.get(BASE_TIME).asLong();

            IanaUnit unit = IanaUnit.UNKNOWN;
            if (root.has(BASE_UNIT)) {
                unit = IanaUnit.fromSymbol(root.get(BASE_UNIT).asText());
            }

            final List<Event> events = new ArrayList<>();
            if (root.has(EVENTS)) {
                final JsonNode eventsNode = root.get(EVENTS);
                for (JsonNode eachEventNode: eventsNode) {
                    events.add(decodeEvent(eachEventNode));
                }
            }

            final SensorData result = new SensorData(baseName, baseTime, unit, events.toArray(new Event[events.size()]));

            return result;

        } catch (IOException ex) {
            throw new RuntimeException("Unexpected I/O error when reading the source (" + ex.getMessage() + ")");
        }
    }
    private static final String BASE_TIME = "bt";
    private static final String BASE_NAME = "bn";
    private static final String BASE_UNIT = "bu";
    private static final String EVENTS = "e";

    private Event decodeEvent(JsonNode node) {
        require(node, is(not(nullValue())));

        final double value = node.get(VALUE).asDouble();
        final long time = node.get(TIME).asLong();
        final Event result = new Event(value, time);

        ensure(result, is(not(nullValue())));
        return result;
    }
    private static final String TIME = "t";
    private static final String VALUE = "v";

    @Override
    public Event decodeEvent(String text) {
        try {
            return decodeEvent(mapper.readTree(text));

        } catch (IOException ex) {
            throw new RuntimeException("Unexpected I/O error when reading the source (" + ex.getMessage() + ")");
        }
    }

    @Override
    public String encodeSensorData(SensorData data) {
        require(data, is(not(nullValue())));

        final StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"" + BASE_NAME + "\":\"").append(data.getSensorId()).append("\",");
        buffer.append("\"" + BASE_TIME + "\":").append(data.getBaseTime()).append(",");
        buffer.append("\"" + BASE_UNIT + "\":\"").append(data.getUnit().getSymbol()).append("\",");

        buffer.append("\"" + EVENTS + "\":[");
        for (int eventIndex = 0; eventIndex < data.getEvents().size(); eventIndex++) {
            buffer.append(encodeEvent(data.getEvents().get(eventIndex)));
            if (eventIndex < (data.getEvents().size() - 1)) {
                buffer.append(",");
            }
        }
        buffer.append("]}");

        return buffer.toString();
    }

    @Override
    public String encodeEvent(Event event) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("{\"" + VALUE + "\":").append(event.getValue()).append(",");
        buffer.append("\"" + TIME + "\":").append(event.getTime()).append("}");
        return buffer.toString();
    }

}
