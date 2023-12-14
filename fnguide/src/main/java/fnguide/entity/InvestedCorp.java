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
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invested_corp", schema = "fnguide")
public class InvestedCorp {

    @Id
    @Column(name = "oversea_seq")
    private String overseaSeq;

    @Column(name = "dmn")
    private String dmn;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "categories")
    private String categories;

    @Column(name = "company_infos")
    private String companyInfos;

    @Column(name = "file_date")
    private String fileDate;

    @Column(name = "homepage_url")
    private String homepageUrl;

}
