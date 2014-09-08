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
package net.modelbased.sensapp.acceptance;

import java.io.FileInputStream;
import java.io.IOException;
import net.modelbased.sensapp.acceptance.driver.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.fail;

/**
 * Specification of the SensApp application
 */
public class SensAppTest {
    
    private final SensAppAdmin admin;
    
    public SensAppTest() throws IOException, MissingEndPointException {
        final EndPointsFactory factory = new EndPointsFactory();
        final EndPoints endPoints = factory.defaultEndPoints();
        admin = new SensAppAdminWithSelenium(endPoints, new FirefoxDriver());
    }
    
    @After
    public void tearDown() {
        admin.exit();
    }
    
    @Test
    public void shouldFail() {
        fail("That was supposed to fail!");
    }
    
    @Test
    public void sensorsShouldBeProperlyRegistered() {
        final String sensorId = "my-sensor";
        
        admin.registerSensor(sensorId, "a sample sensor");
        
        assertThat(sensorId + " is registered",
                   admin.isRegistered(sensorId));
    }
    
}
