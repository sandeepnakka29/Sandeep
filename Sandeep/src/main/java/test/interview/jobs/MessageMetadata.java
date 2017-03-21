package test.interview.jobs;

public class MessageMetadata {
        private String subject;
        private String from;
        private String recipient;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    @Override
    public String toString() {
        return "Message{" +
                "subject='" + subject + '\'' +
                ", from=" + from +
                ", recipient=" + recipient +
                '}';
    }
}
