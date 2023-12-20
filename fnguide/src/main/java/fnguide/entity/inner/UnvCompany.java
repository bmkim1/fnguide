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
@Table(name = "unv_company", schema = "public")
public class UnvCompany {

    @Id
    @Column(name = "id_seq")
    private Integer idSeq;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_name_eng")
    private String companyNameEng;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "crn")
    private String crn;

    @Column(name = "enr")
    private String enr;

    @Column(name = "address")
    private String address;

    @Column(name = "corporate_type")
    private String corporateType;

    @Column(name = "est_date")
    private LocalDate estDate;

    @Column(name = "ksic_code")
    private String ksicCode;

    @Column(name = "ksic_broad")
    private String ksicBroad;

    @Column(name = "ksic_detail")
    private String ksicDetail;

    @Column(name = "field_of_business")
    private String fieldOfBusiness;

    @Column(name = "company_information")
    private String companyInformation;

    @Column(name = "represent_product")
    private String representProduct;

    @Column(name = "fax")
    private String fax;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "naver")
    private String naver;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "etc")
    private String etc;

    @Column(name = "product_summary")
    private String productSummary;

    @Column(name = "bsn")
    private String bsn;

    @Column(name = "bsn_purpose")
    private String bsnPurpose;

    @Column(name = "bsn_opr_month")
    private Integer bsnOprMonth;

    @Column(name = "hit")
    private Integer hit;

    @Column(name = "real_hit")
    private Integer realHti;
}
