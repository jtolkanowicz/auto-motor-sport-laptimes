import geb.Browser
import geb.navigator.Navigator
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.mongo.CarMongo
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage
import pl.tolkanowicz.ams.pages.SupertestPage

new LoginPage().login()
File testLinks = new File('Links.json')
JsonSlurper slurper = new JsonSlurper()
String jsonText = testLinks.getText()
List<String> links = slurper.parseText( jsonText )

links.eachWithIndex{ url, idx ->
    Car car = new Car(url)
    CarMongo carMongo = new CarMongo()
    if(!carMongo.carExists(car.id)){
        CarPage carPage = new CarPage(car)
        if (carPage.hasCarData()) {
            carPage.readData()
            carMongo.saveOrUpdateCar(car)
        }
    }
    println "Current id=$idx"
}