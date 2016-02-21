package com.dedeler.tweetguess.service;

import com.dedeler.tweetguess.model.User;
import com.dedeler.tweetguess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Aykut on 21.02.2016.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

}
