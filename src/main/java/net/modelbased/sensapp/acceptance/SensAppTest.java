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

import net.modelbased.sensapp.acceptance.driver.data.SensorData;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.modelbased.sensapp.acceptance.driver.*;
import net.modelbased.sensapp.acceptance.driver.admin.SensAppAdminWithSelenium;
import net.modelbased.sensapp.acceptance.driver.configuration.*;
import net.modelbased.sensapp.acceptance.driver.dispatcher.RestDispatcher;
import net.modelbased.sensapp.acceptance.driver.storage.RestStorage;

import static org.hamcrest.Matchers.*;

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
        sensapp = new SensApp(
                new SensAppAdminWithSelenium(endPoints, new FirefoxDriver()),
                new RestDispatcher(endPoints),
                new RestStorage(endPoints)
        );
        this.samples = new SampleFactory(sensapp);
    }

    @Test
    public void usersShouldBeAbleToRegisterNewSensors() {
        final String sensorId = samples.randomSensorId();

        sensapp.getAdmin().open();
        sensapp.getAdmin().registerSensor(sensorId, "a sample sensor, for testing purpose");

        assertThat(sensorId + " is not registered!",
                   sensapp.getAdmin().isRegistered(sensorId));
    }

    @Test
    public void sensorsShouldBeAbleToPushData() throws InterruptedException {
        final String sensorId = samples.randomSensorId();
        final SensorData sensorData = samples.dummySensorData(sensorId);

        sensapp.getAdmin().open();
        sensapp.getAdmin().registerSensor(sensorId, "yet another sensor for testing purpose");

        Thread.sleep(ONE_SECOND);

        sensapp.getDispatcher().push(sensorData);

        final SensorData read = sensapp.getStorage().get(sensorId);

        assertThat("sensor ID should be correct!",
                   read.getSensorId(), is(equalTo(sensorId)));
        assertThat("event count should be preserved!",
                   read.getEvents(), hasSize(sensorData.getEvents().size()));
    }

    private static final long ONE_SECOND = 1000L;

    @After
    public void tearDown() {
        samples.cleanUp();
        sensapp.getAdmin().exit();
    }

}
