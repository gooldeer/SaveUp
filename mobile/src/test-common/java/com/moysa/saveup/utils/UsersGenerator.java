package com.moysa.saveup.utils;

import com.moysa.saveup.data.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class UsersGenerator {

    private static final String NAMES[] = {"John", "Steve", "Chris"};

    public static List<UserEntity> testUsers() {

        List<UserEntity> users = new ArrayList<>();

        int index = 0;

        for (String name : NAMES) {

            UserEntity user = new UserEntity();

            user.setName(name);
            user.setId(++index);

            users.add(user);
        }
        return users;
    }

    public static UserEntity testUser() {

        UserEntity user = new UserEntity();

        user.setId(1);
        user.setName(NAMES[0]);

        return user;
    }
}
