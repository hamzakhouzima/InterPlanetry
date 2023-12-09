package com.youcode.interplanetary.ToolKit.ToolKitImpl;

import com.youcode.interplanetary.ToolKit.Entity.MetaData;
import com.youcode.interplanetary.ToolKit.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MetaDataCollection  {
    @Autowired
    private  MetaRepository metaRepository;

    @Autowired
    private IPFSFileStorage ipfsFileStorage;


//    private MetaRepository dataRepository;
    //TODO : Add the rest of the methods to store the data related to the content ( CID ,Date ,Content_relative )

//    String CID = Ipfs.getCid();

public String storeMetaData(MultipartFile file) {
    try{
        String CID = ipfsFileStorage.uploadFile(file);
            MetaData metaData = MetaData.builder()
                    .userCid(CID)
                    .build();
        metaRepository.save(metaData);
        return CID;
    }catch(Exception e){
        throw new RuntimeException("Error while communicating with the IPFS node", e);
    }

}


}
