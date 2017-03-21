package test.interview.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import test.interview.dao.domain.FileInfo;
import test.interview.service.FileUploadService;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@Component
public class UploadedFilesJob {

    private FileUploadService fileUploadService;
    private MailService mailService;

    public UploadedFilesJob(FileUploadService fileUploadService, MailService mailService) {
        this.fileUploadService = fileUploadService;
        this.mailService = mailService;
    }

    @Scheduled(fixedRate = FileUploadService.ONE_HOUR)
    public void lastOneHourFiles() {
        System.out.println("Starting scheduler @ "+ new Date());
        List<FileInfo> fileInfoList = fileUploadService.uploadedInLastOneHourFiles();
        MessageMetadata message = new MessageMetadata();
        message.setFrom("interviewer@mailinator.com");
        message.setRecipient("jayapal@mailinator.com");
        message.setSubject("Interview Test email from scheduler!");
        try {
            if (!fileInfoList.isEmpty()) {
                mailService.sendMail(message, fileInfoList);
            }
        } catch (MessagingException e) {
            System.err.println("Sending an email failed");
            e.printStackTrace();
        }
    }
}
