package pl.tolkanowicz.ams.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
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

    public CarMongo(){
        connection = new Connection()

        collection = connection.database.getCollection("results")
    }

    private static Document getDocument(Car car){
        Document document = new Document("_id", car.id).
                append("make", car.make).
                append("model", car.model).
                append("productionYears", car.productionYears).
                append("nordschleifeTime", car.nordschleifeTime).
                append("hockenheimTime", car.hockenheimTime).
                append("url", car.url).
                append("driver", car.driver).
                append("gearbox", car.gearbox).
                append("layout", car.layout).
                append("weight", car.weight).
                append("power", car.power).
                append("torque", car.torque).
                append("time100", car.time100).
                append("time200", car.time200)


        if(car.testDate != null){
            document.append("testDate", car.testDate.toString())
        }
        return document
    }

    public void saveOrUpdateCar(Car car) {
        Document carDocument = getDocument(car)

        Bson filter = eq("_id", car.id)

        Bson update =  new Document('$set', carDocument)
        UpdateOptions options = new UpdateOptions().upsert(true)

        collection.updateOne(filter, update, options)
    }

    public void createCar(Car car) {
        Document carDocument = getDocument(car)

        collection.insertOne(carDocument)
    }

    public boolean carExists(Integer id){
        Document car = getCar(id)
        return car != null
    }

    public Document getCar(Integer id){
        Document car = collection.find(eq("_id", id)).first()
        return car
    }

}
