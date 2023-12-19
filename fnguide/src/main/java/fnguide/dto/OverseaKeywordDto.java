package fnguide.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverseaKeywordDto {

    private String overseaSeq;
    private String dmn;
    private String discoveredDate;
    private Long keywordSeq;
    private String keywordEng;
    private String keywordKor;
    private Long keywordRowCount;

}
