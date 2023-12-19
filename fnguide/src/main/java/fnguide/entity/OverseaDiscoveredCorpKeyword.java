package fnguide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "oversea_discovered_corp_keyword", schema = "fnguide")
public class OverseaDiscoveredCorpKeyword {

    @Id
    @Column(name = "oversea_seq")
    private String overseaSeq;

    @Column(name = "dmn")
    private String dmn;

    @Column(name = "discovered_date")
    private String discoveredDate;

    @Column(name = "keyword_eng")
    private String keywordEng;

    @Column(name = "keyword_kor")
    private String keywordKor;
}
