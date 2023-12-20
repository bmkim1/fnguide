package fnguide.controller;

import fnguide.dto.oversea.OverseaCorpDto;
import fnguide.dto.oversea.OverseaInfoDto;
import fnguide.dto.oversea.OverseaKeywordDto;
import fnguide.dto.oversea.OverseaRssDto;
import fnguide.service.oversea.OverseaCorpService;
import fnguide.service.oversea.OverseaInfoService;
import fnguide.service.oversea.OverseaKeywordService;
import fnguide.service.oversea.OverseaRssService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    private final OverseaKeywordService overseaKeywordService;

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

    @GetMapping(value = "/overseaKeyword", produces = "text/csv")
    public void overseaKeywordExport (HttpServletResponse response, @RequestParam String filePath, @RequestParam String fileDate) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv; charset=UTF-8");

        String exportFileName = "oversea_discovered_corp_keyword" + fileDate + ".csv";
        response.setHeader("Content-disposition", "attachment;filename=" + exportFileName);

        List<OverseaKeywordDto> keywordDtoList = overseaKeywordService.getOverseaKeywordList(fileDate);

        String serverFilePath = filePath + File.separator + exportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(serverFilePath, StandardCharsets.UTF_8))) {
            writer.write('\ufeff');
            overseaKeywordService.createOverseaKeywordCsv(writer, keywordDtoList);
        } catch (IOException e) {
            throw new RuntimeException("csv 파일 저장 실패 : " + e.getMessage());
        }
    }
}
