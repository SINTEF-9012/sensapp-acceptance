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
import net.modelbased.sensapp.acceptance.driver.dispatcher.Dispatcher;
import net.modelbased.sensapp.acceptance.driver.storage.Storage;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Wrap all the service of SensApp in a single object
 */
public class SensApp {

    private final SensAppAdmin admin;
    private final Dispatcher dispatcher;
    private final Storage storage;

    public SensApp(SensAppAdmin admin, Dispatcher dispatcher, Storage storage) {
        require(admin, is(not(nullValue())));
        require(dispatcher, is(not(nullValue())));
        require(storage, is(not(nullValue())));

        this.admin = admin;
        this.dispatcher = dispatcher;
        this.storage = storage;

    }

    public SensAppAdmin getAdmin() {
        return admin;
    }
    
    public Storage getStorage() {
        return storage;
    }
    
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

}
