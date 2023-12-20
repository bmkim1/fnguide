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
            "WITH vc_freq AS (" +
                    "   SELECT " +
                    "       domain_name, file_date," +
                    "       count(*) vc_cnt," +
                    "       count(distinct vc_seq) unq_vc_cnt," +
                    "       STRING_agg(CAST(vc_seq as VARCHAR), ', ') vc_seqs" +
                    "   FROM fnguide.portfolio" +
                    "   GROUP BY domain_name, file_date" +
                    ")" +
                    "SELECT" +
                    "   t1.oversea_seq, t1.dmn, " +
                    "   CASE WHEN t1.company_name !~ '(svg|png|jpg|jpeg|webp|http)' THEN t1.company_name ELSE NULL END AS company_name," +
                    "   categories, company_infos, t1.file_date, t1.homepage_url," +
                    "   t2.image_path, status_code, live, description, title, keyword," +
                    "   t3.vc_cnt" +
                    " FROM fnguide.invested_corp t1" +
                    " LEFT JOIN fnguide.homepage_info_data t2 ON t1.oversea_seq = t2.oversea_seq" +
                    " LEFT JOIN vc_freq t3 ON t1.dmn = t3.domain_name AND t1.file_date = t3.file_date" +
                    " WHERE t1.oversea_seq IN (SELECT oversea_seq FROM fnguide.oversea_discovered_corp WHERE discovered_date = :discoveredDate)" +
                    " ORDER BY t1.oversea_seq")
    List<Object[]> findAllByOrderByOverseaSeqAsc(@Param("discoveredDate") String discoveredDate);

}
