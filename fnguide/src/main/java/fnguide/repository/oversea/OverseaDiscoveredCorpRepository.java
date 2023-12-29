package fnguide.repository.oversea;

import fnguide.entity.oversea.OverseaDiscoveredCorp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OverseaDiscoveredCorpRepository extends JpaRepository<OverseaDiscoveredCorp, String> {

    @Query(nativeQuery = true, value =
            "with tg as (\n" +
                    "\tselect :tgDate tg_date\n" +
                    "), vc_freq as (\n" +
                    "\tSELECT \n" +
                    "\t\tdomain_name, file_date\n" +
                    "\t\t, count(*) vc_cnt\n" +
                    "\t\t, count(distinct vc_seq) unq_vc_cnt\n" +
                    "\t\t, STRING_agg(CAST(vc_seq AS VARCHAR), ', ') vc_seqs\n" +
                    "\tFROM fnguide.portfolio\n" +
                    "\tgroup by domain_name, file_date\n" +
                    ")\n" +
                    "\n" +
                    "SELECT \n" +
                    "\tCASE WHEN trim(oversea_seq) = '' THEN null ELSE  oversea_seq END AS oversea_seq\n" +
                    "\t, CASE WHEN trim(dmn) = '' THEN null ELSE dmn END AS dmn\n" +
                    "\t, CASE WHEN trim(discovered_date) = '' THEN null ELSE discovered_date END AS discovered_date \n" +
                    "\t, CASE WHEN trim(tech_code_1) = '' THEN null ELSE tech_code_1 END AS tech_code_1  \n" +
                    "\t, CASE WHEN trim(tech_code_2) = '' THEN null ELSE tech_code_2 END AS tech_code_2 \n" +
                    "\t, CASE WHEN trim(bsn_code_1) = '' THEN null ELSE bsn_code_1 END AS bsn_code_1 \n" +
                    "\t, CASE WHEN trim(bsn_code_2) = '' THEN null ELSE bsn_code_2 END AS bsn_code_2\n" +
                    "\t, vc_cnt\n" +
                    "FROM tg, fnguide.oversea_discovered_corp t1\n" +
                    "LEFT JOIN vc_freq t2 ON t1.dmn = t2.domain_name AND t1.discovered_date= t2.file_date \n" +
                    "WHERE discovered_date = tg.tg_date\n" +
                    "ORDER BY discovered_date, oversea_seq")
    List<Object[]> getOverseaDiscoveredCorp(@Param("tgDate") String tgDate);

    @Query(nativeQuery = true, value =
           "with tg as (\n" +
                   "\tselect :tgDate tg_date\n" +
                   ")\n" +
                   "\n" +
                   "SELECT\n" +
                   "\tCASE WHEN trim(oversea_seq) = '' THEN null ELSE oversea_seq END AS oversea_seq\n" +
                   "\t, CASE WHEN trim(dmn) = '' THEN null ELSE dmn END AS dmn\n" +
                   "\t, CASE WHEN trim(discovered_date) = '' THEN null ELSE discovered_date END AS discovered_date \n" +
                   "\t, row_number() over (PARTITION BY oversea_seq order by keyword_eng) keyword_seq\n" +
                   "\t, CASE WHEN trim(keyword_eng) = '' THEN null ELSE keyword_eng END AS keyword_eng  \n" +
                   "\t, CASE WHEN trim(keyword_kor) = '' THEN null ELSE keyword_kor END AS keyword_kor   \n" +
                   "\t, COUNT(*) OVER (PARTITION BY dmn, discovered_date) keyword_row_count\n" +
                   "FROM tg, fnguide.oversea_discovered_corp_keyword\n" +
                   "WHERE discovered_date = tg.tg_date\n" +
                   "ORDER BY discovered_date, oversea_seq, keyword_eng" )
    List<Object[]> getOverseaKeyword (@Param("tgDate") String tgDate);

}
