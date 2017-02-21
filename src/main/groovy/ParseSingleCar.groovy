import groovy.json.JsonOutput
import org.bson.Document
import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.mongo.CarMongo
import pl.tolkanowicz.ams.mongo.Connection
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage

/**
 * Created by jacek on 15.02.17.
 */
new LoginPage().login()
String url = "/supertest/lamborghini-huracan-coupe-lp-610-4-supertest-11494782.html"
Car car = new Car(url)
CarMongo carMongo = new CarMongo()
if(!carMongo.carExists(car.id)){
    CarPage carPage = new CarPage(car)
    if (carPage.hasCarData()) {
        carPage.readData()
        carMongo.saveOrUpdateCar(car)
    }
}
String json = carMongo.getCar(car.id).toJson()
println JsonOutput.prettyPrint(json)


