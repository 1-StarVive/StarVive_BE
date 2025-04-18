package com.starbucks.starvive.tag.application;

import com.starbucks.starvive.tag.dto.out.ListTagResponseDto;
import com.starbucks.starvive.tag.vo.RegisterTagVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TagService {

    void addTag(RegisterTagVo registerTagVo, MultipartFile image);

    List<ListTagResponseDto> findTagsAll();
}
