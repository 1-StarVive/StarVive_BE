package com.starbucks.starvive.tag.presentation;

import com.starbucks.starvive.tag.application.TagService;
import com.starbucks.starvive.tag.vo.RegisterTagVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/tag")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/add")
    public void addTag(
            @RequestPart("request") RegisterTagVo registerTagVo,
            @RequestPart("file") MultipartFile file
    ) {
        tagService.addTag(registerTagVo, file);
    }
}
