package com.youcode.interplanetary.NetworkStorage.Service;

import org.springframework.web.multipart.MultipartFile;

public interface MetaDataService {

     String storeMetaData(MultipartFile file);

     String updateMetaData(MultipartFile file , String idNum);
}
