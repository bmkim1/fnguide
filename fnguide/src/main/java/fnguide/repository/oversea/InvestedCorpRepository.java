package fnguide.repository.oversea;

import fnguide.entity.oversea.InvestedCorp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestedCorpRepository extends JpaRepository <InvestedCorp, String> {

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
                    "SELECT\n" +
                    "    t1.oversea_seq,\n" +
                    "    t1.dmn,\n" +
                    "    CASE WHEN t1.company_name !~ '(svg|png|jpg|jpeg|webp|http)' THEN t1.company_name ELSE null END AS company_name,\n" +
                    "    CASE WHEN trim(categories) = '' THEN null ELSE categories END AS categories,\n" +
                    "    CASE WHEN trim(company_infos) = '' THEN null ELSE company_infos END AS company_infos,\n" +
                    "    t1.file_date,\n" +
                    "    CASE WHEN trim(t1.homepage_url) = '' THEN null ELSE t1.homepage_url END AS homepage_url,\n" +
                    "    fnguide.fn_image_path(t2.image_path, t2.status_code, CAST(t2.live AS bool)) AS image_path,\n" +
                    "    t2.status_code,\n" +
                    "    t2.live,\n" +
                    "    CASE WHEN trim(t2.description) = '' THEN null ELSE t2.description END AS description,\n" +
                    "    CASE WHEN trim(t2.title) = '' THEN null ELSE t2.title END AS title,\n" +
                    "    CASE WHEN trim(t2.keyword) = '' THEN null ELSE t2.keyword END AS keyword,\n" +
                    "    t3.vc_cnt\n" +
                    "FROM tg\n" +
                    "JOIN fnguide.invested_corp t1 ON t1.file_date = tg.tg_date\n" +
                    "LEFT JOIN fnguide.homepage_info_data t2 ON CAST(t1.oversea_seq AS varchar) = t2.oversea_seq\n" +
                    "LEFT JOIN vc_freq t3 ON t1.dmn = t3.domain_name AND t1.file_date = t3.file_date\n" +
                    "WHERE CAST(t1.oversea_seq AS varchar) IN (SELECT oversea_seq FROM fnguide.oversea_discovered_corp WHERE discovered_date = tg.tg_date)\n" +
                    "ORDER BY t1.file_date, t1.oversea_seq;\n")
    List<Object[]> getOverseaScrapedCorp(@Param("tgDate") String tgDate);

}
