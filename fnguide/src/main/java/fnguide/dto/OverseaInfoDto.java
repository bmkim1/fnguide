package fnguide.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverseaInfoDto {

    private String overseaSeq;
    private String dmn;
    private String companyName;
    private String categories;
    private String companyInfos;
    private String fileDate;
    private String homepageUrl;
    private String imagePath;
    private int statusCode;
    private boolean live;
    private String description;
    private String title;
    private String keyword;
    private int vcCnt;
}
