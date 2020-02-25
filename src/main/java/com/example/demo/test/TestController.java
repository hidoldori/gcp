package com.example.demo.test;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class TestController {

    //   public String gcsGetSignUrl() throws Exception{
 //       Storage storage = StorageOptions.getDefaultInstance().getService();

//                Storage storage = StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("C:/google_credential/application_default_credentials.json")))
//                .build()
//                .getService();

//        System.out.println("LOGTYPE:STORAGE-UPLOAD-START, storage.toString : " + storage.toString());


//        Random r = new Random();
//        int nextNum = r.nextInt();

//        BlobInfo blobinfo = BlobInfo.newBuilder(BlobId.of("onsalestorage", "test-file-0221-" + nextNum + ".txt")).build();
//        System.out.println("LOGTYPE:STORAGE-UPLOAD-..., blobinfo.getUpdateTime : " + blobinfo.toString());
//        System.out.println("LOGTYPE:STORAGE-UPLOAD-..., blobinfo.getUpdateTime : " + blobinfo.getBucket());
//        System.out.println("LOGTYPE:STORAGE-UPLOAD-..., blobinfo.getUpdateTime : " + blobinfo.getSelfLink());
//// Generate Signed URL
//        URL url = storage.signUrl(blobinfo, 10, TimeUnit.MINUTES);
//
//        System.out.println("LOGTYPE:STORAGE-UPLOAD-..., url: " + url + ":");
//        System.out.println("LOGTYPE:STORAGE-UPLOAD-FINISH");
//        return url.toString();


//        Date data = new Date(86400000);
//        AccessToken token = new AccessToken("AIzaSyDHy5sDGV9zD8nKrVv23MkYHcZdKzlmXqE", data);
//        Storage storage = StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.create(token))
//                .build()
//                .getService();
//
//        URL url = storage.signUrl(blobinfo, 10, TimeUnit.MINUTES);

 //       return url.toString();
//    }

    @GetMapping("gcsDown")
    public void gcsDown() throws Exception {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("LOGTYPE:STORAGE-DOWN-START, storage.toString : " + storage.toString());
        Blob blob = storage.get(BlobId.of("onsalestorage", "command.txt"));

        blob.downloadTo(Paths.get("C:/tmp/command.txt"));
        System.out.println("LOGTYPE:STORAGE-DOWN-FINISH");

        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();

        System.out.println("::::::::::::" + credentials.getAuthenticationType() + "::::::::::");
        System.out.println("::::::::::::" + credentials.toString() + "::::::::::");

    }



    @GetMapping("gcsUpload")
    public void gcsUpload() throws Exception {

        Storage storage = StorageOptions.getDefaultInstance().getService();

        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        System.out.println("::::::::::::" + credentials.getAuthenticationType() + "::::::::::");
        System.out.println("::::::::::::" + credentials.toString() + "::::::::::");
//
//        String bucketName = "onsalestorage";
//        int number = new Random().nextInt();
//        String blobName = "test-file-0221" + number + ".txt";
//
//        BlobId blobId = BlobId.of(bucketName, blobName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
//
//        // sample 1 - without signedURL
//        InputStream content = new ByteArrayInputStream("Hello, World!".getBytes("UTF-8"));
//        Blob blob = storage.create(blobInfo, content);


        // sample 2 - with signedURL

        String bucketName_1 = "onsalestorage";
        int numbers_1 = new Random().nextInt();
        String blobName_1 = "test-file-0221" + numbers_1 + ".txt";
        BlobId blobId_1 = BlobId.of(bucketName_1, blobName_1);
        BlobInfo blobInfo_1 = BlobInfo.newBuilder(blobId_1).setContentType("text/plain").build();

        URL signedURL = gcsGetSignUrl(storage, blobInfo_1);

        byte[] content_1 = "Hello_1, World_1!".getBytes("UTF-8");
        WriteChannel writer = storage.writer(signedURL);
        writer.write(ByteBuffer.wrap(content_1, 0, content_1.length));
        writer.close();


//gs://onsalestorage/screen-362687497.jpg

//        이미지 테스트
//        String bucketName_2 = "onsalestorage";
//        int numbers_2 = new Random().nextInt();
//        String blobName_2 = "screen" + numbers_2 + ".jpg";
//        BlobId blobId_2 = BlobId.of(bucketName_2, blobName_2);
//        BlobInfo blobInfo_2 = BlobInfo.newBuilder(blobId_2).setContentType("image/jpeg").build();
//
//       signedURL = gcsGetSignUrl(storage, blobInfo_2);
//        File file = new File("C:/tmp/screen.jpg");
//
//        byte[] imageBytes = FileUtils.readFileToByteArray(file);
//
//        writer = storage.writer(signedURL);
//        writer.write(ByteBuffer.wrap(imageBytes, 0, imageBytes.length));
//        writer.close();

    }

    public URL gcsGetSignUrl(Storage storage, BlobInfo blobInfo) throws Exception{

        URL signedURL = storage.signUrl(blobInfo, 10, TimeUnit.MINUTES); // 7, TimeUnit.DAYS

        System.out.println(signedURL.toString());
        return signedURL;
    }

}
