package com.youcode.interplanetary;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//import com.youcode.interplanetary.NetworkStorage.Service.FileStorageService;
import com.youcode.interplanetary.config.IPFSConfig;
import io.ipfs.api.MerkleNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class InterPlanetaryApplicationTests {

//
//    @Autowired
//    private FileStorageService ipfsStorageService;

    @MockBean
    private IPFSConfig ipfsConfig;  // Assuming IPFSConfig is an autowired dependency in IPFSStorageService

//    @BeforeEach
//    public void setUp() throws IOException {
//        // Initialize or mock dependencies if needed
//        // For example, you can mock the IPFS client response
//        List<MerkleNode> someMockResponse= List.of(
//                Mockito.mock(MerkleNode.class)
//        );
//        Mockito.when(ipfsConfig.ipfs.add(Mockito.any())).thenReturn(someMockResponse);
//    }

//    @Test
//    public void testUploadFile() throws IOException {
//        // Arrange
//        MultipartFile mockFile = mock(MultipartFile.class);
//
//        // Mock IPFSConfig or IPFS client response
//        List<MerkleNode> someMockResponse = List.of(Mockito.mock(MerkleNode.class));
//        when(ipfsConfig.ipfs.add(any())).thenReturn(someMockResponse);
//
//        // Act
//        String cid = ipfsStorageService.uploadFile(mockFile);
//
//        // Assert
//        assertNotNull(cid);
//    }
//


    @Test
    void contextLoads() {
    }

}
