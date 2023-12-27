package fnguide.dto.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerCorpNormalKeywordDto {

    private String enr;
    private String companyName;
    private String crn;
    private String discoveredDate;
    private Long keywordSeq;
    private String keyword;
    private Long companyInfoFreq;
    private Long patentFreq;
    private Long utilityFreq;
    private Long rndFreq;
    private Long homepageFreq;
    private Long innerKeywordFreq;
    private Long keywordRowCount;
    private Long keywordFreq;
}
