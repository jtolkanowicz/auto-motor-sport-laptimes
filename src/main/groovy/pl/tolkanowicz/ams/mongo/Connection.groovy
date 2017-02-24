package pl.tolkanowicz.ams.mongo

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase

/**
 * Created by jacek on 20.02.17.
 */
class Connection {

    MongoClient mongoClient

    MongoDatabase database

    public Connection(){
        mongoClient = new MongoClient()

        database = mongoClient.getDatabase("ams")
    }

}
