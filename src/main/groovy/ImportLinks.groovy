import pl.tolkanowicz.ams.TestLink
import pl.tolkanowicz.ams.mongo.TestLinkMongo
import pl.tolkanowicz.ams.pages.SupertestPage

/**
 * Created by jacek on 21.02.17.
 */
TestLinkMongo testLinkMongo = new TestLinkMongo()

Set<String> urls = new SupertestPage().getSupertestUrls()

urls.each { String url ->
    TestLink testLink = new TestLink(url)
    if (!testLinkMongo.testLinkExists(testLink.id)) {
        testLinkMongo.createTestLink(testLink)
    }
}