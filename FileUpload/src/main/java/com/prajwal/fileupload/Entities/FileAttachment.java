package com.prajwal.fileupload.Entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class FileAttachment {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String fileName;

    private String fileType;


    @Lob
    private byte[] data;


    //no args contructor
    public FileAttachment() {
    }


    //all args contructor
    public FileAttachment(String fileName, String filePath, byte[] data) {
        this.fileName = fileName;
        this.fileType = filePath;
        this.data = data;
    }


    //getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
