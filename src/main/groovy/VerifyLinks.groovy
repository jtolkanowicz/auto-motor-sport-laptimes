import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.TestLink
import pl.tolkanowicz.ams.mongo.TestLinkMongo
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage

/**
 * Created by jacek on 21.02.17.
 */

TestLinkMongo testLinkMongo = new TestLinkMongo()
List<TestLink> notVerifiedLinks = testLinkMongo.getAllNotVerifiedLinks()

new LoginPage().login()
notVerifiedLinks.each{ TestLink link ->
    CarPage carPage = new CarPage(link)
    link.verified = true
    link.hasCarData = carPage.hasCarData()
    testLinkMongo.updateTestLink(link)
    println "Current id " + link.id
}