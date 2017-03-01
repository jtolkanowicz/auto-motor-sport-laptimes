package pl.tolkanowicz.ams.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.bson.conversions.Bson
import pl.tolkanowicz.ams.Car

import java.time.LocalDate

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

        if (car.testDate != null) {
            document.append("testDate", car.testDate.toString())
        }
        return document
    }

    private static Car getCarFromDocument(Document document) {
        Integer id = document.get("_id")
        String make = document.get("make")
        String model = document.get("model")
        String productionYears = document.get("productionYears")
        String nordschleifeTime = document.get("nordschleifeTime")
        String hockenheimTime = document.get("hockenheimTime")
        String url = document.get("url")
        String driver = document.get("driver")
        String gearbox = document.get("gearbox")
        String layout = document.get("layout")
        LocalDate testDate = LocalDate.parse(document.get("testDate").toString())
        Integer weight = Integer.parseInt(document.get("weight").toString())
        Integer power = Integer.parseInt(document.get("power").toString())
        Integer torque = Integer.parseInt(document.get("torque").toString())
        Float time100 = Float.parseFloat(document.get("time100").toString())
        Float time200 = null
        if (document.get("time200") != null) {
            time200 = Float.parseFloat(document.get("time200").toString())
        }


        Car car = new Car(id: id, make: make, model: model, productionYears: productionYears, nordschleifeTime: nordschleifeTime, hockenheimTime: hockenheimTime, url: url, driver: driver,
                gearbox: gearbox, layout: layout, testDate: testDate, weight: weight, power: power, torque: torque, time100: time100, time200: time200)
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
        collection.find().each {
            document ->
                cars.add(getCarFromDocument(document))
        }
        return cars
    }

}
