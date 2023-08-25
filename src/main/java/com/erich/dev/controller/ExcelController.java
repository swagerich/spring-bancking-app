package com.erich.dev.controller;

import com.erich.dev.service.impl.ExcelServiceImpl;
import static com.erich.dev.util.printer.ExcelMethods.formatFileName;

import static com.erich.dev.util.BankPath.Path;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(Path + "/excel")
public class ExcelController {

    private final ExcelServiceImpl excelService;

    public ExcelController(ExcelServiceImpl excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam Long userId){
        byte[] excel = excelService.saveDataExcel(userId);
        ByteArrayResource byteArrayResource = new ByteArrayResource(excel);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + formatFileName() + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
}
