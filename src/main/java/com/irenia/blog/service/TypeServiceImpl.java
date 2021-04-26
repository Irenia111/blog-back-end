package com.irenia.blog.service;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.dao.TypeRepository;
import com.irenia.blog.prototype.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Modifying
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Optional<Type> getType(Long id) {
        return typeRepository.findById(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public List<Type> listTopType(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Modifying
    @Override
    public Type updateType(Long id, Type type) {
        Type oldType = typeRepository.findById(id).orElseThrow(() -> new NotFoundException("type not found"));
        BeanUtils.copyProperties(type, oldType);
        return typeRepository.save(type);
    }

    @Transactional
    @Modifying
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
