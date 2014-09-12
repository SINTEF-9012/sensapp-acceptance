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
package net.modelbased.sensapp.acceptance.driver.data;

import static net.modelbased.sensapp.util.dbc.Contracts.require;
import static org.hamcrest.Matchers.*;

/**
 * Enumeration of the standard measurement units such as meter, kilogramme, etc.
 */
public enum IanaUnit {

    METER("meter", "m"),
    DEGREE_CELSIUS("degree celcius", "degC"),
    UNKNOWN("unknown", "??");

    public static IanaUnit fromSymbol(String symbol) {
        require(symbol, is(not(nullValue())));
        require(symbol.length(), is(greaterThan(0)));
        
        for(IanaUnit eachUnit: values()) {
            if (eachUnit.getSymbol().equals(symbol)) {
                return eachUnit;
            }
        }
        
        throw new IllegalArgumentException("Unknown IANA unit symbol '" + symbol + "'");
    }

    private final String name;
    private final String symbol;

    private IanaUnit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

}
