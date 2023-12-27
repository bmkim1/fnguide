package fnguide.service.inner;

import fnguide.dto.inner.InnerCorpDto;
import fnguide.dto.inner.InnerCorpKeywordDto;
import fnguide.repository.Inner.InnerDiscoveredCorpRepository;
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
public class InnerCorpKeywordService {

    private final InnerDiscoveredCorpRepository repository;

    public List<InnerCorpKeywordDto> getInnerKeyword (String fileDate) {
        List<Object[]> resultList = repository.getInnerKeyword(fileDate);
        List<InnerCorpKeywordDto> keywordDtoList = convertToInnerKeyword(resultList);
        return keywordDtoList;
    }

    public List<InnerCorpKeywordDto> convertToInnerKeyword (List<Object[]> resultList) {
        List<InnerCorpKeywordDto> keywordDtoList = new ArrayList<>();
        for (Object[] obj : resultList) {
            InnerCorpKeywordDto dto = new InnerCorpKeywordDto();
            dto.setEnr((String) obj[0]);
            dto.setCompanyName((String) obj[1]);
            dto.setCrn((String) obj[2]);
            dto.setDiscoveredDate((String) obj[3]);
            dto.setKeywordSeq((Long) obj[4]);
            dto.setKeyword((String) obj[5]);
            dto.setCompanyInfoFreq((Long) obj[6]);
            dto.setPatentFreq((Long) obj[7]);
            dto.setUtilityFreq((Long) obj[8]);
            dto.setRndFreq((Long) obj[9]);
            dto.setHomepageFreq((Long) obj[10]);
            dto.setInnerKeywordFreq((Long) obj[11]);
            dto.setKeywordRowCount((Long) obj[12]);
            keywordDtoList.add(dto);
        }
        return keywordDtoList;
    }

    public void createInnerKeywordCsv (Writer writer, List<InnerCorpKeywordDto> keywordDtoList) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("enr", "company_name", "crn", "discoveredDate",
                "keyword_seq", "keyword", "company_info_freq", "patent_freq", "utility_freq", "rnd_freq", "homepage_freq", "inner_keyword_freq", "keyword_row_count");

        for (InnerCorpKeywordDto keywordDto : keywordDtoList) {
            csvPrinter.printRecord(
                    keywordDto.getEnr(),
                    keywordDto.getCompanyName(),
                    keywordDto.getCrn(),
                    keywordDto.getDiscoveredDate(),
                    keywordDto.getKeywordSeq(),
                    keywordDto.getKeyword(),
                    keywordDto.getCompanyInfoFreq(),
                    keywordDto.getPatentFreq(),
                    keywordDto.getUtilityFreq(),
                    keywordDto.getRndFreq(),
                    keywordDto.getHomepageFreq(),
                    keywordDto.getInnerKeywordFreq(),
                    keywordDto.getKeywordRowCount()
            );
        }
    }

}
