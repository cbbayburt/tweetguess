package com.dedeler.tweetguess.controller;

import com.dedeler.tweetguess.model.Category;
import com.dedeler.tweetguess.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Aykut on 20.02.2016.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

}
