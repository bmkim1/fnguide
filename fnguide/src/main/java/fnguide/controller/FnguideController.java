package fnguide.controller;

import fnguide.entity.InvestedCorp;
import fnguide.service.FnguideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fnguide")
@RequiredArgsConstructor
public class FnguideController {

    private final FnguideService fnguideService;

    @PostMapping("/csv")
    public ResponseEntity<List<InvestedCorp>> fnguideResult () {
        List<InvestedCorp> result = fnguideService.fnguideData();
        return ResponseEntity.ok(result);
    }

    /*@GetMapping("/generate")
    public String generateCsv(@RequestParam String filePath) {
        try {
            fnguideService.generateCsv(filePath);
            return "CSV generated successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating CSV";
        }
    }*/
}
