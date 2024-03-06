package com.samoonpride.backend.service;

public interface SubscribeService {
    void subscribe(String lineUserId, int issueId);
    void unsubscribe(String lineUserId, int issueId);
}
