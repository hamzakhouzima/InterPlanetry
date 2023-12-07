package com.youcode.interplanetary.ToolKit.ToolKitImpl;

import com.youcode.interplanetary.ToolKit.FileStorage;
import com.youcode.interplanetary.config.IPFSConfig;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class IPFSFileStorage implements FileStorage {

//    @Autowired
    private final IPFS ipfs;

//    public IPFSFileStorage(IPFSConfig ipfs) {
//        this.ipfs = ipfs;
//    }

        public IPFSFileStorage() {
        this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001"); // Initialize IPFS node
    }

    //this method's parameter should be changed to byte[] and use ipfs.dag.put()
    @Override
    public String uploadFile(NamedStreamable fileData) {
        try {
            MerkleNode cid = (MerkleNode) ipfs.add(fileData);
            return cid.hash.toBase58();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Upload failed due to IO issues", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error during upload", e);
        }
    }



    @Override
    public byte[] downloadFile(String cid, String downloadPath) {
        try {
            // Convert the CID to Multihash
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
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
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
