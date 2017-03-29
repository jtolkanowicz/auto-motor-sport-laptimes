import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.TestLink
import pl.tolkanowicz.ams.mongo.CarMongo
import pl.tolkanowicz.ams.mongo.TestLinkMongo
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage

TestLinkMongo testLinkMongo = new TestLinkMongo()
CarMongo carMongo = new CarMongo()
List<TestLink> testLinks = testLinkMongo.getAllLinksWithTestData()
Integer count = testLinks.size()
new LoginPage().login()
testLinks.eachWithIndex{ TestLink link, int i ->
    CarPage carPage = new CarPage(link)
    Car car = carPage.car
    if(!carMongo.carExists(car._id) && carPage.hasTestData()) {
        println "Current id=$i/$count; article url $link.url"
        carPage.readData()
        carMongo.createCar(car)
    }
}
