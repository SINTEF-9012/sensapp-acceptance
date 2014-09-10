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
import java.util.EnumMap;
import java.util.Map;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;


/**
 * Hold the URL associated with the endpoints of the SensApp services
 */
public class EndPoints {
    
    private final Map<Service, String> endPoints;
    
    public EndPoints(Map<Service, String> endPoints) {
        require(endPoints, is(not(nullValue())));
        
        this.endPoints = new EnumMap<>(Service.class);
        for(Service eachService: Service.values()) {
            require(endPoints, hasKey(eachService)); 
            this.endPoints.put(eachService, endPoints.get(eachService));
        }
    }
    
    public String of(Service service) {
        require(service, is(not(nullValue())));
        
        final String result = endPoints.get(service);
        
        ensure(result, is(not(nullValue())));
        return result;
    }

}
