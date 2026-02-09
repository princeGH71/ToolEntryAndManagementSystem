package com.prince.ToolEntrySystem.controller;

import com.prince.ToolEntrySystem.dto.ToolPatchDto;
import com.prince.ToolEntrySystem.dto.ToolQueryParamsDto;
import com.prince.ToolEntrySystem.dto.ToolRequestDto;
import com.prince.ToolEntrySystem.dto.ToolResponseDto;
import com.prince.ToolEntrySystem.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        String response = toolService.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
    
    @PatchMapping("updateTool/{id}")
    public ResponseEntity<ToolResponseDto> partialUpdateTool(@PathVariable Long id, @RequestBody ToolPatchDto toolPatchDto){
        ToolResponseDto responseDto = toolService.partialUpdate(id, toolPatchDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ToolResponseDto>> searchTools(@ModelAttribute ToolQueryParamsDto toolQueryParamsDto){
        List<ToolResponseDto> responseDtos = toolService.searchTools(toolQueryParamsDto);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
