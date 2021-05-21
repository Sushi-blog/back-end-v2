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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void updateName(UpdateCategoryNameRequest request) {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
            .orElseThrow(UserNotFoundException::new);

        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(CategoryNotFoundException::new);

        if(!category.getUser().equals(user)) {
            throw new NotAccessibleException();
        }

        category.updateName(request.getName());
        categoryRepository.save(category);
    }

}
