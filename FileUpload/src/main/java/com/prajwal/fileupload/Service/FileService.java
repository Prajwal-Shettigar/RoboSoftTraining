package com.prajwal.fileupload.Service;

import com.prajwal.fileupload.Entities.FileAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    FileAttachment upload(MultipartFile file) throws IOException;

    FileAttachment download(String fileId);

    String uploadLocally(FileAttachment fileAttachment) throws IOException;

    String downloadLocally(FileAttachment fileAttachment) throws IOException;
}
