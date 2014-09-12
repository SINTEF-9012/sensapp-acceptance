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
package net.modelbased.sensapp.acceptance.driver.dispatcher;

import java.io.FileInputStream;
import java.io.IOException;
import net.modelbased.sensapp.acceptance.SampleFactory;
import net.modelbased.sensapp.acceptance.driver.SensApp;
import net.modelbased.sensapp.acceptance.driver.admin.SensAppAdminWithSelenium;
import net.modelbased.sensapp.acceptance.driver.configuration.*;
import net.modelbased.sensapp.acceptance.driver.data.*;
import net.modelbased.sensapp.acceptance.driver.storage.RestStorage;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Specification of the Rest Dispatcher
 *
 * Why is it ignored? This is not a 'unit' test as it is made to interact with
 * the dispatcher deployed on the SINTEF minicloud.
 *
 */
@Ignore
public class RestDispatcherTest {

    private final SampleFactory samples;
    private final Dispatcher dispatcher;
    private final SensApp sensApp;

    public RestDispatcherTest() throws IOException, MissingEndPointException {
        EndPointsFactory factory = new EndPointsFactory();
        EndPoints endPoints = factory.fromProperties(new FileInputStream(MINICLOUD_ENDPOINTS));
        dispatcher = new RestDispatcher(endPoints);
        sensApp = new SensApp(new SensAppAdminWithSelenium(endPoints, new FirefoxDriver()), dispatcher, new RestStorage(endPoints));
        samples = new SampleFactory(sensApp);
    }

    @Test
    public void dispatcherShouldRespond() {
        final String sensorId = "sensor-OvO415g922";

//        final SensorData data = new SensorData(sensorId, 0L, IanaUnit.DEGREE_CELSIUS,
//                                               new Event(22.5, +1L)
//        );
        final SensorData data = samples.dummySensorData(sensorId);

        System.out.println(data.toSenMl());

        dispatcher.push(data);

    }
    
    @After
    public void stopFirefox() {
        sensApp.getAdmin().exit();
    }
    

    private static final String MINICLOUD_ENDPOINTS = "src/test/resources/endPoints-minicloud.properties";

}
