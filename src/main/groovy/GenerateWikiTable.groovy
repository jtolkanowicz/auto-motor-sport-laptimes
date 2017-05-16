import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.Tyres
import pl.tolkanowicz.ams.mongo.CarMongo

/**
 * Created by jacek on 25.02.17.
 */
CarMongo carMongo = new CarMongo()
List<Car> cars = carMongo.allCars
StringBuffer wikitable = new StringBuffer("{| class=\"wikitable sortable collapsible autocollapse\"\n" +
        "|+ Supertest results\n" +
        "|-\n" +
        "! scope=\"col\" | Make\n" +
        "! scope=\"col\" | Model\n" +
        "! scope=\"col\" | Test Date/Issue\n" +
        "! scope=\"col\" | Nordschleife time\n" +
        "! scope=\"col\" | Hockenheim time\n" +
        "! scope=\"col\" | 0-100 km/h\n" +
        "! scope=\"col\" | 0-200 km/h\n" +
        "! scope=\"col\" | Driver\n" +
        "! scope=\"col\" | Production Years\n" +
        "! scope=\"col\" | Power\n" +
        "! scope=\"col\" | Ptwr kg/PS\n" +
        "! scope=\"col\" | Tyres")

cars.each {
    car ->
        double time100d = car.time100.trunc(1)
        String time200d = ""
        if(car.time200 != null) {
            time200d = car.time200.trunc(1)
        }
        car.driver = car.driver == null ? "" : car.driver

        double ptwr = ((Double)car.weight / car.power).trunc(1)

        String nordschleifeTime = car.nordschleifeTime + " Min"
        String hockenheimTime = car.hockenheimTime + " Min"

        String tyreInfo = "";
        if(car.tyres != null) {
            Tyres tyres = car.tyres

            String tyresSpec = tyres.spec == null ? "" : " [" + tyres.spec + "]"
            tyreInfo = tyres.name + tyresSpec + (tyres.optional  ? " *" : "") + (tyres.source != null ?
                    " <ref>{{cite web|url=$tyres.source|title=$tyres.sourceTitle|accessdate=23 March 2017}}</ref>" : "")
        }

        String testInfo = car.testDate + " <ref>{{cite web|url=http://www.auto-motor-und-sport.de$car.url|title=$car.testTitle|work=Sportauto|accessdate=23 March 2017}}</ref>"
        wikitable.append("\n|-\n").
                append("| $car.make || $car.model || $testInfo || $nordschleifeTime || $hockenheimTime || $time100d || $time200d " +
                        "|| $car.driver || $car.productionYears || $car.power || $ptwr || $tyreInfo")

        
}
wikitable.append("\n|}")
println wikitable