package fnguide.service.inner;

import fnguide.dto.inner.InnerCorpDto;
import fnguide.dto.oversea.OverseaInfoDto;
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
public class InnerCorpService {

    private final InnerDiscoveredCorpRepository repository;

    public List<InnerCorpDto> getInnerCorp (String fileDate) {
        List<Object[]> resultList = repository.getInnerCorp(fileDate);
        List<InnerCorpDto> innerCorpDtoList = convertToInnerCorp(resultList);
        return innerCorpDtoList;
    }

    private List<InnerCorpDto> convertToInnerCorp (List<Object[]> resultList) {
        List<InnerCorpDto> innerCorpDtoList = new ArrayList<>();
        for (Object[] result : resultList) {
            InnerCorpDto dto = new InnerCorpDto();
            dto.setEnr((String) result[0]);
            dto.setCompanyName((String) result[1]);
            dto.setCrn((String) result[2]);
            dto.setDiscoveredDate((String) result[3]);
            dto.setTech1((String) result[4]);
            dto.setTechCode1((String) result[5]);
            dto.setTech2((String) result[6]);
            dto.setTechCode2((String) result[7]);

            innerCorpDtoList.add(dto);
        }
        return innerCorpDtoList;
    }

    public void createInnerCorpCsv (Writer writer, List<InnerCorpDto> innerCorpDtoList) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("enr", "company_name", "crn",
                "discoveredDate", "tech1", "techCode1", "tech2", "techCode2");

        for (InnerCorpDto corpDto : innerCorpDtoList) {
            csvPrinter.printRecord(
                    corpDto.getEnr(),
                    corpDto.getCompanyName(),
                    corpDto.getCrn(),
                    corpDto.getDiscoveredDate(),
                    corpDto.getTech1(),
                    corpDto.getTechCode1(),
                    corpDto.getTech2(),
                    corpDto.getTechCode2()
            );
        }
    }


}
