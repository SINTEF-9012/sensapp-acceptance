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

import java.util.EnumMap;
import java.util.Map;
import org.junit.Test;

import static net.modelbased.sensapp.acceptance.driver.Service.*;

import org.junit.Test;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Specification of the EndPoints of SensApp
 */
public class EndPointsTest {

    @Test
    public void endPointsShouldBeAccessible() {
        final String adminUrl = "http://org.sensapp.demo/admin";
        
        Map<Service, String> endPoints = sampleEndPoints(); 
        endPoints.put(ADMIN, adminUrl);
        final EndPoints endpoints = new EndPoints(endPoints);
        
        assertThat(endpoints.of(ADMIN), is(equalTo(adminUrl)));
    }

    private Map<Service, String> sampleEndPoints() {
        final Map<Service, String> endPoints = new EnumMap<>(Service.class);
        endPoints.put(Service.ADMIN, "http://foo.com/admin");
        endPoints.put(Service.DISPATCHER, "http://foo.com/admin");
        endPoints.put(Service.NOTIFIER, "http://foo.com/notifier");
        endPoints.put(Service.REGISTRY, "http://foo.com/registry");
        endPoints.put(Service.STORAGE, "http://foo.com/storage");
        return endPoints;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMissingEndPoints() {
        final Map<Service, String> endPoints = new EnumMap<>(Service.class); 
        endPoints.put(Service.ADMIN, "http://foo.com/admin");
        
        new EndPoints(endPoints);
    }
    

}