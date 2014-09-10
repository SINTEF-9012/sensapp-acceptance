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

import net.modelbased.sensapp.acceptance.driver.Service;

/**
 * Thrown when a EndPoint collection is missing for a given service
 */
public class MissingEndPointException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Service missingService;

    public MissingEndPointException(Service missingService) {
        super("No endpoint found for service '" + missingService.name() + "'");
        this.missingService = missingService;
    }

    public Service getMissingService() {
        return missingService;
    }
    
}
