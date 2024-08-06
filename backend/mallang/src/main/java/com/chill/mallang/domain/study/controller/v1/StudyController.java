package com.chill.mallang.domain.study.controller.v1;

import com.chill.mallang.domain.study.service.AllWrongWordService;
import com.chill.mallang.domain.study.service.OneWrongWordService;
import com.chill.mallang.domain.study.service.StudiedWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/study")
@Tag(name = "Study API",description = "학습 API")
public class StudyController {

    @Autowired
    StudiedWordService studiedWordService;

    @Autowired
    AllWrongWordService allWrongWordService;

    @Autowired
    OneWrongWordService oneWrongWordService;

    @Operation(summary = "풀었던 단어 목록 조회", description = "사용자가 학습했던 단어를 조회합니다.")
    @GetMapping("/studied-word/{userId}")
    public ResponseEntity<?> getStudiedWordInfo(@PathVariable Long userId) {
        Map<String,Object> StudiedWords = studiedWordService.getStudiedWord(userId);
        return new ResponseEntity<>(StudiedWords, HttpStatus.OK);
    }

    @Operation(summary = "오답 단어 조회", description = "오답노트 / 사용자가 틀렸던 단어를 조회합니다.")
    @GetMapping("/wrong-word/{userId}")
    public ResponseEntity<?> getAllWrongWordInfo(@PathVariable Long userId) {
        Map<String,Object> wrongWords = allWrongWordService.getWrongWord(userId);
        return new ResponseEntity<>(wrongWords, HttpStatus.OK);
    }

    @Operation(summary = "오답 문제 하나 조회", description = "특정 사용자가 틀린 특정 문제를 조회합니다.")
    @GetMapping("/wrong-word/{userId}/{studyId}")
    public ResponseEntity<?> getOneWrongWordInfo(@PathVariable Long userId, @PathVariable Long studyId) {
        Map<String,Object> oneWrongWord = oneWrongWordService.getOneWrongWord(userId, studyId);
        return new ResponseEntity<>(oneWrongWord,HttpStatus.OK);
    }

}
