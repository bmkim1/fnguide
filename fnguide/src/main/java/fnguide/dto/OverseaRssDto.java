package fnguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OverseaRssDto {

    private Integer feedMessageSeq;
    private String categories;
    private LocalDateTime createAt;
    private String fileDate;
    private String creator;
    private String description;
    private String keywords;
    private String link;
    private String source;
    private String title;

}
