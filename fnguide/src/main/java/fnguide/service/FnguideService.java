package fnguide.service;

import fnguide.entity.InvestedCorp;
import fnguide.repository.InvestedCorpRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FnguideService {

    private final InvestedCorpRepository investedCorpRepository;

    public List<InvestedCorp> fnguideData () {

        List<InvestedCorp> investedCorp = investedCorpRepository.findAll();
        return investedCorp;
    }
    public void writeCsv (Writer writer) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("oversea_seq", "dmn", "company_name", "categories", "company_infos",
                                "file_date", "homepage_url");

        List<InvestedCorp> investedCorpList = fnguideData(); // Assuming this method retrieves data from the repository

        try {
            for (InvestedCorp investedCorp : investedCorpList) {
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
        } finally {
            csvPrinter.close();
        }
    }

}
