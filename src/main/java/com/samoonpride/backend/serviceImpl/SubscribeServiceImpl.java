package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.model.Issue;
import com.samoonpride.backend.model.LineUser;
import com.samoonpride.backend.model.Subscribe;
import com.samoonpride.backend.repository.IssueRepository;
import com.samoonpride.backend.repository.LineUserRepository;
import com.samoonpride.backend.repository.SubscribeRepository;
import com.samoonpride.backend.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService{
    private final SubscribeRepository subscribeRepository;
    private final IssueRepository issueRepository;
    private final LineUserRepository lineUserRepository;

    @Override
    @Transactional
    public void subscribe(String lineUserId, int issueId) {
        if (!subscribeRepository.existsByLineUser_UserIdAndIssueId(lineUserId, issueId)) {
            // Find user and issue
            LineUser lineUser = lineUserRepository.findByUserId(lineUserId);
            Issue issue = issueRepository.findById(issueId);

            // Create new subscribe
            Subscribe subscribe = new Subscribe();
            subscribe.setLineUser(lineUser);
            subscribe.setIssue(issue);

            // Add subscribe to user and issue
            lineUser.getSubscribes().add(subscribe);
            issue.getSubscribes().add(subscribe);

            // Save to database
            issueRepository.save(issue);
            lineUserRepository.save(lineUser);
            subscribeRepository.save(subscribe);
        }
    }

    @Override
    @Transactional
    public void unsubscribe(String lineUserId, int issueId) {
        if (subscribeRepository.existsByLineUser_UserIdAndIssueId(lineUserId, issueId)) {
            subscribeRepository.deleteByLineUser_UserIdAndIssueId(lineUserId, issueId);
        }
    }

    @Override
    public void notifySubscribers(int issueId) {

    }
}
