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
package net.modelbased.sensapp.acceptance.driver;

import java.util.Locale;

import static net.modelbased.sensapp.util.dbc.Contracts.require;
import static org.hamcrest.Matchers.*;

/**
 * The set of services available in SensApp
 *
 * Each service is associated with an index to their use as array indices.
 */
public enum Service {

    ADMIN(0),
    REGISTRY(1),
    DISPATCHER(2),
    STORAGE(3),
    NOTIFIER(4);

    private final int index;

    private Service(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static int count() {
        return values().length;
    }

    public static String[] names() {
        final String[] result = new String[5];

        for (Service each: Service.values()) {
            result[each.getIndex()] = each.name();
        }

        return result;
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.getDefault());
    }

    public static Service byIndex(int index) {
        require(index, is(both(greaterThan(-1)).and(lessThan(values().length))));

        return values()[index];
    }
}
