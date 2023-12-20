package fnguide.dto.inner;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class InnerCorpDto {

    private String enr;
    private String companyName;
    private String crn;
    private String discoveredDate;
    private String tech1;
    private String techCode1;
    private String tech2;
    private String techCode2;

}
