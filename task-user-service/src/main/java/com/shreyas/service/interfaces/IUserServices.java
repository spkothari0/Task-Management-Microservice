package com.shreyas.service.interfaces;

import com.shreyas.bean.UserBean;
import com.shreyas.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IUserServices {
    UserBean createUser(UserBean userBean);
    UserBean getUser(String token);
    List<UserBean> getAllUsers();
    UserBean getUserById(UUID id);
//    String verifyUser(String token);
//    boolean uploadProfileImage(String username, MultipartFile file);
}
