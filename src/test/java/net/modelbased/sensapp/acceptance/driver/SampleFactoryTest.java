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

import net.modelbased.sensapp.acceptance.driver.admin.SensAppAdmin;
import net.modelbased.sensapp.acceptance.SampleFactory;
import net.modelbased.sensapp.acceptance.driver.dispatcher.Dispatcher;
import net.modelbased.sensapp.acceptance.driver.storage.Storage;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Specification of SampleFactory
 */
public class SampleFactoryTest {

    
    private final JUnit4Mockery context;
    private final SensAppAdmin admin;
    private final Dispatcher dispatcher;
    private final Storage storage;
    
    public SampleFactoryTest() {
        context = new JUnit4Mockery();
        admin = context.mock(SensAppAdmin.class);
        dispatcher = context.mock(Dispatcher.class);
        storage = context.mock(Storage.class);
    }
   
    @Test
    public void shouldGenerateProperSensorIds() {
        final SampleFactory make = defaultSampleFactory();
        
        final String id = make.randomSensorId(); 
        
        assertThat(id, is(not(nullValue())));
        assertThat(id, containsString("sensor-"));
        assertThat(make.usedSensorIds(), contains(id));
    }

    private SampleFactory defaultSampleFactory() {
        return new SampleFactory(new SensApp(admin, dispatcher, storage)); 
    }
   
    
    @Test
    public void cleanShouldDeleteAllGeneratedSensors() {
        final SampleFactory factory = defaultSampleFactory();
        
        final String id = factory.randomSensorId(); 
        
        context.checking(new Expectations(){{ 
            oneOf(admin).open();
            oneOf(admin).isRegistered(id); will(returnValue(true));
            oneOf(admin).deleteSensor(id);
        }});
        
        factory.cleanUp();
        
        context.assertIsSatisfied();
    }

}