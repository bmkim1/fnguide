package fnguide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_message", schema = "fnguide")
public class FeedMessage {

    @Id
    @Column(name = "feed_message_seq")
    private Integer feedMessageSeq;

    @Column(name = "categories")
    private String categories;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "file_date")
    private String fileDate;

    @Column(name = "creator")
    private String creator;

    @Column(name = "description")
    private String description;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "link")
    private String link;

    @Column(name = "source")
    private String source;

    @Column(name = "title")
    private String title;

    public String getFileDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateTimeToString = createAt.format(formatter);
        String result = dateTimeToString.substring(0,6);
        return result;
    }
}
