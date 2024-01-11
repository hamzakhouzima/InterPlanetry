package com.youcode.interplanetary.NetworkStorage.Controller;


import com.youcode.interplanetary.NetworkStorage.Service.Impl.FileStorageServiceImpl;
import com.youcode.interplanetary.NetworkStorage.Service.MetaDataCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.SocketTimeoutException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class IPFSController {


//    @Autowired
//    private FileStorage ipfsService;
//
@Autowired
private MetaDataCollection ipfsService;

@Autowired
private FileStorageServiceImpl fileStorageServiceImpl;





    @PostMapping(value="/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileStorageServiceImpl.uploadFile(file);
            String cid = ipfsService.storeMetaData(file);
            return ResponseEntity.ok("File uploaded successfully. CID: " + cid);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/file/{hash}")
    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.ALL_VALUE);

        System.out.println(headers.getContentType());


//        Path filePath = Paths.get("C:", "Users", "Youcode", "Downloads", hash+".png");
        String fileExtension = getFileExtension(hash);
        Path filePath = Paths.get("C:", "Users", "Youcode", "Downloads", hash + fileExtension);
        byte[] bytes = fileStorageServiceImpl.downloadFile(hash, filePath.toString());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);

    }


    @GetMapping(value = "/isAvailable/{hash}")
    public ResponseEntity<String> isAvailable(@PathVariable("hash") String hash) throws SocketTimeoutException {
        try {
            boolean isAvailable = fileStorageServiceImpl.isAvailable(hash);
            if (isAvailable) {
                return ResponseEntity.ok("File is available");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }
        }catch(Exception e ){
                throw new SocketTimeoutException("No file found or timed out");
        }

    }


    @GetMapping(value = "/metaData/{hash}")
    public ResponseEntity<String> getMetaData(@PathVariable("hash") String hash) {
        try {
            Map<String, Object> metaData = fileStorageServiceImpl.getFileMetadata(hash);
            StringBuilder response = new StringBuilder();
            metaData.forEach((key, value) -> response.append(key).append(": ").append(value).append("\n"));
            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            System.out.println("exception triggered" + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }




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
