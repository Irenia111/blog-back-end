package com.irenia.blog.service;

import com.irenia.blog.dao.FlagRepository;
import com.irenia.blog.prototype.FlagType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FlagServiceImpl implements FlagService{
    @Autowired
    private FlagRepository flagRepository;

    @Override
    public Optional<FlagType> getFlag(Long id) {
        return flagRepository.findById(id);
    }

    @Transactional
    @Override
    public List<FlagType> listFlag() {
        return flagRepository.findAll();
    }
}
