package fnguide.entity.oversea;

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
@Table(name = "homepage_info_data", schema = "fnguide")
public class HomepageInfoData {

    @Id
    @Column(name = "oversea_seq", nullable = true)
    private String overseaSeq;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "status_code")
    private int statusCode;

    @Column(name = "live")
    private String live;

    @Column(name = "description")
    private String description;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "keyword")
    private String keyword;

}
