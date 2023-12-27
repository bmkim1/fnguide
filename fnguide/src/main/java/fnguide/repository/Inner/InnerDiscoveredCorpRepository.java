package fnguide.repository.Inner;

import fnguide.entity.inner.InnerDiscoveredCorp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InnerDiscoveredCorpRepository extends JpaRepository<InnerDiscoveredCorp, String> {

    @Query("SELECT t2.enr, t2.companyName, t2.crn, t1.discoveredDate, t1.tech1, t1.techCode1, t1.tech2, t1.techCode2 " +
            "FROM InnerDiscoveredCorp t1 " +
            "LEFT JOIN UnvCompany t2 ON t1.enr = t2.enr " +
            "WHERE t1.discoveredDate = :discoveredDate " +
            "ORDER BY t1.discoveredDate, t2.enr")
    List<Object[]> getInnerCorp(@Param("discoveredDate") String discoveredDate);

    @Query(nativeQuery = true,value =
            "WITH tg as (\n" +
                    "\tselect * from (\n" +
                    "\t\tselect '202306' tg_date, 'fn_guide-tech_v1' keyword_target\n" +
                    "\t\tUNION ALL\n" +
                    "\t\tselect '202307' tg_date, 'fn_guide-tech_v1' keyword_target\n" +
                    "\t\tUNION ALL\n" +
                    "\t\tselect '202308' tg_date, 'fn_guide-tech_v2' keyword_target\n" +
                    "\t\tUNION ALL\n" +
                    "\t\tselect '202309' tg_date, 'fn_guide-tech_v3' keyword_target\n" +
                    "\t\tUNION ALL\n" +
                    "\t\tselect '202310' tg_date, 'fn_guide-tech_v3' keyword_target\n" +
                    "\t\tUNION ALL\n" +
                    "\t\tselect '202311' tg_date, 'fn_guide-tech_v3' keyword_target\n" +
                    "\t) A\n" +
                    "\twhere tg_date = :tgDate\n" +
                    "), base AS (\n" +
                    "\tSELECT \n" +
                    "\tt1.discovered_date, t2.enr, t2.id_seq, t2.company_name, t2.crn, \n" +
                    "\tunnest(regexp_split_to_array(concat_ws(\n" +
                    "            ',',\n" +
                    "            NULLIF(keyword_1, ''),\n" +
                    "            NULLIF(keyword_2, '')\n" +
                    "        ),\n" +
                    "        ','\n" +
                    "    )\n" +
                    "\t) AS keyword\n" +
                    "\tFROM tg, fnguide.inner_discovered_corp t1\n" +
                    "\tLEFT JOIN unv_company t2\n" +
                    "\tON t1.enr = t2.enr\n" +
                    "\twhere t1.discovered_date = tg.tg_date\n" +
                    "\tAND t2.id_seq is not null\n" +
                    ")\n" +
                    ", ORI AS (\n" +
                    "\tSELECT\n" +
                    "\t\tdistinct\n" +
                    "\t\tt1.id_seq\n" +
                    "\t\t, t2.keyword\n" +
                    "\t\t, sub_name\n" +
                    "\t\t, COALESCE(company_info_matched_cnt, 0) has_company_info\n" +
                    "\t\t, COALESCE(patent_matched_cnt,0) has_patent\n" +
                    "\t\t, COALESCE(utility_matched_cnt, 0) has_utility\n" +
                    "\t\t, COALESCE(copyright_matched_cnt, 0) has_copyright\n" +
                    "\t\t, COALESCE(rnd_matched_cnt, 0) has_rnd\n" +
                    "\t\t, COALESCE(article_matched_cnt, 0) has_article\n" +
                    "\t\t, COALESCE(homepage_matched_cnt, 0) has_homepage\n" +
                    "\t\t, has_ksic_keyword\n" +
                    "\t\t, 0 ksic_class\n" +
                    "\t\t\n" +
                    "\tFROM keyword.keyword_result T1 \n" +
                    "\tLEFT JOIN keyword.keyword_dictionary t2 ON T1.keyword_seq = T2.keyword_seq\n" +
                    "\tWHERE T1.project_name = (select keyword_target from tg)\n" +
                    "\tAND t1.id_seq IN (select id_seq from base)\n" +
                    ")\n" +
                    ", a as (\n" +
                    "\tselect\n" +
                    "\t\tid_seq, sub_name, keyword\n" +
                    "\t\t, count(*) cnt\n" +
                    "\t\t, sum(has_company_info) has_company_info\n" +
                    "\t\t, sum(has_patent) has_patent\n" +
                    "\t\t, sum(has_utility) has_utility\n" +
                    "\t\t, sum(has_copyright) has_copyright\n" +
                    "\t\t, sum(has_rnd) has_rnd\n" +
                    "\t\t, sum(has_article) has_article\n" +
                    "\t\t, sum(has_homepage) has_homepage\n" +
                    "\tfrom ori\n" +
                    "\tgroup by id_seq, sub_name, keyword\n" +
                    ")\n" +
                    ", b as (\n" +
                    "\tselect \n" +
                    "\t\t*\n" +
                    "\t\t, count(*) over (partition by id_seq, sub_name) freq -- 기업별, 키워드 내 공통 sub 수\n" +
                    "\t\t, (has_company_info + has_patent + has_utility + has_rnd + has_homepage) inner_keyword_freq -- 키워드 총 포함된 수\n" +
                    "\t\t, has_company_info company_info_freq\n" +
                    "\t\t, has_patent patent_freq\n" +
                    "\t\t, has_utility utility_freq\n" +
                    "\t\t, has_rnd rnd_freq\n" +
                    "\t\t, has_homepage homepage_freq\n" +
                    "\tfrom a\n" +
                    ")\n" +
                    ", c as (\n" +
                    "\tselect b.*\n" +
                    "\t\t, rank() over (partition by id_seq order by freq desc, inner_keyword_freq  desc, keyword) tier\n" +
                    "\tfrom b\n" +
                    ")\n" +
                    "\n" +
                    "SELECT\n" +
                    "\tt1.enr\n" +
                    "\t, t1.company_name, t1.crn, t1.discovered_date\n" +
                    "\t, row_number() over (partition by enr, discovered_date order by (company_info_freq + patent_freq + utility_freq + rnd_freq + homepage_freq) desc, t1.keyword) keyword_seq\n" +
                    "\t, t1.keyword\n" +
                    "\t, company_info_freq\n" +
                    "\t, patent_freq\n" +
                    "\t, utility_freq\n" +
                    "\t, rnd_freq\n" +
                    "\t, homepage_freq\n" +
                    "\t, (company_info_freq + patent_freq + utility_freq + rnd_freq + homepage_freq) inner_freq\n" +
                    "\t, COUNT(*) OVER (PARTITION BY enr, discovered_date) keyword_row_count\n" +
                    "FROM base t1\n" +
                    "LEFT JOIN c t2 ON t1.id_seq = t2.id_seq AND t1.keyword = t2.keyword\n" +
                    "ORDER BY t1.discovered_date, t1.enr \n")
    List<Object[]> getInnerKeyword (@Param("tgDate") String tgDate);


    @Query(nativeQuery = true, value =
           "WITH tg as (\n" +
                   "\tselect :tgDate tg_date\t\n" +
                   "), base as (\n" +
                   "\tSELECT t1.*, t2.id_seq, t2.crn, t2.company_name \n" +
                   "\tFROM tg, fnguide.inner_discovered_corp t1\n" +
                   "\tLEFT JOIN unv_company t2 ON t1.enr = t2.enr\n" +
                   "\tWHERE t1.discovered_date = tg.tg_date\n" +
                   "\tAND t2.id_seq is not null\n" +
                   ")\n" +
                   ", ORI AS (\n" +
                   "\tSELECT\n" +
                   "\t\tdistinct\n" +
                   "\t\tt1.id_seq\n" +
                   "\t\t, t2.keyword\n" +
                   "\t\t, sub_name\n" +
                   "\t\t, COALESCE(company_info_matched_cnt, 0) has_company_info\n" +
                   "\t\t, COALESCE(patent_matched_cnt,0) has_patent\n" +
                   "\t\t, COALESCE(utility_matched_cnt, 0) has_utility\n" +
                   "\t\t, COALESCE(copyright_matched_cnt, 0) has_copyright\n" +
                   "\t\t, COALESCE(rnd_matched_cnt, 0) has_rnd\n" +
                   "\t\t, COALESCE(article_matched_cnt, 0) has_article\n" +
                   "\t\t, COALESCE(homepage_matched_cnt, 0) has_homepage\n" +
                   "\t\t, has_ksic_keyword\n" +
                   "\t\t, 0 ksic_class\n" +
                   "\t\t\n" +
                   "\tFROM keyword.keyword_result T1 \n" +
                   "\tLEFT JOIN keyword.keyword_dictionary t2 ON T1.keyword_seq = T2.keyword_seq\n" +
                   "\tWHERE T1.project_name = 'fnguide_domestic_keyword' \n" +
                   "\tAND t1.id_seq IN (select id_seq from base)\n" +
                   ")\n" +
                   ", a as (\n" +
                   "\tselect\n" +
                   "\t\tid_seq, sub_name, keyword\n" +
                   "\t\t, count(*) cnt\n" +
                   "\t\t, sum(has_company_info) has_company_info\n" +
                   "\t\t, sum(has_patent) has_patent\n" +
                   "\t\t, sum(has_utility) has_utility\n" +
                   "\t\t, sum(has_copyright) has_copyright\n" +
                   "\t\t, sum(has_rnd) has_rnd\n" +
                   "\t\t, sum(has_article) has_article\n" +
                   "\t\t, sum(has_homepage) has_homepage\n" +
                   "\tfrom ori\n" +
                   "\tgroup by id_seq, sub_name, keyword\n" +
                   ")\n" +
                   ", b as (\n" +
                   "\tselect \n" +
                   "\t\t*\n" +
                   "\t\t, count(*) over (partition by id_seq, sub_name) freq -- 기업별, 키워드 내 공통 sub 수\n" +
                   "\t\t, (has_company_info + has_patent + has_utility + has_rnd + has_homepage) inner_keyword_freq -- 키워드 총 포함된 수\n" +
                   "\t\t, has_company_info company_info_freq\n" +
                   "\t\t, has_patent patent_freq\n" +
                   "\t\t, has_utility utility_freq\n" +
                   "\t\t, has_rnd rnd_freq\n" +
                   "\t\t, has_homepage homepage_freq\n" +
                   "\tfrom a\n" +
                   ")\n" +
                   ", c as (\n" +
                   "\tselect b.*\n" +
                   "\t\t, rank() over (partition by id_seq order by freq desc, inner_keyword_freq  desc, keyword) tier\n" +
                   "\tfrom b\n" +
                   ")\n" +
                   "\n" +
                   "select \n" +
                   "\t--c.*, t2.company_name\n" +
                   "\tenr, company_name, crn\n" +
                   "\t, discovered_date\n" +
                   "\t--, sub_name -- 기술분야\n" +
                   "\t, row_number() over (partition by discovered_date, enr order by inner_keyword_freq desc, keyword) keyword_seq\n" +
                   "\t, keyword -- 키워드\n" +
                   "\t--, freq\n" +
                   "\n" +
                   "\t, company_info_freq\n" +
                   "\t, patent_freq\n" +
                   "\t, utility_freq\n" +
                   "\t, rnd_freq\n" +
                   "\t, homepage_freq\n" +
                   "\t, inner_keyword_freq\n" +
                   "\t\n" +
                   "\t, count(*) over (partition by enr) keyword_row_count\n" +
                   "\t, count(*) over (partition by keyword) keyword_freq\n" +
                   "from c\n" +
                   "left join base t3 on c.id_seq = t3.id_seq\n" +
                   "--where t3.discovered_date = ''\n" +
                   "order by discovered_date, enr\n" +
                   "--LIMIT 100")
    List<Object[]> getInnerNormalKeyword (@Param("tgDate") String tgDate);


}
