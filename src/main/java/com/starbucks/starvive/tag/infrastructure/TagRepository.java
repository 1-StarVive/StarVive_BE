package com.starbucks.starvive.tag.infrastructure;

import com.starbucks.starvive.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
