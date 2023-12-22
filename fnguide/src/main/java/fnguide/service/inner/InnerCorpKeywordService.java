package fnguide.service.inner;

import fnguide.dto.inner.InnerCorpKeywordDto;
import fnguide.repository.Inner.InnerDiscoveredCorpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            dto.setKeywordSeq((Integer) obj[4]);
            dto.setKeyword((String) obj[5]);
            dto.setCompanyInfoFreq((Integer) obj[6]);
            dto.setPatentFreq((Integer) obj[7]);
            dto.setUtilityFreq((Integer) obj[8]);
            dto.setRndFreq((Integer) obj[9]);
            dto.setHomepageFreq((Integer) obj[10]);
            dto.setInnerFreq((Integer) obj[11]);
            dto.setKeywordRowCount((Integer) obj[12]);
            keywordDtoList.add(dto);
        }
        return keywordDtoList;
    }


}
