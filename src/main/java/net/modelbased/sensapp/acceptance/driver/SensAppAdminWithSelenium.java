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

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static net.modelbased.sensapp.acceptance.driver.Service.ADMIN;

/**
 * Provide access to the SensAppAdmin service using the selenium browser driver.
 */
public class SensAppAdminWithSelenium implements SensAppAdmin {

    private final EndPoints endPoints;
    private final WebDriver driver;

    public SensAppAdminWithSelenium(EndPoints endPoints, WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        this.endPoints = endPoints;
    }

    @Override
    public void open() {
        this.driver.get(endPoints.of(ADMIN));
    }

    @Override
    public void exit() {
        driver.quit();
    }

    @Override
    public void registerSensor(String identifier, String description) {
        driver.findElement(By.linkText("New Sensor")).click();

        driver.findElement(By.id("id")).clear();
        driver.findElement(By.id("id")).sendKeys(identifier);

        driver.findElement(By.id("descr")).clear();
        driver.findElement(By.id("descr")).sendKeys(description);

        driver.findElement(By.xpath("//a[contains(@onclick, 'registerSensor')]")).click();
    }

    @Override
    public boolean isRegistered(String sensorId) {
        return driver.findElements(By.id(sensorId)).size() == 1;
    }
    
    @Override
    public void deleteSensor(String sensorId) {
        final String deleteButton = DELETE_BUTTON_ID.replaceAll(SENSOR_ID_MARKER, sensorId);
        driver.findElement(By.xpath(deleteButton)).click();
        driver.findElement(By.id("delete")).click();
    }
    
    
    private static final String SENSOR_ID_MARKER = "###-sensor-id-###";
    private static final String DELETE_BUTTON_ID = "//a[contains(@onclick,'" + SENSOR_ID_MARKER + "') and contains(@onclick, 'delete-Sensor')]";
}

