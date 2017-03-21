package test.interview.jobs;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import test.interview.dao.domain.FileInfo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class MailService {
    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(MessageMetadata message, List<FileInfo> fileInfoList) throws MessagingException {
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setFrom(message.getFrom());
        simpleMailMessage.setTo(message.getRecipient());
        StringBuilder consolidatedText = new StringBuilder("Last one hour files: ");
        for (FileInfo fileInfo: fileInfoList){
            consolidatedText.append("File Name : ").append(fileInfo.getFileName())
                            .append(", Content Type: ").append(fileInfo.getContentType())
                            .append(", with size : ").append(fileInfo.getSize())
                            .append(", at : ").append(fileInfo.getCreatedTime())
                            .append("<br/>");
        }

        simpleMailMessage.setText(consolidatedText.toString());

        javaMailSender.send(simpleMailMessage);
    }
}
