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

import net.modelbased.sensapp.acceptance.driver.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Build endPoints from various sources
 */
public class EndPointsFactory {

    /**
     * @return default endpoints for SensApp, mainly for testing purpose. Each
     * service is available at
     */
    public EndPoints defaultEndPoints() {
        final Map<Service, String> endPointsAsMap = new EnumMap<>(Service.class);
        for (Service eachService: Service.values()) {
            endPointsAsMap.put(eachService, SENSAPP_DEFAULT_URL + eachService.name());
        }
        return new EndPoints(endPointsAsMap);
    }

    private static final String SENSAPP_DEFAULT_URL = "http://demo.sensapp.org/";

    /**
     * Extract end points from the given input stream assuming the content is
     * formatted as a property file
     *
     * @param source the input stream to parse
     * @return an equivalent EndPoints object
     *
     * @throws IOException if an unexpected I/O error occur
     * @throws MissingEndPointException when the given stream does not provide
     * end points for some SensApp services.
     */
    public EndPoints fromProperties(InputStream source) throws IOException, MissingEndPointException {
        final Properties properties = new Properties();
        properties.load(source);
        return fromProperties(properties);
    }

    /**
     * Convert a property dictionary into a EndPoints object
     *
     * @param properties the dictionary to convert
     * @return an equivalent EndPoints object
     *
     * @throws MissingEndPointException when the given properties does not cover
     * all SensApp endpoints
     */
    public EndPoints fromProperties(Properties properties) throws MissingEndPointException {
        require(properties, is(not(nullValue())));

        final Map<Service, String> endPointsAsMap = new EnumMap<>(Service.class);
        for (Service eachService: Service.values()) {
            final String url = properties.getProperty(eachService.toString());
            if (url == null) {
                throw new MissingEndPointException(eachService);
            }
            endPointsAsMap.put(eachService, url);
        }

        ensure(endPointsAsMap.size(), is(equalTo(Service.values().length)));
        return new EndPoints(endPointsAsMap);
    }

}
