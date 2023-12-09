package com.youcode.interplanetary.ToolKit.ToolKitImpl;

import com.youcode.interplanetary.ToolKit.Entity.MetaData;
import com.youcode.interplanetary.ToolKit.FileStorage;
import com.youcode.interplanetary.ToolKit.MetaRepository;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Getter
@Setter

@Service
public class IPFSFileStorage implements FileStorage {

//    @Autowired
    private final IPFS ipfs;
    private FileStorage fileStorage;

    @Autowired
    private final MetaRepository dataRepository;


//    @Autowired
//    public IPFSFileStorage(FileStorage FileStorage) {
//        this.FileStorage = FileStorage;
////        this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
//    }

    private String cid;



//    public IPFSFileStorage(IPFSConfig ipfs) {
//        this.ipfs = ipfs;
//    }

        public IPFSFileStorage(MetaRepository dataRepository) {
            this.dataRepository = dataRepository;
            this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001"); // Initialize IPFS node
    }

    //this method's parameter should be changed to byte[] and use ipfs.dag.put()
    @Override
    public String uploadFile(MultipartFile file) {
//            MetaData metaData = new MetaData();

        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
//            IPFS ipfs = ipfsConfig.ipfs;

            NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(inputStream);
//                                        /\
            //  ||                    //  ||
            MerkleNode response = ipfs.add(is).get(0);

            cid = response.hash.toBase58();
//          MetaData  metaData = MetaData.builder()
//                  .User_Cid(cid)
//                  .build();
//
//            fileStorage.storeMetaData(metaData);

            return cid;
        } catch (IOException ex) {
            throw new RuntimeException("Error while communicating with the IPFS node", ex);
        }
    }


    @Override
    public byte[] downloadFile(String cid, String downloadPath) {
        try {
            // convert the CID to Multihash
            Multihash filePointer = Multihash.fromBase58(cid);

            byte[] content = ipfs.cat(filePointer);

            Path path = Paths.get(downloadPath);
            Files.write(path, content);
            System.out.println("File downloaded successfully to: " + downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public boolean isAvailable(String cid) {
        try {
            Multihash filePointer = Multihash.fromBase58(cid);
            ipfs.object.stat(filePointer);
            System.out.println("File is available on the IPFS network");
            return true;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error while communicating with the IPFS node", e);
        }
    }

    @Override
    public boolean reseedFile(String cid) {

        return false;
    }

    @Override
    public Map<String, Object> getFileMetadata(String cid) {
        return null;
    }

    @Override
    public List<String> listFilesInDirectory(String cid) {
        return null;
    }

    @Override
    public boolean pinFile(String cid) {
        return false;
    }

    @Override
    public boolean unpinFile(String cid) {
        return false;
    }

    @Override
    public void publishName(String name, String cid) {

    }

    @Override
    public String resolveName(String name) {
        return null;
    }
}
