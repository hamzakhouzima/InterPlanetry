package com.youcode.interplanetary.NetworkStorage.service;
import org.springframework.web.multipart.MultipartFile;

public interface IPFSService {
    public String saveFile(String filePath);

    String saveFile(MultipartFile file);

    public byte[] loadFile(String hash);
}
