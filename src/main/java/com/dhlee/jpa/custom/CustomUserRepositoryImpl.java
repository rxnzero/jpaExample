package com.dhlee.jpa.custom;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class CustomUserRepositoryImpl implements UserRepositoryCustom {
    @Autowired
    private RestTemplate restTemplate;

    private final String serviceUrl = "http://localhost:8080/users";

    @Override
    public List<CustomUser> listAll() {
        // API ȣ��
    	CustomUser[] users = restTemplate.getForObject(serviceUrl, CustomUser[].class);
        // �迭�� ����Ʈ�� ��ȯ�Ͽ� ��ȯ
        return Arrays.asList(users);
    }
    
    @Override
    public CustomUser findUserById(Long id) {
        return restTemplate.getForObject(serviceUrl + "/" + id, CustomUser.class);
    }

    @Override
    public CustomUser createUser(CustomUser user) {
        return restTemplate.postForObject(serviceUrl, user, CustomUser.class);
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(serviceUrl + "/" + id);
    }

    // ��Ÿ �޼��� ����
}
