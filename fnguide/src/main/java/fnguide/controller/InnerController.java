package fnguide.controller;

import fnguide.dto.inner.InnerCorpDto;
import fnguide.dto.inner.InnerCorpKeywordDto;
import fnguide.dto.inner.InnerCorpNormalKeywordDto;
import fnguide.service.inner.InnerCorpKeywordService;
import fnguide.service.inner.InnerCorpService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/fnguide")
@RequiredArgsConstructor
public class InnerController {

    private final InnerCorpService innerCorpService;
    private final InnerCorpKeywordService innerCorpKeywordService;
    private final InnerCorpNormalKeywordDto innerCorpNormalKeywordDto;

    @GetMapping("/test")
    public ResponseEntity<List<InnerCorpKeywordDto>> test (@RequestParam String fileDate) {
        List<InnerCorpKeywordDto> innerCorpDtoList = innerCorpKeywordService.getInnerKeyword(fileDate);
        return ResponseEntity.ok(innerCorpDtoList);
    }

    @GetMapping("/innerCorp")
    public void innerCorpExport(HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "discovered_corp_" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<InnerCorpDto> innerCorpDtoList = innerCorpService.getInnerCorp(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            innerCorpService.createInnerCorpCsv(writer, innerCorpDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/innerKeyword")
    public void innerKeywordExport(HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "discovered_corp_keyword_" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<InnerCorpKeywordDto> keywordDtoList = innerCorpKeywordService.getInnerKeyword(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            innerCorpKeywordService.createInnerKeywordCsv(writer, keywordDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
    }
}
