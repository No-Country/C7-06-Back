package com.C706Back.repository;

import com.C706Back.models.entity.Comment;
import com.C706Back.models.entity.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Page<Favourite> findByUserId(Long userId, Pageable pageable);
}
