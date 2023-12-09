package com.youcode.interplanetary.ToolKit.Entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MetaData")
public class MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size") // in bytes
    private long fileSize;

    @Column(name = "User_Cid")
    private String userCid;

    @Column(name = "Date")
    private Date date;

    // Add getters, setters, and any necessary methods
}
