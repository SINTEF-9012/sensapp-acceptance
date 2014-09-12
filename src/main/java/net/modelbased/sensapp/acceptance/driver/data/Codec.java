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

/**
 * Represent specific encoding of the sensor data, such as JSON or XML
 */
public interface Codec {
    
    /**
     * Extract a sensorData object from a given text
     * @param text the text to parse
     * @return the equivalent SensorData object
     */
    public SensorData decodeSensorData(String text);
    
    /**
     * Extract an event object from a given text
     * @param text the text to parse
     * @return an equivalent Event object
     */
    public Event decodeEvent(String text);
    
    /**
     * Encode the given SensorData object into JSON
     * @param data the sensor data to serialise
     * @return the equivalent JSON string
     */
    public String encodeSensorData(SensorData data);
    
    /**
     * Encode the given Event object into JSON
     * @param event the event object to serialise
     * @return the equivalent JSON string
     */
    public String encodeEvent(Event event);
    
}
