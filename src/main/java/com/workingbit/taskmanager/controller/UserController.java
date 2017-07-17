package com.workingbit.taskmanager.controller;

import com.workingbit.taskmanager.common.AppConstants;
import com.workingbit.taskmanager.common.EnumResponse;
import com.workingbit.taskmanager.common.StringMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Created by Aleksey Popryaduhin on 12:26 15/07/2017.
 */
@RestController
@RequestMapping(AppConstants.API)
public class UserController {

    @PostMapping(AppConstants.LOGIN_PATH)
    public StringMap login(@RequestBody StringMap credentials) {
        if (Objects.equals(credentials.getString("username"), "admin")
                && Objects.equals(credentials.getString("password"), "123")) {
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), true)
                    .build();
        }
        return StringMap.StringMapBuilder.getInstance()
                .put(EnumResponse.ok.name(), false)
                .build();
    }
}
