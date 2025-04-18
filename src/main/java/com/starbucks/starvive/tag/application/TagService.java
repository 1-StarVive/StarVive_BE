package com.starbucks.starvive.tag.application;

import com.starbucks.starvive.tag.vo.RegisterTagVo;
import org.springframework.web.multipart.MultipartFile;

public interface TagService {

    void addTag(RegisterTagVo registerTagVo, MultipartFile image);
}
