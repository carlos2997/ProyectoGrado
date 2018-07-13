/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GridFS;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author carlos
 */
public class Image {
    private String name;
    private int height, width;
    private BufferedImage img;
    private List<Double> coordinates;
    
    public Image(){
    }

    public Image(String name, int height, int width, BufferedImage img, List<Double> coordinates) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.img = img;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString(){
        return "name: "+name+", coordinates: "+coordinates+", height: "+Integer.toString(height)+", width: "+Integer.toString(width);
    }
}
