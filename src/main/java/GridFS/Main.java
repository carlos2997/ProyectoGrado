package GridFS;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author carlos
 */
public class Main {

    static GridFSBucket gridFSBucket;
    static MongoDatabase myDatabase;

    /*private static void findFilesStores() {
        List<String> dates = new ArrayList<>();
        gridFSBucket.find().forEach(
                new Block<GridFSFile>() {
            @Override
            public void apply(final GridFSFile gridFSFile) {
                String dateFile = gridFSFile.getMetadata().getString("date");
                if (dateFile != null && !dates.contains(dateFile)) {
                    dates.add(dateFile);
                }
            }
        });
        System.out.println(dates);
    }*/

    private static List<Image> findFilesStores2(String prefix) {
        List<Image> imgs = new ArrayList<>();
        gridFSBucket.find(Filters.regex("filename", prefix)).forEach(new Block<GridFSFile>() {
            @Override
            public void apply(final GridFSFile gridFSFile) {
                FileOutputStream streamToDownloadTo;
                Image img = new Image();
                try {
                    streamToDownloadTo = new FileOutputStream(gridFSFile.getFilename());
                    gridFSBucket.downloadToStream(gridFSFile.getFilename(), streamToDownloadTo);
                    streamToDownloadTo.close();
                   
                    
                    File f = new File(gridFSFile.getFilename());
                    BufferedImage bfimg = ImageIO.read(f);
                    img.setImg(bfimg);
                    img.setName(gridFSFile.getFilename());
                    img.setHeight(bfimg.getHeight());
                    img.setWidth(bfimg.getWidth());
                    img.setCoordinates(((List<Double>) gridFSFile.getMetadata().get("coordinates")));
                    imgs.add(img);
                    System.out.println(f.getName());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        System.out.println(imgs);
        return imgs;
    }

    public static void main(String[] args) throws Throwable {

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://10.8.0.23:27017"));

        myDatabase = mongoClient.getDatabase("prueba");

        gridFSBucket = GridFSBuckets.create(myDatabase);
        
        List<Double> coor1 = new ArrayList<>();
        coor1.add(-74.04499679699575);
        coor1.add(4.7990289263004895);
        
        List<Double> coor2 = new ArrayList<>();
        coor2.add(-74.04495522275602);
        coor2.add(4.7990503087476295);
        
        List<Double> coor3 = new ArrayList<>();
        coor3.add(-74.04494114115869);
        coor3.add(4.799030262703462);
        
        List<Double> coor4 = new ArrayList<>();
        coor4.add(-74.04497332766687);
        coor4.add(4.799016230472187);
        
        List<Double> coor5 = new ArrayList<>();
        coor5.add(-74.04499679699575);
        coor5.add(4.7990289263004895);
        
        List<List<Double>> lotm1 = new ArrayList<>();
        lotm1.add(coor1);
        lotm1.add(coor2);
        lotm1.add(coor3);
        lotm1.add(coor4);
        lotm1.add(coor5);
        
        List<List<List<Double>>> lote1 = new ArrayList<>();
        lote1.add(lotm1);

        TagLandlot tg = new TagLandlot("e5d84d00-8561-11e8-81fb-dffed39fab6c", "Tizon Tardio", "", "IMG_170510_171032_0037_RGB.JPG", 123, null, new Polygon(lote1, "polygon"),23);

        
        ImageML imgml = new ImageML(findFilesStores2("IMG_170510_171032_0037"), tg);
        imgml.calculateNDVI();

        //downloadFile();
        //restoreFile();
        //getFrontImage();
    }

    private static void downloadFile() throws FileNotFoundException, IOException {
        try {
            FileOutputStream streamToDownloadTo = new FileOutputStream("/home/carlos/Documents/DJI_0196.JPG");
            gridFSBucket.downloadToStream("DJI_0196.JPG", streamToDownloadTo);
            streamToDownloadTo.close();
            System.out.println(streamToDownloadTo.toString());

            File f = new File("/home/carlos/Documents/DJI_0196.JPG");
            if (f.exists()) {
                String resultBase64Encoded = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(f));
                System.out.println("este es el codificado");
                System.out.println(resultBase64Encoded);
            } else {
                System.out.println("no existe el archivo");
            }

        } catch (IOException e) {
            // handle exception
        }
    }

    public static void getFrontImage() throws IOException {

        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream("raiden.jpg");
        int fileLength = (int) downloadStream.getGridFSFile().getLength();
        byte[] bytesToWriteTo = new byte[fileLength];
        downloadStream.read(bytesToWriteTo);
        downloadStream.close();

        File file = new File("/home/carlos/Documents/raiden.jpg");

        try {

            OutputStream os = new FileOutputStream(file);
            os.write(bytesToWriteTo);
            System.out.println("Write bytes to file.");
            printContent(file);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printContent(File file) throws Exception {
        System.out.println("Print File Content");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }

}
