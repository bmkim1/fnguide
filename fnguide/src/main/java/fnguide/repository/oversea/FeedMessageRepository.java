package fnguide.repository.oversea;

import fnguide.entity.oversea.FeedMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedMessageRepository extends JpaRepository<FeedMessage, Integer> {

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "    feed_message_seq,\n" +
                    "    CASE WHEN trim(categories) = '' THEN NULL ELSE categories END AS categories,\n" +
                    "    left(CAST(created_at AS varchar), 19) AS created_at,\n" +
                    "    LEFT(REPLACE(CAST(created_at AS VARCHAR), '-', ''), 6) AS file_date,\n" +
                    "    CASE WHEN trim(creator) = '' THEN NULL ELSE creator END AS creator,\n" +
                    "    CASE WHEN trim(description) = '' THEN NULL ELSE description END AS description,\n" +
                    "    CASE WHEN trim(keywords) = '' THEN NULL ELSE keywords END AS keywords,\n" +
                    "    CASE WHEN trim(link) = '' THEN NULL ELSE link END AS link,\n" +
                    "    CASE WHEN trim(source) = '' THEN NULL ELSE source END AS \"source\",\n" +
                    "    CASE WHEN trim(title) = '' THEN NULL ELSE title END AS title\n" +
                    "FROM fnguide.feed_message\n" +
                    "WHERE left(replace(CAST(created_at AS varchar), '-' , ''), 6) = :tgDate\n" +
                    "ORDER BY file_date")
    List<Object[]> getOverseaRss(@Param("tgDate") String tgDate);

}
