package test.interview.service;

import org.springframework.stereotype.Service;
import test.interview.dao.FileInfoRepository;
import test.interview.dao.domain.FileInfo;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FileUploadService {

    //    public final static int ONE_HOUR = 1000 * 60 * 60;
    public final static int ONE_HOUR = 1000 * 60;
    private FileInfoRepository fileInfoRepository;

    public FileUploadService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public Long saveFile(FileInfo fileInfo) {
        try {
            FileInfo save = fileInfoRepository.save(fileInfo);
            return save.getId();
        } catch (RuntimeException re) {
            return null;

        }
    }

    public FileInfo fetchFileInfo(Long imageId) {
        return fileInfoRepository.findOne(imageId);
    }

    public List<FileInfo> searchFiles(List<Long> ids) {
        return fileInfoRepository.findByIdIn(ids);
    }

    public List<FileInfo> uploadedInLastOneHourFiles() {
        return fileInfoRepository.findByCreatedTimeAfter(new Timestamp(System.currentTimeMillis() - ONE_HOUR));
    }
}
