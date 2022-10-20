package com.prajwal.fileupload.Service;


import com.prajwal.fileupload.Entities.FileAttachment;
import com.prajwal.fileupload.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    FileRepository fileRepository;

    @Value("${file.local-dir}")
    private String localDir;

    @Value("${file.local-download}")
    private String downloadLocation;

    //upload a file
    @Override
    public FileAttachment upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String fileType   = file.getContentType();

        System.out.println(fileName);
        System.out.println(fileType);

        return fileRepository.save(new FileAttachment(fileName,fileType,file.getBytes()));
    }



    //download a file
    @Override
    public FileAttachment download(String fileId) {
        return fileRepository.findById(fileId).orElseThrow();
    }

    @Override
    public String uploadLocally(FileAttachment fileAttachment) throws IOException {

        String pathLocation = localDir+fileAttachment.getId()+fileAttachment.getFileName();

//        System.out.println(pathLocation);
        File myFile = new File(pathLocation);

        myFile.createNewFile();

        FileOutputStream myOutputStream = new FileOutputStream(myFile);

        myOutputStream.write(fileAttachment.getData());

        myOutputStream.close();

        return "file uploaded successfully...";
    }

    @Override
    public String downloadLocally(FileAttachment fileAttachment) throws IOException {
        String fileName = fileAttachment.getId()+fileAttachment.getFileName();

        File downloadFile  = new File(downloadLocation+fileName);
        downloadFile.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(downloadFile);

        File file = new File(localDir+fileName);

        FileInputStream fileInputStream = new FileInputStream(file);

        fileOutputStream.write(fileInputStream.readAllBytes());

        fileInputStream.close();
        fileOutputStream.close();

        return "file downloaded successfully...";
    }


}
