package com.irenia.blog.dao;

import com.irenia.blog.prototype.FlagType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagRepository extends JpaRepository<FlagType, Long> {
}
