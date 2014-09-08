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
package net.modelbased.sensapp.acceptance.driver;

/**
 * Abstract behaviour of the 'SensAppAdmin' service
 */
public interface SensAppAdmin {

    /**
     * Open the web interface of the SensAppAdmin
     */
    public void open();

    /**
     * Register a sensor with the given identifier and description
     *
     * @param id the unique identifier of the sensor
     * @param description a short description of the sensor
     */
    public void registerSensor(String id, String description);

    /**
     * @return true if there exists a sensor with the given identifier
     * @param sensorId the sensor identifier
     */
    public boolean isRegistered(String sensorId);

    /**
     * Delete the sensor with the specified ID
     *
     * @param sensorId the ID of the sensor to delete
     */
    public void deleteSensor(String sensorId);

    /**
     * Close the connection with the SensAppAdmin web interface
     */
    public void exit();

}
