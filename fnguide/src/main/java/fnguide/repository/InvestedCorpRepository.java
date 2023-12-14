package fnguide.repository;

import fnguide.entity.InvestedCorp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestedCorpRepository extends JpaRepository <InvestedCorp, Long> {

    /*@Query("SELECT t1 " +
            "FROM InvestedCorp t1 " +
            "LEFT JOIN HomepageInfoData t2 ON t1.overseaSeq = t2.overseaSeq " +
            "WHERE <your_condition_here> " +
            "ORDER BY t1.overseaSeq")
    Page<InvestedCorp> findMatchingCompany(Pageable pageable);*/

}
