package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import pl.tolkanowicz.ams.Car

/**
 * Created by jacek on 09.01.17.
 */
class CarPage {

    String url;

    Car car = null

    private boolean hasCarData = false

    private Navigator nordschleifeSection

    private Navigator carName

    public boolean hasCarData() {
        Browser.drive {
            go url + "/technische-daten/"
            if (articleHasTestResult()) {
                nordschleifeSection = $("td", text: "Nordschleife")
                carName = $("th", 1)
                if (!nordschleifeSection.empty && !carName.empty) {
                    hasCarData = true
                }
            }
        }
        return hasCarData
    }

    public Car getCarData() {
        if (hasCarData) {
            Browser.drive {
                car = new Car()

                getMakeAndModel(carName.text())

                nordschleifeSection = nordschleifeSection.parent().parent().children()
                getTime(nordschleifeSection)
            }
            return car
        } else {
            return null
        }

    }

    private boolean articleHasTestResult() {
        boolean hasTestResult = true;
        Browser.drive {
            hasTestResult = !$("span", text: "Technische Daten").empty
        }
        return hasTestResult;
    }

    private getMakeAndModel(String temp) {
        String make = temp.take(temp.indexOf(" "))
        String model = temp.drop(temp.indexOf(" ") + 1)
        car.make = make
        car.model = model
    }

    private getTime(Navigator nordschleifeSection) {
        String time = "0:00"

        while (nordschleifeSection.size() > 0) {
            if (nordschleifeSection.children().getAt(0).text().equals("Rundenzeit")) {
                time = nordschleifeSection.children().getAt(1).text()
            }
            nordschleifeSection = nordschleifeSection.next()
        }
        car.time = time
    }

}
