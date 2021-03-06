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
import net.modelbased.sensapp.acceptance.driver.Service;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static net.modelbased.sensapp.acceptance.driver.Service.*;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Specification of the EndPoints of SensApp
 */
public class EndPointsTest {

    @Test
    public void endPointsShouldBeAccessible() throws MissingEndPointException, MalformedURLException {
        final String adminUrl = "http://org.sensapp.demo/admin";
        
        Map<String, String> endPoints = sampleEndPoints(); 
        endPoints.put(ADMIN.toString(), adminUrl);
        final EndPoints endpoints = new EndPoints(endPoints);
        
        assertThat(endpoints.getUrlOf(ADMIN).toString(), is(equalTo(adminUrl)));
    }

    private Map<String, String> sampleEndPoints() {
        final Map<String, String> endPoints = new HashMap<>();
        endPoints.put(ADMIN.toString(), "http://foo.com/admin");
        endPoints.put(DISPATCHER.toString(), "http://foo.com/admin");
        endPoints.put(NOTIFIER.toString(), "http://foo.com/notifier");
        endPoints.put(REGISTRY.toString(), "http://foo.com/registry");
        endPoints.put(STORAGE.toString(), "http://foo.com/storage");
        return endPoints;
    }
    
    @Test(expected = MissingEndPointException.class)
    public void shouldRejectMissingEndPoints() throws MissingEndPointException, MalformedURLException {
        final Map<String, String> endPoints = new HashMap<>();
        endPoints.put(ADMIN.toString(), "http://foo.com/admin");
        
        EndPoints result = new EndPoints(endPoints);
    }
    

}