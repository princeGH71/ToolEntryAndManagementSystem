package com.prince.ToolEntrySystem.controller;

import com.prince.ToolEntrySystem.dto.ToolRequestDto;
import com.prince.ToolEntrySystem.dto.ToolResponseDto;
import com.prince.ToolEntrySystem.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tool")
@RequiredArgsConstructor
@Slf4j
public class ToolController {
    
    private final ToolService toolService;
    
    @PostMapping("/enterTool")
    public ResponseEntity<ToolResponseDto> enterTool(@RequestBody @Valid ToolRequestDto toolRequestDto){
        log.trace("Tool entry creation process started");
        ToolResponseDto toolResponseDto = toolService.enterTool(toolRequestDto);
        
        return new ResponseEntity<>(toolResponseDto, HttpStatus.CREATED);
    }
}
