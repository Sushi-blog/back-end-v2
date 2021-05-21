package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.dto.request.UpdateCategoryNameRequest;
import com.sushiblog.backendv2.usecase.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sushi/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateName(@RequestBody UpdateCategoryNameRequest request) {
        categoryService.updateName(request);
    }

}
