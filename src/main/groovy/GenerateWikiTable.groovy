import pl.tolkanowicz.ams.Car
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
        "! scope=\"col\" | Test Date\n" +
        "! scope=\"col\" | Nordschleife time\n" +
        "! scope=\"col\" | Hockenheim time\n" +
        "! scope=\"col\" | 0-100 km/h\n" +
        "! scope=\"col\" | 0-200 km/h\n" +
        "! scope=\"col\" | Driver\n" +
        "! scope=\"col\" | Production Years\n" +
        "! scope=\"col\" | Power\n" +
        "! scope=\"col\" | Ptwr kg/PS\n" +
        "! scope=\"col\" class=\"unsortable\" | Link")

cars.each {
    car ->
        float time100f = car.time100.trunc(1)
        String time200f = ""
        if(car.time200 != null) {
            time200f = car.time200.trunc(1)
        }
        car.driver = car.driver == null ? "" : car.driver
        car.gearbox = car.gearbox == null ? "" : car.gearbox
        float ptwr = ((Float)car.weight / car.power).trunc(1)
        wikitable.append("\n|-\n").
                append("| $car.make || $car.model || $car.testDate || $car.nordschleifeTime || $car.hockenheimTime || $time100f || $time200f " +
                        "|| $car.driver || $car.productionYears || $car.power || $ptwr || [http://www.auto-motor-und-sport.de$car.url link]")

        
}
wikitable.append("\n|}")
println wikitable