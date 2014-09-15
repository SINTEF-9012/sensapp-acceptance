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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Test;

import static net.modelbased.sensapp.acceptance.driver.Service.ADMIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EndPointsBrokerTest {

    private final EndPointsBroker broker;

    public EndPointsBrokerTest() {
        broker = EndPointsBroker.getInstance();
    }

    @Test
    public void shouldBeAvailable() {
        assertThat(broker, is(not(nullValue())));
    }

    @Test
    public void shouldAlwaysProvideTheSameInstance() {
        final EndPointsBroker alternative = EndPointsBroker.getInstance();
        assertThat(broker, is(sameInstance(alternative)));
    }

    @Test
    public void shouldProvideDefaultEndPoints() {
        final EndPoints endPoints = broker.getEndPoints();

        assertThat(endPoints, is(not(nullValue())));
    }

    @Test
    public void dataLoadedShouldBeAvailable() throws MissingEndPointException, IOException {
        ByteArrayInputStream source = new ByteArrayInputStream(SAMPLE_ENDPOINTS.getBytes());
        broker.loadEndPointsFrom(source);
        final EndPoints endPoints = broker.getEndPoints();

        assertThat(endPoints.getUrlOf(ADMIN).toString(), is(equalTo(ADMIN_URL)));
    }

    private static final String ADMIN_URL = "http://www.mysensapp/admin";
    private static final String SAMPLE_ENDPOINTS
            = "admin: " + ADMIN_URL + "\n"
            + "notifier: http://192.168.11.18:8080/notifier\n"
            + "registry: http://192.168.11.18:8080/registry\n"
            + "storage: http://192.168.11.18:8080/storage\n"
            + "dispatcher: http://192.168.11.18:8080/dispatcher";

}
