package fnguide.service;

import fnguide.dto.OverseaRssDto;
import fnguide.entity.FeedMessage;
import fnguide.repository.FeedMessageRepository;
import fnguide.repository.InvestedCorpRepository;
import fnguide.repository.OverseaDiscoveredCorpRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FnguideService {

    private final InvestedCorpRepository investedCorpRepository;
    private final FeedMessageRepository feedMessageRepository;
    private final OverseaDiscoveredCorpRepository overseaDiscoveredCorpRepository;

    public List<OverseaRssDto> createRssDto (String fileDate) {
        List<OverseaRssDto> rssDtos = new ArrayList<>();

        int pageNumber = 0;
        while (true) {
            Pageable pageable = PageRequest.of(pageNumber,1000);
            Page<FeedMessage> feedMessages = feedMessageRepository.findAllByOrderByFeedMessageSeqAsc(pageable);

            for (FeedMessage feedMessage : feedMessages) {
                if (feedMessage.getFileDate().equals(fileDate)) {
                    OverseaRssDto rssDto = convertToDto(feedMessage);
                    rssDtos.add(rssDto);
                }
            }

            if (!feedMessages.hasNext()) {
                break;
            }

            pageNumber++;
        }

        return rssDtos;
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

    private OverseaRssDto convertToDto(FeedMessage feedMessage) {
        OverseaRssDto rssDto = new OverseaRssDto();
        rssDto.setFeedMessageSeq(feedMessage.getFeedMessageSeq());
        rssDto.setCategories(feedMessage.getCategories());
        rssDto.setCreateAt(feedMessage.getCreateAt());
        rssDto.setFileDate(feedMessage.getFileDate());
        rssDto.setCreator(feedMessage.getCreator());
        rssDto.setDescription(feedMessage.getDescription());
        rssDto.setKeywords(feedMessage.getKeywords());
        rssDto.setLink(feedMessage.getLink());
        rssDto.setSource(feedMessage.getSource());
        rssDto.setTitle(feedMessage.getTitle());
        return rssDto;
    }



}