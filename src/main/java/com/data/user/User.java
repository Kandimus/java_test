package com.testjava.data.user;

import lombok.Value;

@Value
class UserRegistrationRequest {
   String login;
   String password;
}
