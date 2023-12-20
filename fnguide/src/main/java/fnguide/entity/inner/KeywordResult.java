package fnguide.entity.inner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "keyword_result", schema = "keyword")
public class KeywordResult {

    @Id
    @Column(name = "id_seq")
    private Integer idSeq;

    @Column(name = "keyword_seq")
    private String keywordSeq;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "has_company_info")
    private String hasCompanyInfo;

    @Column(name = "has_copyright")
    private String hasCopyright;

    @Column(name = "has_patent")
    private String hasPatent;

    @Column(name = "has_utility")
    private String hasUtility;

    @Column(name = "has_rnd")
    private String hasRnd;

    @Column(name = "has_homepage")
    private String hasHomepage;

    @Column(name = "has_article")
    private String hasArticle;

    @Column(name = "news_count")
    private Integer newsCount;

    @Column(name = "keyword_result_seq")
    private String keywordResultSeq;

    @Column(name = "is_ksic")
    private String isKsic;

    @Column(name = "has_ksic_keyword")
    private String hasKsicKeyword;

    @Column(name = "company_info_matched_cnt")
    private Integer companyInfoMatchedCnt;

    @Column(name = "copyright_matched_cnt")
    private Integer copyrightMatchedCnt;

    @Column(name = "patent_matched_cnt")
    private Integer patentMatchedCnt;

    @Column(name = "utility_matched_cnt")
    private Integer utilityMatchedCnt;

    @Column(name = "rnd_matched_cnt")
    private Integer rndMatchedCnt;

    @Column(name = "article_matched_cnt")
    private Integer articleMatchedCnt;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "project_name")
    private String projectName;
}
