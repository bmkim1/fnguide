package fnguide.controller;

import fnguide.entity.InvestedCorp;
import fnguide.service.FnguideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fnguide")
@RequiredArgsConstructor
public class FnguideController {

    private final FnguideService fnguideService;

    @PostMapping("/csv")
    public ResponseEntity<List<InvestedCorp>> fnguideResult () {
        List<InvestedCorp> result = fnguideService.investedCorps();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/csvfile", produces = "text/csv")
    public ResponseEntity<String> export(@RequestParam String filePath,
                                         @RequestParam String fileDate) {
        try {
            String exportFileName = "fnguide-" + LocalDate.now().toString() + ".csv";
            File csvFile = new File(filePath, exportFileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                fnguideService.writeCsv(writer, fileDate);
            }

            return ResponseEntity.ok("CSV file 생성 완료: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("CSV file 생성 실패: " + e.getMessage());
        }
    }

    @GetMapping(value = "/rss", produces = "text/csv")
    public ResponseEntity<String> rssExport(@RequestParam String filePath,
                                         @RequestParam String fileDate) {
        try {
            String exportFileName = "oversea_feed_" + fileDate + ".csv";
            File csvFile = new File(filePath, exportFileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                fnguideService.createRssCsv(writer, fileDate);
            }

            return ResponseEntity.ok("CSV file 생성 완료: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("CSV file 생성 실패: " + e.getMessage());
        }
    }

    @GetMapping(value = "/overseaCorp", produces = "text/csv")
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
    }
}
