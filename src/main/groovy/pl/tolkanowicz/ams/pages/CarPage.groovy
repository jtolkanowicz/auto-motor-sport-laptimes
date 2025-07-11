package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import pl.tolkanowicz.ams.Car

/**
 * Created by jacek on 09.01.17.
 */
class CarPage extends Page {

    //static url = "/supertest/bmw-m3-smg-e36-im-supertest-der-gesellschaftliche-ueberflieger-im-test-2783833.html/technische-daten/"

    static url = "/supertest/mercedes-amg-gts-im-supertest-nordschleife-hockenheim-11578449.html/technische-daten/"

    public static Car getCarData(String carPage) {
        Car car = null;
        Browser.drive {
            go carPage + "/technische-daten/"
            Navigator nordschleifeSection = $("td", text: "Nordschleife")
            if (articleHasTestResult() && !nordschleifeSection.empty) {
                Navigator carName = $("th", 1)
                String make = "";
                String model = "";
                if (!carName.isEmpty()) {
                    String temp = carName.text()
                    make = temp.take(temp.indexOf(" "))
                    model = temp.drop(temp.indexOf(" ") + 1)
                }
                String time = "0:00"
                nordschleifeSection = nordschleifeSection.parent().parent().children()
                while (nordschleifeSection.size() > 0) {
                    if (nordschleifeSection.children().getAt(0).text().equals("Rundenzeit")) {
                        time = nordschleifeSection.children().getAt(1).text()
                    }
                    nordschleifeSection = nordschleifeSection.next()
                }
                car = new Car(make: make, model: model, time: time)
            }
        }
        return car;
    }

    private static boolean articleHasTestResult() {
        boolean hasTestResult = true;
        Browser.drive {
            hasTestResult = !$("span", text: "Technische Daten").empty
        }
        return hasTestResult;
    }

}
