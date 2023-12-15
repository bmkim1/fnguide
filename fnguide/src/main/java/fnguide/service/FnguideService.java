package fnguide.service;

import fnguide.dto.OverseaCorpDto;
import fnguide.entity.FeedMessage;
import fnguide.entity.InvestedCorp;
import fnguide.entity.OverseaDiscoveredCorp;
import fnguide.repository.FeedMessageRepository;
import fnguide.repository.InvestedCorpRepository;
import fnguide.repository.OverseaDiscoveredCorpRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FnguideService {

    private final InvestedCorpRepository investedCorpRepository;
    private final FeedMessageRepository feedMessageRepository;
    private final OverseaDiscoveredCorpRepository overseaDiscoveredCorpRepository;

    public List<InvestedCorp> investedCorps () {
        List<InvestedCorp> investedCorp = investedCorpRepository.findAll();
        return investedCorp;
    }
    public void writeCsv (Writer writer, String fileDate) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("oversea_seq", "dmn", "company_name", "categories", "company_infos",
                                "file_date", "homepage_url");

        List<InvestedCorp> investedCorpList = investedCorps();

        try {
            for (InvestedCorp investedCorp : investedCorpList) {
                if (investedCorp.getFileDate().equals(fileDate)) {
                    csvPrinter.printRecord(
                            investedCorp.getOverseaSeq(),
                            investedCorp.getDmn(),
                            investedCorp.getCompanyName(),
                            investedCorp.getCategories(),
                            investedCorp.getCompanyInfos(),
                            investedCorp.getFileDate(),
                            investedCorp.getHomepageUrl()
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void createRssCsv (Writer writer, String fileDate) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("feed_message_seq", "categories", "created_at",
                "file_date", "creator", "description", "keywords", "link", "source", "title");

        List<FeedMessage> feedMessages = feedMessageRepository.findAll();

        try {
            for (FeedMessage feedMessage : feedMessages) {
                if (feedMessage.getFileDate().equals(fileDate)) {
                    csvPrinter.printRecord(
                            feedMessage.getFeedMessageSeq(),
                            feedMessage.getCategories(),
                            feedMessage.getCreateAt(),
                            feedMessage.getFileDate(),
                            feedMessage.getCreator(),
                            feedMessage.getDescription(),
                            feedMessage.getKeywords(),
                            feedMessage.getLink(),
                            feedMessage.getSource(),
                            feedMessage.getTitle()
                    );

                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void createOverseaDiscoveredCorp (Writer writer, String fileDate) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("oversea_seq", "dmn", "discovered_date",
                "tech_code_1", "tech_code_2", "bsn_code_1", "bsn_code_2", "vc_cnt");

        List<OverseaDiscoveredCorp> overseaDiscoveredCorps = overseaDiscoveredCorpRepository.findAll();

        String tgDate = "";
        List<OverseaCorpDto> getOverseaResult = overseaDiscoveredCorpRepository.getCustomResults(tgDate);

        try {
            for (OverseaDiscoveredCorp overseaDiscoveredCorp : overseaDiscoveredCorps) {
                if (overseaDiscoveredCorp.getDiscoveredDate().equals(fileDate)) {
                    csvPrinter.printRecord(
                            overseaDiscoveredCorp.getOverseaSeq(),
                            overseaDiscoveredCorp.getDmn(),
                            overseaDiscoveredCorp.getDiscoveredDate(),
                            overseaDiscoveredCorp.getTechCode1(),
                            overseaDiscoveredCorp.getTechCode2(),
                            overseaDiscoveredCorp.getBsnCode1(),
                            overseaDiscoveredCorp.getBsnCode2()
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
