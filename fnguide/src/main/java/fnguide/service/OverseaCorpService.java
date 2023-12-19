package fnguide.service;

import fnguide.dto.OverseaCorpDto;
import fnguide.repository.OverseaDiscoveredCorpRepository;
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
public class OverseaCorpService {

    private final OverseaDiscoveredCorpRepository overseaDiscoveredCorpRepository;

    public List<OverseaCorpDto> getOverseaCorp (String fileDate) {

        List<Object[]> resultList = overseaDiscoveredCorpRepository.findAllByOrderByOverseaSeqAsc(fileDate);
        List<OverseaCorpDto> corpDtoList = convertOverseaCorpDate(resultList);
        return corpDtoList;

    }

    private List<OverseaCorpDto> convertOverseaCorpDate (List<Object[]> resultList) {
        List<OverseaCorpDto> corpDtoList = new ArrayList<>();
        for (Object[] result : resultList) {
            OverseaCorpDto dto = new OverseaCorpDto();
            dto.setOverseaSeq((String) result[0]);
            dto.setDmn((String) result[1]);
            dto.setDiscoveredDate((String) result[2]);
            dto.setTechCode1((String) result[3]);
            dto.setTechCode2((String) result[4]);
            dto.setBsnCode1((String) result[5]);
            dto.setBsnCode2((String) result[6]);
            dto.setVcCnt((Long) result[7]);

            corpDtoList.add(dto);
        }
        return corpDtoList;
    }

    public void createOverseaCorpCsv (Writer writer, List<OverseaCorpDto> corpDtoList) throws IOException {

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("oversea_seq", "dmn", "discovered_date",
                "tech_code_1", "tech_code_2", "bsn_code_1", "bsn_code_2", "vc_cnt");

        for (OverseaCorpDto corpDto : corpDtoList) {
            csvPrinter.printRecord(
                    corpDto.getOverseaSeq(),
                    corpDto.getDmn(),
                    corpDto.getDiscoveredDate(),
                    corpDto.getTechCode1(),
                    corpDto.getTechCode2(),
                    corpDto.getBsnCode1(),
                    corpDto.getBsnCode2(),
                    corpDto.getVcCnt()
            );
        }
    }

}
