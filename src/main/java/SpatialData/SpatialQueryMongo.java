/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpatialData;

import com.mongodb.BasicDBObject;

/**
 *
 * @author carlos
 */
public class SpatialQueryMongo {
    
    public SpatialQueryMongo(){}
    
    public BasicDBObject queryPointInPolygon(double[] pointCoordinates){
        BasicDBObject dbQuery = new BasicDBObject(
                "polygons",new BasicDBObject("$geoIntersects",
                           new BasicDBObject("$geometry", 
                           new BasicDBObject("type","Point").append("coordinates",pointCoordinates)
                          )));
        
        return dbQuery;
    }
    
    public BasicDBObject queryPolygonPictures(String nameLand){
        BasicDBObject dbQuery = new BasicDBObject(
                "metadata",new BasicDBObject("type","Picture").append("nameLand", nameLand));
        
        return dbQuery;
    }
}
