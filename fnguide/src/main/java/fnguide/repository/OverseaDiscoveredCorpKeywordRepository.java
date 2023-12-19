package fnguide.repository;

import fnguide.entity.OverseaDiscoveredCorpKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OverseaDiscoveredCorpKeywordRepository extends JpaRepository<OverseaDiscoveredCorpKeyword, String> {

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "\toversea_seq\n" +
                    "\t, dmn\n" +
                    "\t, discovered_date\n" +
                    "\t, row_number() over (PARTITION BY oversea_seq order by keyword_eng) keyword_seq\n" +
                    "\t, keyword_eng\n" +
                    "\t, keyword_kor\n" +
                    "\t, COUNT(*) OVER (PARTITION BY dmn, discovered_date) keyword_row_count\n" +
                    "FROM fnguide.oversea_discovered_corp_keyword\n" +
                    "WHERE discovered_date = :discoveredDate\n" +
                    "ORDER BY discovered_date, oversea_seq, keyword_eng\t")
    List<Object[]> findAllByOrderByOverseaSeqAsc(@Param("discoveredDate") String discoveredDate);

}
