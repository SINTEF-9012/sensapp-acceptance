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

package net.modelbased.sensapp.acceptance.driver.configuration;

import java.io.IOException;
import java.io.InputStream;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Provide a single access to the configuration (i.e., end points) of the
 * SensApp system under test.
 */
public class EndPointsBroker {

    private static final Object lock = new Object();
    private static EndPointsBroker instance = null;

    /**
     * @return the single access point to the EndPointsBroker
     */
    public static EndPointsBroker getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new EndPointsBroker();
            }
        }
        return instance;
    }

    private final EndPointsFactory factory;
    private EndPoints endPoints;

    private EndPointsBroker() {
        factory = new EndPointsFactory();
        endPoints = factory.defaultEndPoints();
    }

    public EndPoints getEndPoints() {
        return this.endPoints;
    }

    /**
     * Load the given property file as the current end points
     *
     * @param source a stream from the properties to be read
     * @throws MissingEndPointException if the property file is not complete
     * @throws java.io.IOException if I/O occurs while reading the source
     */
    public void loadEndPointsFrom(InputStream source) throws MissingEndPointException, IOException {
        require(source, is(not(nullValue())));

        try {
            endPoints = factory.fromProperties(source);

        } catch (IOException | MissingEndPointException ex) {
            endPoints = factory.defaultEndPoints();
            throw ex;
        }

        assert endPoints != null: "Should have set the endpoints";
    }

}
