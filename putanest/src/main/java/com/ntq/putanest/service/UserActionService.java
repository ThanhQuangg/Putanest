package com.ntq.putanest.service;

import com.ntq.putanest.dto.UserActionDTO;
import com.ntq.putanest.pojo.UserAction;

import java.util.List;


public interface UserActionService {
    void saveUserAction(UserActionDTO userActionDTO);

    List<UserAction> findByUserId(int userId);
}

