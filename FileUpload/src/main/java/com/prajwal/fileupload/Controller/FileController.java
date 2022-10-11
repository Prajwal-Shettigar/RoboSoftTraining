package com.prajwal.fileupload.Controller;

import com.prajwal.fileupload.Entities.FileAttachment;
import com.prajwal.fileupload.Model.ResponseData;
import com.prajwal.fileupload.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
public class FileController {




    @Autowired
    FileService fileService;


    //upload the file
    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws IOException {


        //uploading in database
        FileAttachment fileAttachment = fileService.upload(file);


        //uploading locally
        System.out.println(fileService.uploadLocally(fileAttachment));

        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(String.valueOf(fileAttachment.getId())).toUriString();

        return new ResponseData(fileAttachment.getFileName(),downloadUrl,fileAttachment.getFileType(),file.getSize());
    }


    //download the file
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws IOException{

        //downloading from database
        FileAttachment fileAttachment = fileService.download(fileId);

        //downloading from local storage
        System.out.println(fileService.downloadLocally(fileAttachment));

//        System.out.println(fileAttachment.getFileType());
//        System.out.println(MediaType.parseMediaType(fileAttachment.getFileType()));


        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileAttachment.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + fileAttachment.getFileName() + "\"").body(new ByteArrayResource(fileAttachment.getData()));
    }
}
