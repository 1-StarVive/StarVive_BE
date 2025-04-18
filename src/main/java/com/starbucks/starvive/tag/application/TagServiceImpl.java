package com.starbucks.starvive.tag.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.common.s3.S3Uploader;
import com.starbucks.starvive.tag.domain.Tag;
import com.starbucks.starvive.tag.dto.in.RegisterTagRequestDto;
import com.starbucks.starvive.tag.infrastructure.TagRepository;
import com.starbucks.starvive.tag.vo.RegisterTagVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.DUPLICATED_TAG;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final S3Uploader s3Uploader;

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public void addTag(RegisterTagVo registerTagVo, MultipartFile image) {
        if(tagRepository.findByName(registerTagVo.getName()).isPresent()) {
            throw new BaseException(DUPLICATED_TAG);
        }

        String imageUrl = s3Uploader.upload(image, "tags");

        RegisterTagRequestDto registerTagRequestDto = RegisterTagRequestDto.from(registerTagVo, imageUrl);

        tagRepository.save(registerTagRequestDto.toTag());
    }
}
