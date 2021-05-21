package com.sushiblog.backendv2.usecase.service.category;

import com.sushiblog.backendv2.usecase.dto.request.UpdateCategoryNameRequest;
import com.sushiblog.backendv2.usecase.dto.response.CategoriesResponse;

public interface CategoryService {
    void updateName(UpdateCategoryNameRequest request);
    CategoriesResponse getCategories(String email);
}
