package com.bookstore.service;

import com.bookstore.repository.UserBookHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final UserBookHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public List<Object[]> viewsByCategoryForAuthor(Long authorId) {
        return historyRepository.countViewsByCategoryForAuthor(authorId);
    }

    @Transactional(readOnly = true)
    public List<Object[]> likesByCategoryForAuthor(Long authorId) {
        return historyRepository.countLikesByCategoryForAuthor(authorId);
    }

    @Transactional(readOnly = true)
    public List<Object[]> topBooksByViews() {
        return historyRepository.countViewsByBook();
    }
}
