package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.navigator.EmptyNavigator
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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    private DateTimeFormatter formatterYearMonth = DateTimeFormatter.ofPattern("M/yyyy")

    private static Map<String, String> gearboxes = new HashMap<>()

    static {
        gearboxes.putAll(['Automatikgetriebe': 'Automatic', 'Schaltgetriebe': 'Manual', 'Doppelkupplungsgetriebe': 'dual clutch'])
    }

    private static Map<String, String> layouts = new HashMap<>()

    static {
        layouts.putAll(['Vorderradantrieb': 'FWD', 'Allradantrieb': 'AWD', 'Hinterradantrieb': 'RWD'])
    }


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

    private boolean articleHasTestResult() {
        boolean hasTestResult = true
        Browser.drive {
            hasTestResult = !$("span", text: "Technische Daten").empty
        }
        return hasTestResult
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

        readProductionYears()

        readWeight()

        readPower()

        readTorque()

        readGearbox()

        readLayout()
    }

    private void readTestData() {

        readLaptimes()

        readAccelerationTimes()
    }

    private void readTestInfo() {
        Browser.drive {
            go car.url
            String text = "Powered by"

            Navigator driver = $("a", href: contains("/autor/"))

            if (!driver.empty) {
                car.driver = driver.text()
                text = car.driver
            }

            Navigator testDate = $("span", text: text)
            if (!testDate.empty) {
                car.testDate = LocalDate.parse(testDate.previous().text(), formatter)
            }
        }
    }

    private void readMakeAndModel() {
        Navigator carName = new EmptyNavigator()
        Browser.drive {
            carName = $("th", 1)
        }
        String carNameText = carName.text()
        String make = carNameText.take(carNameText.indexOf(" "))
        String model = carNameText.drop(carNameText.indexOf(" ") + 1)
        car.make = make
        car.model = model
    }

    private void readProductionYears() {
        String date = ""
        String rowValue = getRowValue("Baujahr")
        if (!rowValue.empty) {
            if(rowValue.contains("ab")){
                String temp = rowValue.split()[1]
                date = YearMonth.parse(temp, formatterYearMonth).toString()
            } else {
                String[] temp = rowValue.split()
                date = YearMonth.parse(temp[0], formatterYearMonth).toString() + " to " + YearMonth.parse(temp[2], formatterYearMonth).toString()
            }
        }
        car.productionYears = date
    }

    private void readWeight() {
        String rowValue = getRowValue("Leergewicht Testwagen\nvollgetankt")
        if (!rowValue.empty) {
            car.weight = Integer.parseInt(rowValue.split()[0])
        }
    }

    private void readPower() {
        String rowValue = readRowInSection("Motor", "Leistung")
        String power = getMatchedValue(rowValue, "\\d* PS")
        if (!power.empty) {
            car.power = Integer.parseInt(power)
        }
    }

    private void readTorque() {
        String rowValue = readRowInSection("Motor", "Max. Drehmoment")
        String torque = getMatchedValue(rowValue, "\\d* Nm")
        if (!torque.empty) {
            car.torque = Integer.parseInt(torque)
        }
    }

    private void readGearbox() {
        String rowValue = getRowValue("Getriebe")
        if (!rowValue.empty) {
            String[] values = rowValue.split()
            String gears = values[0].replace("Gang,","speed")
            String type = gearboxes.get(values[1])
            car.gearbox = type + ", " + gears
        }
    }

    private void readLayout() {
        String rowValue = getRowValue("Antriebsart")
        if (!rowValue.empty) {
            car.layout = layouts.get(rowValue)
        }
    }

    private void readLaptimes() {
        car.nordschleifeTime = readRowInSection("Nordschleife", "Rundenzeit")
        car.hockenheimTime = readRowInSection("Hockenheim", "Rundenzeit")

    }

    private void readAccelerationTimes() {
        String time0100 = getRowValue("0-100 km/h\nMesswert")
        if (!time0100.empty) {
            car.time100 = parseAccelerationTime(time0100)
        }
        String time0200 = getRowValue("0-200 km/h\nMesswert")
        if (!time0200.empty) {
            car.time200 = parseAccelerationTime(time0200)
        }
    }

    private static Float parseAccelerationTime(String time){
        String temp = time.split()[0].replace(",", ".")
        return Float.parseFloat(temp)

    }

    private static String readRowInSection(String sectionName, String rowName) {
        Navigator section = new EmptyNavigator()
        Browser.drive {
            section = $("td", text: sectionName)
        }
        String value = ""
        if (!section.empty) {
            section = section.parent().parent().children()
            while (section.size() > 0) {
                if (section.children().getAt(0).text().equals(rowName)) {
                    value = section.children().getAt(1).text()
                }
                section = section.next()
            }
        }
        return value
    }

    private static String getMatchedValue(String rowValue, String regex) {
        Pattern pattern = Pattern.compile(regex)
        Matcher matcher = pattern.matcher(rowValue)
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
