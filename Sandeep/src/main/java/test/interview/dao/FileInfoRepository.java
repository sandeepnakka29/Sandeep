package test.interview.dao;


import org.springframework.data.repository.CrudRepository;
import test.interview.dao.domain.FileInfo;

import java.sql.Timestamp;
import java.util.List;

public interface FileInfoRepository extends CrudRepository<FileInfo, Long> {
    List<FileInfo> findByIdIn(List<Long> ids);

    List<FileInfo> findByCreatedTimeAfter(Timestamp timestamp);
}
