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

import java.io.FileNotFoundException;
import net.modelbased.sensapp.acceptance.driver.configuration.MissingEndPointException;

/**
 * Entry point of the acceptance test. Run JUnit4 on the predefined test suite
 */
public class Main {

    /**
     * @param commandLine the command line arguments provided by the user
     */
    public static void main(String[] commandLine) {
        try {
            new SensAppAcceptance(commandLine).run();

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.out.println(SensAppAcceptance.getUsageMessage());

        } catch (MissingEndPointException ex) {
            System.out.println("Error: Invalid endpoints configuration of SensApp");
            System.out.println(ex.getMessage());
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error: unable to find the endpoints file");
            System.out.println(ex.getMessage());
        }
    }

}
