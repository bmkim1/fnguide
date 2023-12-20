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


}
