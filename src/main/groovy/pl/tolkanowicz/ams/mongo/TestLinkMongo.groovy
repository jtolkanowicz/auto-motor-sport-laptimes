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

    private static Document getDocumentFromTestLink(TestLink testLink){
        return new Document("_id", testLink.id).
                append("url", testLink.url).
                append("verified", testLink.verified).
                append("hasCarData", testLink.hasCarData)
    }

    private static TestLink getTestLinkFromDocument(Document document){
        Integer id = document.get("_id")
        String url = document.get("url")
        Boolean verified = document.get("verified")
        Boolean hasCarData = document.get("hasCarData")
        return new TestLink(id, url, verified, hasCarData)
    }

    public void dropAllRecords(){
        collection.drop()
    }

    public void createTestLink(TestLink testLink) {
        Document testLinkDocument = getDocumentFromTestLink(testLink)

        collection.insertOne(testLinkDocument)
    }

    public void updateTestLink(TestLink testLink) {
        Document testLinkDocument = getDocumentFromTestLink(testLink)
        Bson update =  new Document('$set', testLinkDocument)

        collection.updateOne(eq("_id", testLink.id), update)
    }

    public Document getTestLink(String url){
        Document testLink = collection.find(eq("url", url)).first()
        return testLink
    }

    public List<TestLink> getAllNotVerifiedLinks(){
        List<TestLink> testLinks = new ArrayList<>()
        collection.find(eq("verified", false)).each {
            document ->
            testLinks.add(getTestLinkFromDocument(document))
        }
        return testLinks
    }

}
