package fnguide.controller;

import fnguide.dto.OverseaRssDto;
import fnguide.entity.InvestedCorp;
import fnguide.service.FnguideService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fnguide")
@RequiredArgsConstructor
public class FnguideController {

    private final FnguideService fnguideService;

    @GetMapping(value = "/overseaRss", produces = "text/csv")
    public void rssExport(HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "oversea_feed_" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<OverseaRssDto> rssDtoList = fnguideService.createRssDto(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            fnguideService.createRssCsv(writer, rssDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
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
