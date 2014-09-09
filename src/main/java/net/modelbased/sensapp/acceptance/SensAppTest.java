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

package net.modelbased.sensapp.acceptance;

import java.io.IOException;
import net.modelbased.sensapp.acceptance.driver.*;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.*;

/**
 * Specification of the SensApp application
 */
public class SensAppTest {

    private final SampleFactory samples;
    private final SensApp sensapp;

    public SensAppTest() throws IOException, MissingEndPointException {
        final EndPoints endPoints = EndPointsBroker.getInstance().getEndPoints();
        sensapp = new SensApp(new SensAppAdminWithSelenium(endPoints, new FirefoxDriver()));
        this.samples = new SampleFactory(sensapp);
    }


    @Test
    public void sensorsShouldBeProperlyRegistered() {
        final String sensorId = samples.randomSensorId();

        sensapp.getAdmin().open();
        sensapp.getAdmin().registerSensor(sensorId, "a sample sensor, for testing purpose");

        assertThat(sensorId + " is not registered!",
                   sensapp.getAdmin().isRegistered(sensorId));
    }

    @After
    public void tearDown() {
        samples.cleanUp();
        sensapp.getAdmin().exit();
    }

}
