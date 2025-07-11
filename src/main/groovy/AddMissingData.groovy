import com.mongodb.BasicDBList
import com.mongodb.DBObject
import com.mongodb.util.JSON
import groovy.json.JsonSlurper
import org.bson.Document
import pl.tolkanowicz.ams.mongo.CarMongo

/**
 * Created by jacek on 01.03.17.
 */
File updates = new File('updates.json')
String json = updates.getText()
CarMongo carMongo = new CarMongo()
BasicDBList list = (BasicDBList) JSON.parse(json)
list.each { dbObject ->
    Document car = (Document)dbObject
    carMongo.saveOrUpdateCar(car)
}

