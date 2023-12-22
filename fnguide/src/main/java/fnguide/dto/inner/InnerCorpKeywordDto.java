package fnguide.dto.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerCorpKeywordDto {

    private String enr;
    private String companyName;
    private String crn;
    private String discoveredDate;
    private Integer keywordSeq;
    private String keyword;
    private Integer companyInfoFreq;
    private Integer patentFreq;
    private Integer utilityFreq;
    private Integer rndFreq;
    private Integer homepageFreq;
    private Integer innerFreq;
    private Integer keywordRowCount;

}
