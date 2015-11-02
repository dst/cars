package com.stefanski.cars.search;

import java.util.List;
import java.util.Map;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stefanski.cars.store.Car;

import static java.util.stream.Collectors.toList;

/**
 * @author Dariusz Stefanski
 */
@Component
public class AttributeSearchStore {

    private static final String DB_NAME = "cars";
    private static final String COLLECTION_NAME = "attributes";
    private static final String ID_FIELD = "_id";

    private DBCollection collection;

    @Autowired
    AttributeSearchStore(Mongo mongo) {
        DB db = mongo.getDB(DB_NAME);
        this.collection = db.getCollection(COLLECTION_NAME);
    }

    public void insertAttributesOf(Car car) {
        BasicDBObject doc = createDocument(car);
        collection.insert(doc);
    }

    public void updateAttributesOf(Car car) {
        BasicDBObject doc = createDocument(car);
        collection.save(doc);
    }

    public void deleteAttributesOf(Long carId) {
        BasicDBObject doc = new BasicDBObject(ID_FIELD, carId);
        collection.remove(doc);
    }

    public List<Long> findCars(Map<String, String> query) {
        BasicDBObject queryDoc = new BasicDBObject(query);
        BasicDBObject fieldsDoc = new BasicDBObject(ID_FIELD, 1);
        List<DBObject> dbObjects = collection.find(queryDoc, fieldsDoc).toArray();
        return extractIds(dbObjects);
    }

    private BasicDBObject createDocument(Car car) {
        BasicDBObject doc = new BasicDBObject(car.getAttributesMap());
        doc.put(ID_FIELD, car.getId());
        return doc;
    }

    private List<Long> extractIds(List<DBObject> dbObjects) {
        return dbObjects.stream()
                .map(dbObject -> (Long) dbObject.get(ID_FIELD))
                .collect(toList());
    }
}
