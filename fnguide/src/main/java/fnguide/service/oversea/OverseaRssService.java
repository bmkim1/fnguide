package fnguide.service.oversea;

import fnguide.dto.oversea.OverseaRssDto;
import fnguide.entity.oversea.FeedMessage;
import fnguide.repository.oversea.FeedMessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverseaRssService {

    private final FeedMessageRepository feedMessageRepository;

    public List<OverseaRssDto> getOverseaRss (String fileDate) {
        List<Object[]> resultList = feedMessageRepository.getOverseaRss(fileDate);
        List<OverseaRssDto> rssDtoList = convertToRssDto(resultList);
        return rssDtoList;
    }

    private List<OverseaRssDto> convertToRssDto(List<Object[]> resultList) {
        List<OverseaRssDto> rssDtoList = new ArrayList<>();
        for (Object[] obj : resultList) {
            OverseaRssDto dto = new OverseaRssDto();
            dto.setFeedMessageSeq((Integer) obj[0]);
            dto.setCategories((String) obj[1]);
            dto.setCreateAt((String) obj[2]);
            dto.setFileDate((String) obj[3]);
            dto.setCreator((String) obj[4]);
            dto.setDescription((String) obj[5]);
            dto.setKeywords((String) obj[6]);
            dto.setLink((String) obj[7]);
            dto.setSource((String) obj[8]);
            dto.setTitle((String) obj[9]);
            rssDtoList.add(dto);
        }
        return rssDtoList;
    }

    public void createRssCsv (Writer writer, List<OverseaRssDto> rssDtoList) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        csvPrinter.printRecord("feed_message_seq", "categories", "created_at",
                "file_date", "creator", "description", "keywords", "link", "source", "title");

        for (OverseaRssDto rssDto : rssDtoList) {
            csvPrinter.printRecord(
                    rssDto.getFeedMessageSeq(),
                    rssDto.getCategories(),
                    rssDto.getCreateAt(),
                    rssDto.getFileDate(),
                    rssDto.getCreator(),
                    rssDto.getDescription(),
                    rssDto.getKeywords(),
                    rssDto.getLink(),
                    rssDto.getSource(),
                    rssDto.getTitle()
            );
        }
    }

}