package fnguide.repository.oversea;

import fnguide.entity.oversea.OverseaDiscoveredCorp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OverseaDiscoveredCorpRepository extends JpaRepository<OverseaDiscoveredCorp, String> {

    @Query (nativeQuery = true, value =
            "WITH vc_freq as (\n" +
                    "\tSELECT\n" +
                    "\t\tdomain_name, file_date\n" +
                    "\t\t, count(*) vc_cnt\n" +
                    "\t\t, count(distinct vc_seq) unq_vc_cnt\n" +
                    "\tFROM fnguide.portfolio\n" +
                    "\tGROUP BY domain_name, file_date\n" +
                    ")\n" +
                    "SELECT\n" +
                    "\tt1.oversea_seq, dmn, discovered_date, tech_code_1, tech_code_2, bsn_code_1, bsn_code_2\n" +
                    "\t, t2.vc_cnt\n" +
                    "FROM fnguide.oversea_discovered_corp t1\n" +
                    "LEFT JOIN vc_freq t2 ON t1.dmn = t2.domain_name AND t1.discovered_date = t2.file_date\n" +
                    "WHERE discovered_date = :discoveredDate\n" +
                    "ORDER BY discovered_date, oversea_seq\t ")
    List<Object[]> findAllByOrderByOverseaSeqAsc (@Param("discoveredDate") String discoveredDate);

}
