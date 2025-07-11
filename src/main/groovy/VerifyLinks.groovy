import pl.tolkanowicz.ams.TestLink
import pl.tolkanowicz.ams.mongo.TestLinkMongo
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage

/**
 * Created by jacek on 21.02.17.
 */

TestLinkMongo testLinkMongo = new TestLinkMongo()
Set<TestLink> notVerifiedLinks = testLinkMongo.getAllNotVerifiedLinks()
Integer count = notVerifiedLinks.size()

new LoginPage().login()
notVerifiedLinks.eachWithIndex{ TestLink link, int i->
    CarPage carPage = new CarPage(link)
    link.verified = true
    link.hasTestData = carPage.hasTestData()
    testLinkMongo.updateTestLink(link)
    println "Current id=$i/$count; article url $link.url"
}