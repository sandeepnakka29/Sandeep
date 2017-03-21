package test.interview.rest.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test.interview.dao.domain.FileInfo;
import test.interview.service.FileUploadService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FileUploadController {

    private FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    @RequestMapping(value = "/api/v1/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(required = false, value = "interviewFile") MultipartFile file) throws IOException, SQLException {
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        FileInfo fileInfo = new FileInfo();

        try (InputStream inputStream = file.getInputStream()) {
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            fileInfo.setImage(imageBytes);
        }

        fileInfo.setContentType(contentType);
        fileInfo.setFileName(originalFilename);
        fileInfo.setSize(size);
        Long fileId = fileUploadService.saveFile(fileInfo);
        return "File Uploaded Successful and Id is " + fileId;
    }

    @RequestMapping("/api/v1/file/{id}")
    public FileInfo getFileInfo(@PathVariable("id") Long imageId) throws IOException {
        return fileUploadService.fetchFileInfo(imageId);
    }

    @RequestMapping("/api/v1/file/search/{searchText}")
    public List<FileInfo> getFiles(@PathVariable("searchText") String searchText) throws IOException {
        List<String> strings = Arrays.asList(searchText.split(","));
        List<Long> list = new ArrayList<>(strings.size());
        for (String str : strings) {
            list.add(Long.parseLong(str));
        }

        return fileUploadService.searchFiles(list);
    }

    @RequestMapping("/api/v1/file/{id}/download")
    public void downloadFile(@PathVariable("id") Long imageId, HttpServletResponse response) throws IOException, SQLException {
        FileInfo fileInfo = fileUploadService.fetchFileInfo(imageId);

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileInfo.getImage());
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType(fileInfo.getContentType());
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }

    }
}
