import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.TestLink
import pl.tolkanowicz.ams.mongo.CarMongo
import pl.tolkanowicz.ams.mongo.TestLinkMongo
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage

TestLinkMongo testLinkMongo = new TestLinkMongo()
CarMongo carMongo = new CarMongo()
List<Integer> testLinkIds = [9266605, 8911761, 1747833, 1041324, 1041363, 1347840, 1041516, 1041447, 1041040, 1040952, 1036024]
new LoginPage().login()
testLinkIds.eachWithIndex{ Integer id, int i ->
    TestLink link = testLinkMongo.getTestLink(id)
    CarPage carPage = new CarPage(link)
    Car car = carPage.car
    if(!carMongo.carExists(car.id)) {
        //manually set hasTestData to true, to continue with readData
        carPage.hasTestData()
        carPage.hasTestData = true
        carPage.readData()
        carMongo.createCar(car)
    }
}