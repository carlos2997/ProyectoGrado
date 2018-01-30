package GridFS;


import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSDownloadByNameOptions;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bson.Document;
import org.bson.types.ObjectId;

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

        // Create some custom options
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024)
                .metadata(new Document("type", "video"));

        ObjectId fileId = gridFSBucket.uploadFromStream("videoArmin", streamToUploadFrom, options);
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

        myDatabase = mongoClient.getDatabase("nuevaDB");

        gridFSBucket = GridFSBuckets.create(myDatabase);

        File file = new File("/home/carlos/Videos/Armin.mp4");

        uploadFile(file);
        
        findFilesStores();

        downloadFile();

    }

    private static void downloadFile() throws FileNotFoundException, IOException {
        FileOutputStream streamToDownloadTo = new FileOutputStream("/home/carlos/Documents/Armin.mp4");
        GridFSDownloadByNameOptions downloadOptions = new GridFSDownloadByNameOptions().revision(0);
        gridFSBucket.downloadToStreamByName("videoArmin", streamToDownloadTo, downloadOptions);
        streamToDownloadTo.close();
    }
}
