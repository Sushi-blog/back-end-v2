package com.sushiblog.backendv2.usecase.service.category;

import com.sushiblog.backendv2.usecase.dto.request.UpdateCategoryNameRequest;

public interface CategoryService {
    void updateName(UpdateCategoryNameRequest request);
}
