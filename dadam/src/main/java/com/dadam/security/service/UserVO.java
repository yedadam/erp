package com.dadam.security.service;

import lombok.Data;

@Data
public class UserVO {
   private String username;
   private String password;
   private String roleId;
   private String name;
}
