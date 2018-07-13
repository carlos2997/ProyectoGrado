/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GridFS;

import java.util.List;

/**
 *
 * @author Carlos Ramirez
 */
public class Polygon {
    private List<List<List<Double>>> coordinates;
    private String type;

    public Polygon() {
    }

    public Polygon(List<List<List<Double>>> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
    }

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
