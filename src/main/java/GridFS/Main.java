package GridFS;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSDownloadByNameOptions;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.bson.Document;
import org.bson.types.ObjectId;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author carlos
 */
public class Main {

    static GridFSBucket gridFSBucket;
    static MongoDatabase myDatabase;

    private static void uploadFile(File file) throws FileNotFoundException {
        // Get the input stream
        InputStream streamToUploadFrom = new FileInputStream(file);

        Document doc = new Document();
        doc.append("type", "Picture");
        doc.append("nameLand", "Carlos Ramirez");

        // Create some custom options
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024)
                .metadata(doc);

        ObjectId fileId = gridFSBucket.uploadFromStream(file.getName(), streamToUploadFrom, options);
        System.out.println("Archivo ya subido!!");
    }

    private static void findFilesStores() {
        gridFSBucket.find().forEach(
                new Block<GridFSFile>() {
            @Override
            public void apply(final GridFSFile gridFSFile) {
                System.out.println(gridFSFile.getFilename());
            }
        });
    }

    public static void main(String[] args) throws Throwable {

        MongoClient mongoClient = new MongoClient();

        myDatabase = mongoClient.getDatabase("prueba");

        gridFSBucket = GridFSBuckets.create(myDatabase);

        File file = new File("//home//carlos//Downloads//Carlos//DJI_0194.JPG");

        if (file.exists()) {

            System.out.println("El archivo si existe!!");

            uploadFile(file);

            findFilesStores();

            downloadFile(file.getName());
            
            //restoreFile(file.getName());
        } else {
            System.out.println("El archivo no existe!!");
        }

    }

    private static void downloadFile(String nameFile) throws FileNotFoundException, IOException {
        FileOutputStream streamToDownloadTo = new FileOutputStream("/home/carlos/Documents/" + nameFile);
        GridFSDownloadByNameOptions downloadOptions = new GridFSDownloadByNameOptions().revision(0);
        gridFSBucket.downloadToStreamByName(nameFile, streamToDownloadTo, downloadOptions);
        streamToDownloadTo.close();
    }

    private static void restoreFile(String nameFile) throws IOException {
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStreamByName(nameFile);
        int fileLength = (int) downloadStream.getGridFSFile().getLength();
        byte[] bytesToWriteTo = new byte[fileLength];
        downloadStream.read(bytesToWriteTo);
        downloadStream.close();  
    }
}
