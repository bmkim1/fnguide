package fnguide.service.oversea;


import fnguide.dto.oversea.OverseaKeywordDto;
import fnguide.repository.oversea.OverseaDiscoveredCorpRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverseaKeywordService {

    private final OverseaDiscoveredCorpRepository repository;

    public List<OverseaKeywordDto> getOverseaKeywordList (String fileDate) {
        List<Object[]> resultList = repository.getOverseaKeyword(fileDate);
        List<OverseaKeywordDto> overseaKeywordList = convertOverseaKeywordData(resultList);
        return overseaKeywordList;
    }

    private List<OverseaKeywordDto> convertOverseaKeywordData (List<Object[]> resultList) {
        List<OverseaKeywordDto> keywordDtoList = new ArrayList<>();
        for (Object[] result : resultList) {
            OverseaKeywordDto dto = new OverseaKeywordDto();
            dto.setOverseaSeq((String) result[0]);
            dto.setDmn((String) result[1]);
            dto.setDiscoveredDate((String) result[2]);
            dto.setKeywordSeq((Long) result[3]);
            dto.setKeywordEng((String) result[4]);
            dto.setKeywordKor((String) result[5]);
            dto.setKeywordRowCount((Long) result[6]);

            keywordDtoList.add(dto);
        }
        return keywordDtoList;
    }

    public void createOverseaKeywordCsv (Writer writer, List<OverseaKeywordDto> keywordDtoList) throws IOException {

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("oversea_seq", "dmn", "discovered_date",
                "keyword_seq", "keyword_eng", "keyword_kor", "keyword_row_count");

        for (OverseaKeywordDto keywordDto : keywordDtoList) {
            csvPrinter.printRecord(
                    keywordDto.getOverseaSeq(),
                    keywordDto.getDmn(),
                    keywordDto.getDiscoveredDate(),
                    keywordDto.getKeywordSeq(),
                    keywordDto.getKeywordEng(),
                    keywordDto.getKeywordKor(),
                    keywordDto.getKeywordRowCount()
            );
        }
    }
}
