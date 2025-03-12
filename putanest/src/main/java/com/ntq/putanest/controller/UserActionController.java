package com.ntq.putanest.controller;

import com.ntq.putanest.dto.UserActionDTO;
import com.ntq.putanest.pojo.UserAction;
import com.ntq.putanest.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-actions")
public class UserActionController {

    @Autowired
    private UserActionService userActionService;

    // API để thêm một UserAction mới
    @PostMapping
    public ResponseEntity<String> saveUserAction(@RequestBody UserActionDTO userActionDTO) {
        try {
            userActionService.saveUserAction(userActionDTO);
            return new ResponseEntity<>("User action saved successfully!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving user action.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserAction>> getUserActions(@PathVariable int userId) {
        List<UserAction> actions = userActionService.findByUserId(userId);
        return ResponseEntity.ok(actions);
    }
}

