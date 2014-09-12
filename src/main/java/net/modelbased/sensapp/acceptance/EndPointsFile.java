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

import java.io.File;

import static net.modelbased.sensapp.util.dbc.Contracts.*;
import static org.hamcrest.Matchers.*;

/**
 * A reference to a property file on disk.
 */
public class EndPointsFile extends File {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Create from the path to a property file.
     *
     * @param path the local path to the property file of interest
     */
    public EndPointsFile(String path) {
        super(path);
        require(path, is(not(nullValue())));
        
        if (!path.endsWith(PROPERTIES_FILE_EXTENSION)) {
            throw new IllegalArgumentException("Invalid path to a property file, expected '*.properties' but '" + path + "' was found.");
        }
    }
    
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";
  

}
