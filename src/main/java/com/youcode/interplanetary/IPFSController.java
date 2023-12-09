package com.youcode.interplanetary;


import com.youcode.interplanetary.ToolKit.FileStorage;
import com.youcode.interplanetary.ToolKit.ToolKitImpl.MetaDataCollection;
import com.youcode.interplanetary.service.IPFSService;
import com.youcode.interplanetary.service.Impl.IpfsServiceImpl;
import io.ipfs.api.NamedStreamable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class IPFSController {


//    @Autowired
//    private FileStorage ipfsService;
//
@Autowired
private MetaDataCollection ipfsService;
//    @Autowired
//    private IpfsServiceImpl ipfsService;

//    @GetMapping(value = "")
//    public String saveText(@RequestParam("filepath") String filepath) {
//        return ipfsService.uploadFile(filepath);
//    }

    @PostMapping(value="/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String cid = ipfsService.storeMetaData(file);
            return ResponseEntity.ok("File uploaded successfully. CID: " + cid);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
//
//    @GetMapping(value = "file/{hash}")
//    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-type", MediaType.ALL_VALUE);
//        Path filePath = Paths.get("C:", "Users", "Youcode", "Downloads", hash);
//        byte[] bytes = ipfsService.downloadFile(hash, filePath.toString());
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
//
//    }
////TODO  : handle the time out exception , because the ipfs network is slow
//    @GetMapping(value = "isAvailable/{hash}")
//    public ResponseEntity<String> isAvailable(@PathVariable("hash") String hash) {
//        boolean isAvailable = ipfsService.isAvailable(hash);
//        if (isAvailable) {
//            return ResponseEntity.ok("File is available");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
//        }
//    }



}
