package com.prajwal.fileupload.Repository;

import com.prajwal.fileupload.Entities.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileAttachment,String> {
}
