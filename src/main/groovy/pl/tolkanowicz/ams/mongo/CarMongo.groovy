package pl.tolkanowicz.ams.mongo

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.bson.Document
import org.bson.conversions.Bson
import pl.tolkanowicz.ams.Car

import static com.mongodb.client.model.Filters.eq

/**
 * Created by jacek on 20.02.17.
 */
class CarMongo {

    Connection connection

    MongoCollection<Document> collection

    public CarMongo() {
        connection = new Connection()

        collection = connection.database.getCollection("results")
    }

    private static Document getDocumentFromCar(Car car) {
        String json = new JsonBuilder(car)
        return Document.parse(json)
    }

    private static Car getCarFromDocument(Document document) {
        String carJson = document.toJson()
        Object carMap = new JsonSlurper().parseText(carJson)
        Car car = new Car(carMap)
        return car
    }

    public void saveOrUpdateCar(Car car) {
        Document carDocument = getDocumentFromCar(car)

        saveOrUpdateCar(carDocument)
    }

    public void saveOrUpdateCar(Document carDocument) {
        Bson filter = eq("_id", carDocument.get("_id"))

        Bson update = new Document('$set', carDocument)
        UpdateOptions options = new UpdateOptions().upsert(true)

        collection.updateOne(filter, update, options)
    }

    public void createCar(Car car) {
        Document carDocument = getDocumentFromCar(car)

        collection.insertOne(carDocument)
    }

    public boolean carExists(Integer id) {
        Document car = getCar(id)
        return car != null
    }

    public Document getCar(Integer id) {
        Document car = collection.find(eq("_id", id)).first()
        return car
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>()
        collection.find().sort(new BasicDBObject("nordschleifeTime", 1)).each {
            document ->
                cars.add(getCarFromDocument(document))
        }
        return cars
    }

}
