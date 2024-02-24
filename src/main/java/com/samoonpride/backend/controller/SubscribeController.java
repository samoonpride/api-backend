package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.request.SubscribeRequest;
import com.samoonpride.backend.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("api/subscribe")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("/line-user/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@RequestBody SubscribeRequest subscribeRequest) {
        log.info("subscribeRequest: {}", subscribeRequest);
        subscribeService.subscribe(subscribeRequest.getLineUserId(), subscribeRequest.getIssueId());
    }

    @DeleteMapping("/line-user/unsubscribe")
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(@RequestBody SubscribeRequest subscribeRequest) {
        log.info("subscribeRequest: {}", subscribeRequest);
        subscribeService.unsubscribe(subscribeRequest.getLineUserId(), subscribeRequest.getIssueId());
    }
}
