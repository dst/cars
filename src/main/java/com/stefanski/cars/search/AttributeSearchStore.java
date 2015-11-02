package com.stefanski.cars.search;

import java.util.List;
import java.util.Map;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.stefanski.cars.store.Car;
import com.stefanski.cars.store.DeletedCarEvent;
import com.stefanski.cars.store.NewCarEvent;
import com.stefanski.cars.store.UpdatedCarEvent;

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

    @EventListener
    public void insertAttributes(NewCarEvent event) {
        BasicDBObject doc = createDocument(event.getCar());
        collection.insert(doc);
    }

    @EventListener
    public void updateAttributes(UpdatedCarEvent event) {
        BasicDBObject doc = createDocument(event.getCar());
        collection.save(doc);
    }

    @EventListener
    public void deleteAttributes(DeletedCarEvent event) {
        BasicDBObject doc = new BasicDBObject(ID_FIELD, event.getCarId());
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
