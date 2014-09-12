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

import net.modelbased.sensapp.acceptance.driver.configuration.EndPointsFactory;
import net.modelbased.sensapp.acceptance.driver.configuration.EndPoints;
import net.modelbased.sensapp.acceptance.driver.configuration.MissingEndPointException;
import net.modelbased.sensapp.acceptance.driver.Service;
import java.io.*;
import org.junit.Test;

import static net.modelbased.sensapp.util.dbc.Contracts.require;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the endpoints factory
 */
public class EndPointsFactoryTest {

    private static final String END_POINTS[] = {
        "http://demo.sensapp.org/admin",
        "http://demo.sensapp.org/registry",
        "http://demo.sensapp.org/dispatcher",
        "http://demo.sensapp.org/storage",
        "http://demo.sensapp.org/notfifer"
    };

    @Test
    public void shouldReadEndPointsInThePropertyFormat() throws IOException, MissingEndPointException {
        final EndPointsFactory makeEndPoints = new EndPointsFactory();
        
        final InputStream input = formatAsPropertyFile(END_POINTS);
        final EndPoints endPoints = makeEndPoints.fromProperties(input);

        verifyEndPoints(endPoints);
    }
    
    @Test(expected = MissingEndPointException.class)
    public void shouldDetectMissingEndPoints() throws IOException, MissingEndPointException {       
        final EndPointsFactory makeEndPoints = new EndPointsFactory();
 
        final InputStream inputStream = formatAsPropertyFile(onlyAdminEndPoint());
        makeEndPoints.fromProperties(inputStream);
    }

    private static String[] onlyAdminEndPoint() {
        return new String[]{END_POINTS[0]};
    }

    private InputStream formatAsPropertyFile(String[] endPoints) {
        require(endPoints, is(not(nullValue())));

        final StringBuilder buffer = new StringBuilder();

        for (int serviceIndex=0 ; serviceIndex<endPoints.length ; serviceIndex++) {
            buffer.append(Service.byIndex(serviceIndex).toString());
            buffer.append(": ");
            buffer.append(endPoints[serviceIndex]);
            buffer.append(System.lineSeparator());
        }
      
        return new ByteArrayInputStream(buffer.toString().getBytes());
    }

    private void verifyEndPoints(EndPoints endPoints) {
        assertThat(endPoints, is(not(nullValue())));
        
        for (Service eachService: Service.values()) {
            assertThat(endPoints.getUrlOf(eachService).toString(),
                       is(equalTo(END_POINTS[eachService.getIndex()])));
        }
    }

}
