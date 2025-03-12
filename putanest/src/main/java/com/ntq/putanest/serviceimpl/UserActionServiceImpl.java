package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.dto.UserActionDTO;
import com.ntq.putanest.pojo.UserAction;
import com.ntq.putanest.repository.UserActionRepository;
import com.ntq.putanest.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    private UserActionRepository userActionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveUserAction(UserActionDTO userActionDTO) {
        UserAction userAction = new UserAction();
        userAction.setUserId(userActionDTO.getUserId());
        userAction.setActionType(userActionDTO.getActionType());
        userAction.setProductId(userActionDTO.getProductId());
        userAction.setQuantity(userActionDTO.getQuantity());

        if (userActionDTO.getTimestamp() != null) {
            userAction.setTimestamp(userActionDTO.getTimestamp());
        } else {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        userActionRepository.save(userAction);
    }

    @Override
    public List<UserAction> findByUserId(int userId) {
        return userActionRepository.findByUserId(userId);
    }
}
