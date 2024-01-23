package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.request.CreateLineUserRequest;
import com.samoonpride.backend.model.LineUser;
import com.samoonpride.backend.service.LineUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/line-user")
public class LineUserController {
    private final LineUserService lineUserService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLineUser(@RequestBody CreateLineUserRequest createLineUserRequest) {
        System.out.printf("createLineUserRequest: %s\n", createLineUserRequest.toString());
        lineUserService.createLineUser(createLineUserRequest);
    }
}
