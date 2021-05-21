package com.sushiblog.backendv2.usecase.service.category;

import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.CategoryNotFoundException;
import com.sushiblog.backendv2.error.NotAccessibleException;
import com.sushiblog.backendv2.error.UserNotFoundException;
import com.sushiblog.backendv2.security.auth.AuthenticationFacade;
import com.sushiblog.backendv2.usecase.dto.request.UpdateCategoryNameRequest;
import com.sushiblog.backendv2.usecase.dto.response.Categories;
import com.sushiblog.backendv2.usecase.dto.response.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.stat.internal.CategorizedStatistics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void updateName(UpdateCategoryNameRequest request) {
        User user = authenticationFacade.checkAuth();

        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(CategoryNotFoundException::new);

        if(!category.getUser().equals(user)) {
            throw new NotAccessibleException();
        }

        category.updateName(request.getName());
        categoryRepository.save(category);
    }

    @Override
    public CategoriesResponse getCategories(String email) {
        User user = authenticationFacade.checkAuth();

        List<Category> categoryList = user.getCategories();
        List<Categories> categories = new ArrayList<>();

        for(Category category : categoryList) {
            categories.add(
                    Categories.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build()
            );
        }

        return new CategoriesResponse(categories);
    }

}
