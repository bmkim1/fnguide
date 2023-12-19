package fnguide.service;

import fnguide.dto.OverseaInfoDto;
import fnguide.entity.OverseaDiscoveredCorp;
import fnguide.entity.Portfolio;
import fnguide.repository.InvestedCorpRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OverseaInfoService {

    private final InvestedCorpRepository investedCorpRepository;

    public List<OverseaInfoDto> getOverseaInfo(String fileDate) {
        List<Object[]> resultList = investedCorpRepository.findAllByOrderByOverseaSeqAsc(fileDate);
        List<OverseaInfoDto> dtoList = convertOverseaInfoData(resultList);

        return dtoList;
    }

    private List<OverseaInfoDto> convertOverseaInfoData(List<Object[]> resultList) {
        List<OverseaInfoDto> infoDtoList = new ArrayList<>();
        for (Object[] result : resultList) {
            OverseaInfoDto dto = new OverseaInfoDto();
            dto.setOverseaSeq((String) result[0]);
            dto.setDmn((String) result[1]);
            dto.setCompanyName((String) result[2]);
            dto.setCategories((String) result[3]);
            dto.setCompanyInfos((String) result[4]);
            dto.setFileDate((String) result[5]);
            dto.setHomepageUrl((String) result[6]);
            dto.setImagePath((String) result[7]);
            dto.setStatusCode((Integer) result[8]);
            dto.setLive((String) result[9]);
            dto.setDescription((String) result[10]);
            dto.setTitle((String) result[11]);
            dto.setKeyword((String) result[12]);
            dto.setVcCnt((Long) result[13]);

            infoDtoList.add(dto);
        }
        return infoDtoList;
    }

    public void createOverseaInfoCsv (Writer writer, List<OverseaInfoDto> infoDtoList) throws IOException {

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("oversea_seq", "dmn", "company_name", "categories",
                "company_infos", "file_date", "homepage_url", "image_path", "status_code",
                "live", "description", "title", "keyword", "vcCnt");

        for (OverseaInfoDto infoDto : infoDtoList) {
            csvPrinter.printRecord(
                    infoDto.getOverseaSeq(),
                    infoDto.getDmn(),
                    infoDto.getCompanyName(),
                    infoDto.getCategories(),
                    infoDto.getCompanyInfos(),
                    infoDto.getFileDate(),
                    infoDto.getHomepageUrl(),
                    infoDto.getImagePath(),
                    infoDto.getStatusCode(),
                    infoDto.getLive(),
                    infoDto.getDescription(),
                    infoDto.getTitle(),
                    infoDto.getKeyword(),
                    infoDto.getVcCnt()
            );
        }
    }
}
