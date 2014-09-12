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
import java.net.MalformedURLException;
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
        try {
            final Map<String, String> endPointsAsMap = new HashMap<>();
            for (Service eachService: Service.values()) {
                endPointsAsMap.put(eachService.toString(), SENSAPP_DEFAULT_URL + eachService.name());
            }
            return new EndPoints(endPointsAsMap);

        } catch (MissingEndPointException ex) {
            throw new RuntimeException("Internal error: Default endpoints misses one service", ex);

        } catch (MalformedURLException ex) {
            throw new RuntimeException("Internal error: Malformed URL", ex);

        }
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
     * @param urlByService the dictionary to convert, containing the URL
     * associated to all SensApp services
     * @return an equivalent EndPoints object
     *
     * @throws MissingEndPointException when the given properties does not cover
     * all SensApp endpoints
     * @throws java.net.MalformedURLException if some URL associated with the
     * services are malformed
     */
    public EndPoints fromProperties(Properties urlByService) throws MissingEndPointException, MalformedURLException {
        require(urlByService, is(not(nullValue())));

        final Map<String, String> endPointsAsMap = new HashMap<>();
        for (Service eachService: Service.values()) {
            final String serviceName = eachService.toString();
            if (urlByService.containsKey(serviceName)) {
                final String url = urlByService.getProperty(serviceName);
                endPointsAsMap.put(serviceName, url);
            }
        }

        return new EndPoints(endPointsAsMap);
    }

}
