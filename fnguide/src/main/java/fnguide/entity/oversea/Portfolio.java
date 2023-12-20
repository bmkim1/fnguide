package fnguide.entity.oversea;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio", schema = "fnguide")
public class Portfolio {

    @Id
    @Column(name = "vc_seq")
    private String vcSeq;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "category")
    private String category;

    @Column(name = "company_info")
    private String companyInfo;

    @Column(name = "homepage_url")
    private String homepageUrl;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "file_date")
    private String fileDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "dmn", referencedColumnName = "dmn")
    @JoinColumn(name = "discovered_date", referencedColumnName = "discovered_date")
    private OverseaDiscoveredCorp overseaDiscoveredCorp;
}
