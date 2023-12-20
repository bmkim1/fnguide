package fnguide.entity.inner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inner_discovered_corp", schema = "fnguide")
public class InnerDiscoveredCorp {

    @Id
    @Column(name = "enr")
    private String enr;

    @Column(name = "discovered_date")
    private String discoveredDate;

    @Column(name = "keyword_1")
    private String keyword1;

    @Column(name = "tech_1")
    private String tech1;

    @Column(name = "tech_code_1")
    private String techCode1;

    @Column(name = "keyword_2")
    private String keyword2;

    @Column(name = "tech_2")
    private String tech2;

    @Column(name = "tech_code_2")
    private String techCode2;

}
