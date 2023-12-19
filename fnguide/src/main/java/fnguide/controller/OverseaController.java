package fnguide.controller;

import fnguide.dto.OverseaCorpDto;
import fnguide.dto.OverseaInfoDto;
import fnguide.dto.OverseaRssDto;
import fnguide.service.OverseaCorpService;
import fnguide.service.OverseaInfoService;
import fnguide.service.OverseaRssService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/fnguide")
@RequiredArgsConstructor
public class OverseaController {

    private final OverseaRssService overseaRssService;
    private final OverseaInfoService overseaInfoService;
    private final OverseaCorpService overseaCorpService;

    @GetMapping(value = "/overseaRss", produces = "text/csv")
    public void rssExport(HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "oversea_feed_" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<OverseaRssDto> rssDtoList = overseaRssService.createRssDto(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            overseaRssService.createRssCsv(writer, rssDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
    }

    @GetMapping(value = "/overseaInfo", produces = "text/csv")
    public void overseaCorpExport (HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "oversea_scraped_corp_info_" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<OverseaInfoDto> infoDtoList = overseaInfoService.getOverseaInfo(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            overseaInfoService.createOverseaInfoCsv(writer, infoDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
    }

    @GetMapping(value = "/overseaCorp", produces = "text/csv")
    public void overseaInfoExport (HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "oversea_discovered_corp" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<OverseaCorpDto> corpDtoList = overseaCorpService.getOverseaCorp(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            overseaCorpService.createOverseaCorpCsv(writer, corpDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
    }

    @GetMapping(value = "/overseaCorp")
    public ResponseEntity<List<OverseaCorpDto>> testInfo (@RequestParam String fileDate) {
        List<OverseaCorpDto> infos = overseaCorpService.getOverseaCorp(fileDate);
        return ResponseEntity.ok(infos);
    }


    /*@GetMapping(value = "/overseaCorp", produces = "text/csv")
    public ResponseEntity<String> overseaCorp(@RequestParam String filePath,
                                            @RequestParam String fileDate) {
        try {
            String exportFileName = "oversea_discovered_corp_" + fileDate + ".csv";
            File csvFile = new File(filePath, exportFileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                fnguideService.createOverseaDiscoveredCorp(writer, fileDate);
            }

            return ResponseEntity.ok("CSV file 생성 완료: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("CSV file 생성 실패: " + e.getMessage());
        }
    }*/
}
