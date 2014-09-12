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

import org.junit.Test;

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

}
