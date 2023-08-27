package com.cooper.springtest.dirtiescontext;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserCache {

    private List<String> userList = new ArrayList<>();

    public boolean addUser(String user) {
        return userList.add(user);
    }

    public boolean removeUser(String user) {
        return userList.remove(user);
    }

    public void printUserList(String message) {
        System.out.println(message + ": " + userList);
    }

}
