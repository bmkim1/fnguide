package fnguide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "oversea_discovered_corp", schema = "fnguide")
public class OverseaDiscoveredCorp {

    @Id
    @Column(name = "oversea_seq")
    private String overseaSeq;

    @Column(name = "dmn")
    private String dmn;

    @Column(name = "discovered_date")
    private String discoveredDate;

    @Column(name = "tech_code_1")
    private String techCode1;

    @Column(name = "tech_code_2", nullable = true)
    private String techCode2;

    @Column(name = "bsn_code_1")
    private String bsnCode1;

    @Column(name = "bsn_code_2", nullable = true)
    private String bsnCode2;

    @OneToMany(mappedBy = "overseaDiscoveredCorp", fetch = FetchType.LAZY)
    private List<Portfolio> portfolios;
}
