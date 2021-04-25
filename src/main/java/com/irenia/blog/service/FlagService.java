package com.irenia.blog.service;

import com.irenia.blog.prototype.FlagType;

import java.util.List;
import java.util.Optional;

public interface FlagService {
    Optional<FlagType> getFlag(Long id);
    List<FlagType> listFlag();
}
