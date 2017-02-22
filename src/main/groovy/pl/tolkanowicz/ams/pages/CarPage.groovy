package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.navigator.Navigator
import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.TestLink

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by jacek on 09.01.17.
 */
class CarPage {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    Car car

    private boolean hasTestData = false

    public CarPage(TestLink link) {
        this.car = new Car(url: link.url, id: link.id)
    }

    public boolean hasTestData() {
        Browser.drive {
            go car.url + "/technische-daten/"
            if (articleHasTestResult()) {
                Navigator nordschleifeSection = $("td", text: "Nordschleife")
                Navigator carName = $("th", 1)
                //carName.children().empty -> not a multitest
                if (!nordschleifeSection.empty && !carName.empty && carName.children().empty) {
                    hasTestData = true
                }
            }
        }
        return hasTestData
    }

    public void readData() {
        if (hasTestData) {
            readCarData()

            readTestData()

            readTestInfo()
        }

    }

    private void readCarData() {
        readMakeAndModel()

        readWeight()

        readPower()

        readTorque()

        readProductionYears()
    }

    private void readTestData() {
        readLaptimes()

        readAccelerationTimes()
    }

    private void readTestInfo() {
        Browser.drive {
            go car.url

            Navigator testDate = $("span", class: "y99 a100")
            if (!testDate.empty) {
                car.testDate = LocalDate.parse(testDate.text(), formatter)
            }

            Navigator driver = $("span", text: "Redakteur")
            if (!driver.previous().empty) {
                car.driver = driver.previous().text()
            }
        }
    }

    private boolean articleHasTestResult() {
        boolean hasTestResult = true;
        Browser.drive {
            hasTestResult = !$("span", text: "Technische Daten").empty
        }
        return hasTestResult;
    }

    private void readMakeAndModel() {
        Navigator carName
        Browser.drive {
            carName = $("th", 1)
        }
        String carNameText = carName.text()
        String make = carNameText.take(carNameText.indexOf(" "))
        String model = carNameText.drop(carNameText.indexOf(" ") + 1)
        car.make = make
        car.model = model
    }

    private void readLaptimes() {
        car.nordschleifeTime = readLapTime("Nordschleife")
        car.hockenheimTime = readLapTime("Hockenheim")
    }

    private static String readLapTime(String text) {
        Navigator section
        Browser.drive {
            section = $("td", text: text)
        }
        String time = ""
        if (!section.empty) {
            section = section.parent().parent().children()
            while (section.size() > 0) {
                if (section.children().getAt(0).text().equals("Rundenzeit")) {
                    time = section.children().getAt(1).text()
                }
                section = section.next()
            }
        }
        return time
    }

    private void readProductionYears() {
        String rowValue = getRowValue("Baujahr")
        if (!rowValue.empty) {
            car.productionYears = rowValue.replace("ab ", "").replace("bis", "to")
        }
    }

    private void readWeight() {
        String rowValue = getRowValue("Leergewicht Testwagen\nvollgetankt")
        if (!rowValue.empty) {
            car.weight = Integer.parseInt(rowValue.split(" ")[0])
        }
    }

    private void readPower() {
        String rowValue = getRowValue("Leistung")
        String power = getMatchedValue(rowValue, "\\d* PS")
        if (!power.empty) {
            car.power = Integer.parseInt(power)
        }
    }

    private void readTorque() {
        String rowValue = getRowValue("Max. Drehmoment")
        String torque = getMatchedValue(rowValue, "\\d* Nm")
        if (!torque.empty) {
            car.torque = Integer.parseInt(torque)
        }
    }

    private void readAccelerationTimes() {
        String time0100 = getRowValue("0-100 km/h\nMesswert").split()[0]
        if (!time0100.empty) {
            car.time100 = Float.parseFloat(time0100.replace(",", "."))
        }
        String time0200 = getRowValue("0-200 km/h\nMesswert").split()[0]
        if (!time0200.empty) {
            car.time200 = Float.parseFloat(time0200.replace(",", "."))
        }
    }

    private static String getMatchedValue(String rowValue, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rowValue);
        if (matcher.find()) {
            return matcher.group().split()[0]
        } else {
            return ""
        }
    }

    private String getRowValue(String rowName) {
        String rowValue = ""
        Browser.drive {
            Navigator row = $("td", text: rowName)
            if (!row.empty) {
                rowValue = row.next().text()
            }
        }
        return rowValue
    }
}
