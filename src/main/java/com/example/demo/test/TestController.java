package com.example.demo.test;

import com.google.cloud.storage.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class TestController {


    @GetMapping("gcs")
    public String gcs() {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("storage.toString : " + storage.toString());
        BlobInfo blobinfo = BlobInfo.newBuilder(BlobId.of("onsalestorage", "test.txt")).build();
        System.out.println("blobinfo.getUpdateTime : " + blobinfo.getUpdateTime());

// Generate Signed URL
        URL url = storage.signUrl(blobinfo, 10, TimeUnit.MINUTES);

        System.out.println("curl '" + url + "'");

        return url.toString();
    }

    @GetMapping("gcsDown")
    public void gcsDown() {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("storage.toString : " + storage.toString());
        Blob blob = storage.get(BlobId.of("onsalestorage", "test.txt"));

// Generate Signed URL
        blob.downloadTo(Paths.get("test-create-empty-blob"));

    }
}
