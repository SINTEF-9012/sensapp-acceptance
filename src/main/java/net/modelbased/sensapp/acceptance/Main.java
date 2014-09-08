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

import org.junit.runner.JUnitCore;

/**
 * Entry point of the acceptance test. Run JUnit4 on the predefined test suite
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JUnitCore.main(SensAppTest.class.getCanonicalName());
    }

}
