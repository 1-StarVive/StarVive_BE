package com.starbucks.starvive.tag.presentation;

import com.starbucks.starvive.tag.application.TagService;
import com.starbucks.starvive.tag.dto.out.ListTagResponseDto;
import com.starbucks.starvive.tag.vo.RegisterTagVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/tag")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(summary = "태그 등록", description = "태그를 등록합니다.",
            tags = {"tag-service"})
    @PostMapping("/add")
    public void addTag(
            @RequestPart("request") RegisterTagVo registerTagVo,
            @RequestPart("file") MultipartFile file
    ) {
        tagService.addTag(registerTagVo, file);
    }

    @Operation(summary = "태그 등록", description = "태그를 등록합니다.",
            tags = {"tag-service"})
    @GetMapping("/all")
    public List<ListTagResponseDto> getTags() {
        return tagService.findTagsAll();
    }
}
