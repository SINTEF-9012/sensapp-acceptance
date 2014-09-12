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
/*
 */
package net.modelbased.sensapp.acceptance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.modelbased.sensapp.acceptance.driver.configuration.EndPointsBroker;
import net.modelbased.sensapp.acceptance.driver.configuration.MissingEndPointException;
import org.junit.runner.JUnitCore;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * Parse the command line arguments and proceed accordingly if they are valid.
 */
public class SensAppAcceptance {

    private final PropertyFileReference endPoints;

    public SensAppAcceptance(String[] commandLine) {
        require(commandLine, is(not(nullValue())));

        if (commandLine.length < 1) {
            throw new IllegalArgumentException("Missing path to the sensapp configuration.");
        }

        endPoints = new PropertyFileReference(commandLine[0]);
    }

    public void run() throws MissingEndPointException, FileNotFoundException {
        InputStream configFile = null;
        try {
            configFile = new FileInputStream(endPoints.getPath());
            EndPointsBroker.getInstance().loadEndPointsFrom(configFile);

        } catch (IOException ex) {
            throw new RuntimeException("I/O error while reading the endpoints", ex);

        } finally {
            if (configFile != null) {
                try {
                    configFile.close();

                } catch (IOException ex) {
                    throw new RuntimeException("I/O error while closing the stream from the endpoints", ex);
                }
            }
        }

        JUnitCore.main(SensAppTest.class.getCanonicalName());
    }

    public static String getUsageMessage() {
        return "Usage: java -jar sensapp-acceptance.jar my-sensapp.properties";
    }

}
