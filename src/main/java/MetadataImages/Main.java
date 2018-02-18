/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetadataImages;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author carlos
 */
public class Main {
    
    private static Image img;
    
    public static Double[] toDecimal(String latitude, String longitude) {
        try {
            String[] lat = latitude.replaceAll("[^0-9.\\s-]", "").split(" ");
            String[] lng = longitude.replaceAll("[^0-9.\\s-]", "").split(" ");
            Double dlat = toDecimal(lat); 
            Double dlng = toDecimal(lng);
            return new Double[]{dlat, dlng};
        } catch(Exception ex) {
            System.out.println(String.format("Error en el formato de las coordenadas: %s %s", new Object[]{latitude, longitude}));
            return null;
        }
    }
    
    public static Double toDecimal(String latOrLng) {
        try {
            String[] latlng = latOrLng.replaceAll("[^0-9.\\s-]", "").split(" ");
            Double dlatlng = toDecimal(latlng); 
            return dlatlng;
        } catch(Exception ex) {
            System.out.println(String.format("Error en el formato de las coordenadas: %s ", new Object[]{latOrLng}));
            return null;
        }
    }
 
    public static Double toDecimal(String[] coord) {
        double d = Double.parseDouble(coord[0]);
        double m = Double.parseDouble(coord[1]);
        double s = Double.parseDouble(coord[2]);
        double signo = 1;
        if (coord[0].startsWith("-"))
            signo = -1;
        return signo * (Math.abs(d) + (m / 60.0) + (s / 3600.0));
    }

    public static void main(String[] args) throws Throwable {
        File f = new File("//home//carlos//Downloads//Carlos//DJI_0194.JPG");
        img = new Image();
        if (f.exists()) {
            System.out.println("Si existe el archivo");
            try {
                Metadata metadata = JpegMetadataReader.readMetadata(f);
                print(metadata, "Using JpegMetadataReader");
                Double[] coord = toDecimal(img.getLatitude(), img.getLongitude());
                System.out.println(Arrays.toString(coord));
            } catch (JpegProcessingException | IOException e) {
                print(e);
            }

        } else {
            System.out.println("No existe el archivo");
        }

    }
    
    private static void print(Metadata metadata, String method){
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if(tag.getTagName().equals("GPS Latitude")){
                    img.setLatitude(tag.getDescription());
                    System.out.println("    "+tag.getDescription());
                }else if(tag.getTagName().equals("GPS Longitude")){
                    img.setLongitude(tag.getDescription());
                    System.out.println("    "+tag.getDescription());
                }else if(tag.getTagName().equals("File Modified Date")){
                    img.setModifiedDate(tag.getDescription());
                    System.out.println("    "+tag.getDescription());
                }else if(tag.getTagName().equals("File Size")){
                    img.setFileSize(tag.getDescription());
                    System.out.println("    "+tag.getDescription());
                }else if(tag.getTagName().equals("File Name")){
                    img.setName(tag.getDescription());
                    System.out.println("    "+tag.getDescription());
                }
            }
        }
    }

    private static void print(Exception exception){
        System.err.println("EXCEPTION: " + exception);
    }
}
