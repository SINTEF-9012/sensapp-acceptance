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

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Create various sample data for testing SensApp
 */
public class SampleFactory {

    private final SensApp sensapp;
    private final Set<String> usedSensorIds;

    public SampleFactory(SensApp sensapp) {
        this.sensapp = sensapp;
        this.usedSensorIds = new HashSet<>();
    }

    /**
     * Delete all the instance that were created by the this factory
     */
    public void cleanUp() {
        for (String sensorId: usedSensorIds) {
            if (sensapp.getAdmin().isRegistered(sensorId)) {
                sensapp.getAdmin().deleteSensor(sensorId);
            }
        }
    }

    /**
     * @return the list of sensor name that have been created
     */
    public Set<String> usedSensorIds() {
        return Collections.unmodifiableSet(usedSensorIds);
    }

    /**
     * Generate a random sensor ID of the form: 'sensor-xxx' where xxx is a
     * randomly generated key.
     *
     * Each generated sensor ID is memorised so as to be able to restore the
     * initial state of the services.
     *
     * @return the generated sensor ID as a key
     */
    public String randomSensorId() {
        final String sensorId = SENSOR_PREFIX + randomWord(KEY_LENGTH);
        usedSensorIds.add(sensorId);

        return sensorId;
    }

    private static final String SENSOR_PREFIX = "sensor-";
    private static final int KEY_LENGTH = 10;

    /**
     * Generate a random key of the given length. Each character in the key is
     * in [a-zA-Z0-9]
     *
     * @param length the number of character in the key.
     * @return a randomly generated word of the specified length
     */
    private String randomWord(final int length) {
        final char[] characters = new char[length];
        for (int index = 0; index < length; index++) {
            characters[index] = AsciiCodeSlice.getRandomSlice().getRandomSymbol();
        }
        return new String(characters);
    }

    /**
     * Represent a slice of the ASCII code, identified by a first symbol and the
     * number of symbol to includes from there.
     */
    private static enum AsciiCodeSlice {

        DIGITS(48, 10),
        UPPER_CASE_LETTERS(65, 26),
        LOWER_CASE_LETTERS(97, 26);

        public static AsciiCodeSlice getRandomSlice() {
            final int sliceCount = values().length;
            final Random generate = new Random();
            return AsciiCodeSlice.values()[generate.nextInt(sliceCount)];
        }

        private final Random generate;
        private final int firstSymbol;
        private final int symbolCount;

        private AsciiCodeSlice(int firstSymbol, int symbolCount) {
            this.generate = new Random();
            this.firstSymbol = firstSymbol;
            this.symbolCount = symbolCount;
        }

        public char getRandomSymbol() {
            return (char) (firstSymbol + generate.nextInt(symbolCount));
        }

    }

}
