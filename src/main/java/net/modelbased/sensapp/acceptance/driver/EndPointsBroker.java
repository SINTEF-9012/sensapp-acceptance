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
package net.modelbased.sensapp.acceptance.driver;

import java.io.FileInputStream;
import java.io.IOException;
import net.modelbased.sensapp.acceptance.PropertyFileReference;

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
     * @param file the path to the end points to be load (should point to a
     * property file)
     * @throws MissingEndPointException if the property file is not complete
     */
    public void loadEndPointsFrom(PropertyFileReference file) throws MissingEndPointException {
        require(file, is(not(nullValue())));

        FileInputStream input = null;
        try {
            input = new FileInputStream(file.getPath());
            endPoints = factory.fromProperties(input);

        } catch (IOException ex) {
            throw new RuntimeException("Unexpected I/O error while reading from '" + file.getPath() + "'", ex);

        } finally {
            if (input != null) {
                try {
                    input.close();

                } catch (IOException ex) {
                    throw new RuntimeException("Unable to close the stream from '" + file.getPath() + "'", ex);

                }
            }
        }

        ensure(endPoints, is(not(nullValue())));
    }

}
