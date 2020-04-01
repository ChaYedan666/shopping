package org.project01.service;

import org.project01.domain.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> findAll();
    public Category findById(String cid);
}
