package pl.tolkanowicz.ams.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.bson.conversions.Bson
import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.TestLink

import static com.mongodb.client.model.Filters.eq
import static com.mongodb.client.model.Filters.eq

/**
 * Created by jacek on 21.02.17.
 */
class TestLinkMongo {

    Connection connection

    MongoCollection<Document> collection

    public TestLinkMongo(){
        connection = new Connection()

        collection = connection.database.getCollection("supertestLinks")
    }

    private static Document getDocument(TestLink testLink){
        return new Document("_id", testLink.id).
                append("url", testLink.url).
                append("verified", testLink.verified).
                append("hasCarData", testLink.hasCarData)
    }

    public void createTestLink(TestLink testLink) {
        Document testLinkDocument = getDocument(testLink)

        collection.insertOne(testLinkDocument)
    }

    public boolean testLinkExists(String url){
        Document testLink = getTestLink(url)
        return testLink != null
    }

    public Document getTestLink(String url){
        Document testLink = connection.collection.find(eq("url", url)).first()
        return testLink
    }

    public List<TestLink> getAllLinks(){
        List<TestLink> urls = connection.collection.find() as List<TestLink>
        return urls
    }

    public List<TestLink> getAllNotVerifiedLinks(){
        List<TestLink> urls = connection.collection.find(eq("verified", false)) as List<TestLink>
        return urls
    }

}
