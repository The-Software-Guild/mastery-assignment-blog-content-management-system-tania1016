package com.wiley.BlogManagementSystem.dao;

import com.wiley.BlogManagementSystem.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
