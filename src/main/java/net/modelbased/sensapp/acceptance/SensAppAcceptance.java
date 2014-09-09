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
/*
 */
package net.modelbased.sensapp.acceptance;

import net.modelbased.sensapp.acceptance.driver.EndPointsBroker;
import net.modelbased.sensapp.acceptance.driver.MissingEndPointException;
import org.junit.runner.JUnitCore;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Parse the command line arguments and proceed accordingly if they are valid.
 */
public class SensAppAcceptance {

    private final PropertyFileReference endPoints;
    
    public SensAppAcceptance(String[] commandLine) {
        require(commandLine, is(not(nullValue())));
        
        if (commandLine.length < 1) {
            throw new IllegalArgumentException("Missing path to the sensapp configuration.");
        }
        
        endPoints = new PropertyFileReference(commandLine[0]);
        
        ensure(this.endPoints, is(not(nullValue())));
    }
    
    
    public void run() throws MissingEndPointException {
        EndPointsBroker.getInstance().loadEndPointsFrom(endPoints); 
        JUnitCore.main(SensAppTest.class.getCanonicalName());
    }
    
    public static String getUsageMessage() {
        return "Usage: java -jar sensapp-acceptance my-sensapp.properties";
    }

}