package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.service.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("api/v1/user")
@Tag(name = "Join API", description = "회원가입 API")
public class JoinController {
    private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }
    @PostMapping("/join")
    public void joinProcess(@Valid @RequestBody JoinRequestDTO joinRequestDTO, HttpServletResponse response) throws IOException {
        JoinResponseDTO joinResponseDTO = joinService.joinProcess(joinRequestDTO);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("token", joinResponseDTO.getJwtToken());
        Map<String, Map<String, String>> responseMap = new HashMap<>();
        responseMap.put("data", dataMap);

        // Convert the response map to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseMap);

        // Write JSON to the HttpServletResponse
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

}