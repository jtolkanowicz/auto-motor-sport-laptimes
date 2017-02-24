package pl.tolkanowicz.ams.mongo

import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.conversions.Bson
import pl.tolkanowicz.ams.TestLink

import static com.mongodb.client.model.Filters.eq

/**
 * Created by jacek on 21.02.17.
 */
class TestLinkMongo {

    Connection connection

    MongoCollection<Document> collection

    public TestLinkMongo(){
        connection = new Connection()

        collection = connection.database.getCollection("links")
    }

    private static Document getDocumentFromTestLink(TestLink testLink){
        return new Document("_id", testLink.id).
                append("url", testLink.url).
                append("verified", testLink.verified).
                append("hasTestData", testLink.hasTestData)
    }

    private static TestLink getTestLinkFromDocument(Document document){
        Integer id = document.get("_id")
        String url = document.get("url")
        Boolean verified = document.get("verified")
        Boolean hasTestData = document.get("hasTestData")
        return new TestLink(id, url, verified, hasTestData)
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

    public TestLink getTestLink(Integer id){
        Document document = collection.find(eq("_id", id)).first()
        return getTestLinkFromDocument(document)
    }

    public List<TestLink> getAllNotVerifiedLinks(){
        List<TestLink> testLinks = new ArrayList<>()
        collection.find(eq("verified", false)).each {
            document ->
            testLinks.add(getTestLinkFromDocument(document))
        }
        return testLinks
    }

    public List<TestLink> getAllLinksWithTestData(){
        List<TestLink> testLinks = new ArrayList<>()
        collection.find(eq("hasTestData", true)).each {
            document ->
                testLinks.add(getTestLinkFromDocument(document))
        }
        return testLinks
    }

}
