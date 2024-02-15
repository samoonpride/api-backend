package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.request.CreateLineUserRequest;
import com.samoonpride.backend.model.LineUser;

public interface LineUserService {
    void createLineUser(CreateLineUserRequest createLineUserRequest);

    LineUser findByUserId(String lineUserId);
}
