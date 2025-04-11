package com.starbucks.starvive.banner.infrastructure;

import com.starbucks.starvive.banner.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BannerImageRepository extends JpaRepository<Banner, UUID> {


}
