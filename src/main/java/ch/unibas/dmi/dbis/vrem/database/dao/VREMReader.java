package ch.unibas.dmi.dbis.vrem.database.dao;

import ch.unibas.dmi.dbis.vrem.model.exhibition.Exhibition;
import ch.unibas.dmi.dbis.vrem.model.general.Tuple2;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VREMReader {


    private final static String EXHIBITION_COLLECTION = "exhibitions";


    private final MongoDatabase database;

    /**
     *
     * @param database
     */
    public VREMReader(MongoDatabase database) {
        this.database = database;
    }

    /**
     *
     * @param id
     * @return
     */
    public Exhibition getExhibition(ObjectId id) {
        final MongoCollection<Exhibition> exhibitions = database.getCollection(EXHIBITION_COLLECTION, Exhibition.class);
        return exhibitions.find(Filters.eq("_id",id)).first();
    }

    /**
     *
     * @return
     */
    public Map<String,String> listExhibitions() {
        final MongoCollection<Document> exhibitions = database.getCollection(EXHIBITION_COLLECTION);
        final Map<String,String> map = new HashMap<>();
        for (Document document : exhibitions.find().projection(Projections.include("_id", "name"))) {
            map.put(document.getObjectId("_id").toString(), document.getString("name"));
        }
        return map;
    }
}


