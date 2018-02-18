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

/**
 *
 * @author carlos
 */
public class Main {

    public static void main(String[] args) throws Throwable {
        File f = new File("//home//carlos//Downloads//Carlos//DJI_0194.JPG");

        if (f.exists()) {
            System.out.println("Si existe el archivo");
            try {
                Metadata metadata = JpegMetadataReader.readMetadata(f);

                print(metadata, "Using JpegMetadataReader");
            } catch (JpegProcessingException | IOException e) {
                print(e);
            }

        } else {
            System.out.println("No existe el archivo");
        }

    }
    
    private static void print(Metadata metadata, String method){
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.print(' ');
        System.out.print(method);
        System.out.println("-------------------------------------------------");
        System.out.println();

        //
        // A Metadata object contains multiple Directory objects
        //
        for (Directory directory : metadata.getDirectories()) {

            //
            // Each Directory stores values in Tag objects
            //
            for (Tag tag : directory.getTags()) {
                if(tag.getTagName().equals("GPS Latitude")){
                    System.out.println("    "+tag.getDescription());
                }
                
                System.out.println(tag);
            }

            //
            // Each Directory may also contain error messages
            //
            for (String error : directory.getErrors()) {
                System.err.println("ERROR: " + error);
            }
        }
    }

    private static void print(Exception exception){
        System.err.println("EXCEPTION: " + exception);
    }
}
