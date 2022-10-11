package com.prajwal.fileupload.Model;

public class ResponseData {

    private String fileName;

    private String downloadUrl;

    private String fileType;

    private long fileSize;


    //no args
    public ResponseData() {
    }


    //all args
    public ResponseData(String fileName, String downloadUrl, String fileType, long fileSize) {
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }


    //getters and setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
