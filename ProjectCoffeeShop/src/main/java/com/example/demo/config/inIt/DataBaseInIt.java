package com.example.demo.config.inIt;

import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseInIt implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public DataBaseInIt(UserService userService, CategoryService categoryService, ProductService productService) {

        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.initializeUsers();
        categoryService.initCategories();
        productService.initProducts();
    }
}
