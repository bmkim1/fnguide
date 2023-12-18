package fnguide.repository;

import fnguide.dto.OverseaCorpDto;
import fnguide.entity.OverseaDiscoveredCorp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OverseaDiscoveredCorpRepository extends JpaRepository<OverseaDiscoveredCorp, String> {

    /*@Query("SELECT " +
            "    fnguide.fn_empty_to_null(t1.overseaSeq) as overseaSeq, " +
            "    fnguide.fn_empty_to_null(t1.dmn) as dmn, " +
            "    fnguide.fn_empty_to_null(t1.discoveredDate) as discoveredDate, " +
            "    fnguide.fn_empty_to_null(t1.techCode1) as techCode1, " +
            "    fnguide.fn_empty_to_null(t1.techCode2) as techCode2, " +
            "    fnguide.fn_empty_to_null(t1.bsnCode1) as bsnCode1, " +
            "    fnguide.fn_empty_to_null(t1.bsnCode2) as bsnCode2, " +
            "    t2.vcCnt " +
            "FROM OverseaDiscoveredCorp t1 " +
            "LEFT JOIN vc_freq t2 ON t1.dmn = t2.domainName AND t1.discoveredDate = t2.fileDate " +
            "WHERE t1.discoveredDate = :tgDate " +
            "ORDER BY t1.discoveredDate, t1.overseaSeq")
    List<OverseaCorpDto> getCustomResults(@Param("tgDate") String tgDate);*/

}
