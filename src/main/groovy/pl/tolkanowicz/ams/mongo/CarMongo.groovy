package pl.tolkanowicz.ams.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
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
        Document document = new Document("_id", car._id).
                append("make", car.make).
                append("model", car.model).
                append("productionYears", car.productionYears).
                append("nordschleifeTime", car.nordschleifeTime).
                append("hockenheimTime", car.hockenheimTime).
                append("url", car.url).
                append("testTitle", car.testTitle).
                append("driver", car.driver).
                append("gearbox", car.gearbox).
                append("layout", car.layout).
                append("weight", car.weight).
                append("power", car.power).
                append("torque", car.torque).
                append("time100", car.time100).
                append("time200", car.time200)

        if (car.testDate != null) {
            document.append("testDate", car.testDate)
        }
        return document
    }

    private static Car getCarFromDocument(Document document) {
        String carJson = document.toJson()
        Object carMap = new JsonSlurper().parseText(carJson)
        Car car = new Car(carMap)
        return car
        /*Integer id = document.getInteger("_id")
        String make = document.getString("make")
        String model = document.getString("model")
        String productionYears = document.getString("productionYears")
        String nordschleifeTime = document.getString("nordschleifeTime")
        String hockenheimTime = document.getString("hockenheimTime")
        String url = document.getString("url")
        String testTitle = document.getString("testTitle")
        String driver = document.getString("driver")
        String gearbox = document.getString("gearbox")
        String layout = document.getString("layout")
        String testDate = document.getString("testDate")
        Integer weight = document.getInteger("weight")
        Integer power = document.getInteger("power")
        Integer torque = document.getInteger("torque")
        Double time100 = document.getDouble("time100")
        Double time200 = null
        if (document.get("time200") != null) {
            time200 = document.getDouble("time200")
        }
        String name = document.get("tyres")
        String tyresSource = document.getString("tyresSource")
        String spec = document.getString("tyresSpec")
        Boolean optionalTyre = document.get("optionalTyre") == null ? false : document.get("optionalTyre")


        Car car = new Car(id: id, make: make, model: model, productionYears: productionYears,
                nordschleifeTime: nordschleifeTime, hockenheimTime: hockenheimTime, url: url, testTitle: testTitle,
                driver: driver, gearbox: gearbox, layout: layout, testDate: testDate, weight: weight, power: power,
                torque: torque, time100: time100, time200: time200, tyres: new Tyres(name: name, spec: ))*/
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
