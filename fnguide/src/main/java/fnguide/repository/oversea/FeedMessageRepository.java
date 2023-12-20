package fnguide.repository.oversea;

import fnguide.entity.oversea.FeedMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedMessageRepository extends JpaRepository<FeedMessage, Integer> {

    Page<FeedMessage> findAllByOrderByFeedMessageSeqAsc(Pageable pageable);

}
