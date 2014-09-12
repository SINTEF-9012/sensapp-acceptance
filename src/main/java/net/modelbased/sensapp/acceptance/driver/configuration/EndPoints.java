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

import java.net.MalformedURLException;
import java.net.URL;
import net.modelbased.sensapp.acceptance.driver.Service;
import java.util.EnumMap;
import java.util.Map;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Hold the URL associated with the endpoints of the SensApp services
 */
public class EndPoints {

    private final Map<Service, URL> endPoints;

    /**
     * Create the EndPoints set from a mapping between service name and URL.
     *
     * @param urlByService a mapping from service name to URL
     * @throws MissingEndPointException if some service are not provided with
     * any URL
     * @throws MalformedURLException if some URL are invalid
     */
    public EndPoints(Map<String, String> urlByService) throws MissingEndPointException, MalformedURLException {
        require(urlByService, is(not(nullValue())));

        this.endPoints = new EnumMap<>(Service.class);
        for (Service eachService: Service.values()) {
            final String urlText = urlByService.get(eachService.toString());
            if (urlText == null) {
                throw new MissingEndPointException(eachService);
            }
            this.endPoints.put(eachService, new URL(urlText));
        }

    }

    /**
     * @return the URL associated with the given service
     * @param service the service whose URL is needed
     *
     */
    public URL getUrlOf(Service service) {
        require(service, is(not(nullValue())));

        return endPoints.get(service);
    }

}
