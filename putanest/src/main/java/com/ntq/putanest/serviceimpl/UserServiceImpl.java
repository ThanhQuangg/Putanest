//package com.ntq.putanest.serviceimpl;
//
//import com.ntq.putanest.dto.UserActionDTO;
//import com.ntq.putanest.pojo.UserAction;
//import com.ntq.putanest.repository.UserActionRepository;
//import com.ntq.putanest.service.UserActionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class UserActionServiceImpl implements UserActionService {
//
//    @Autowired
//    private UserActionRepository userActionRepository;
//
//    @Override
////    public void saveUserAction(UserActionDTO userActionDTO) {
////        UserAction userAction = new UserAction();
////        userAction.setActionType(userActionDTO.getActionType());
////        userAction.setProductId(userActionDTO.getProductId());
////        userAction.setQuantity(userActionDTO.getQuantity());
////
////        // Kiểm tra nếu timestamp từ DTO là String
////        if (userActionDTO.getTimestamp() != null) {
////            try {
////                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////                LocalDateTime timestamp = LocalDateTime.parse(userActionDTO.getTimestamp(), formatter);
////                userAction.setTimestamp(timestamp);
////            } catch (Exception e) {
////                throw new IllegalArgumentException("Invalid timestamp format. Expected 'yyyy-MM-dd HH:mm:ss'");
////            }
////        } else {
////            throw new IllegalArgumentException("Timestamp cannot be null");
////        }
////
////        userActionRepository.save(userAction);
////    }
//}
