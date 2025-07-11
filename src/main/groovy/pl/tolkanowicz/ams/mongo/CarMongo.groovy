package pl.tolkanowicz.ams.mongo

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.bson.conversions.Bson
import pl.tolkanowicz.ams.Car

import java.time.format.DateTimeFormatter

import static com.mongodb.client.model.Filters.eq

/**
 * Created by jacek on 20.02.17.
 */
class CarMongo {

    Connection connection

    public CarMongo(){
        connection = new Connection()
    }

    private static Document getDocument(Car car){
        return new Document("_id", car.id).
                append("make", car.make).
                append("model", car.model).
                append("productionYears", car.productionYears).
                append("nordschleifeTime", car.nordschleifeTime).
                append("hockenheimTime", car.hockenheimTime).
                append("url", car.url).
                append("testDate", car.testDate.toString()).
                append("weight", car.weight).
                append("power", car.power).
                append("torque", car.torque).
                append("time100", car.time100).
                append("time200", car.time200)
    }

    public void saveOrUpdateCar(Car car) {
        Document carDocument = getDocument(car)

        Bson filter = eq("_id", car.id)

        Bson update =  new Document('$set', carDocument)
        UpdateOptions options = new UpdateOptions().upsert(true)

        connection.collection.updateOne(filter, update, options)
    }

    public boolean carExists(Integer id){
        Document car = getCar(id)
        return car != null
    }

    public Document getCar(Integer id){
        Document car = connection.collection.find(eq("_id", id)).first()
        return car
    }

}
