package fnguide.repository.Inner;

import fnguide.entity.inner.UnvCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnvCompanyRepository extends JpaRepository<UnvCompany, Integer> {

}
