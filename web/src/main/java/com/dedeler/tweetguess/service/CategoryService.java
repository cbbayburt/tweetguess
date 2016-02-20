package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.repository.CategoryRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return Lists.newArrayList(categoryRepository.findAll());
    }

}
