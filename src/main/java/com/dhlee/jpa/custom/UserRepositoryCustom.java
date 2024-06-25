package com.dhlee.jpa.custom;

import java.util.List;

public interface UserRepositoryCustom {
	List<CustomUser> listAll();
	CustomUser findUserById(Long id);
    CustomUser createUser(CustomUser user);
    void deleteUser(Long id);
}
