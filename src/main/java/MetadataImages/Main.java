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
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author carlos
 */
public class Main {
    
    private static Image img;
    
    public static Double toDecimalResult(String degreeCoordinate) {
        try {
            String[] degree = degreeCoordinate.replaceAll("[^0-9.\\s-]", "").split(" ");
            Double decimalConv = toDecimal(degree); 
            return decimalConv;
        } catch(Exception ex) {
            System.out.println(String.format("Error en el formato de las coordenadas:"));
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
        File f = new File("//home//carlos//Documents//fotos//IMG_170510_171014_0030_GRE.TIF");
        img = new Image();
        if (f.exists()) {
            System.out.println("Si existe el archivo");
            try {
                BufferedImage tif = ImageIO.read(f);
                File testpng = new File("test.png");
                ImageIO.write(tif,"png",testpng);
                BufferedImage tif2 = ImageIO.read(testpng);
                File testjpg = new File("testing.jpg");
                ImageIO.write(tif2,"jpg",testjpg);
                Metadata metadata = JpegMetadataReader.readMetadata(testjpg);
                extractMetada(metadata);
                System.out.println(img);
            } catch (JpegProcessingException | IOException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("No existe el archivo");
        }
    }
    
    private static void extractMetada(Metadata metadata){
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
                System.out.println(tag.getDescription());
            }
        }
    }
}
