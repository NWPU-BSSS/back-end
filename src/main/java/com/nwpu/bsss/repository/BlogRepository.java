package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity,Long> {

    BlogEntity findBlogById(long id);

}
