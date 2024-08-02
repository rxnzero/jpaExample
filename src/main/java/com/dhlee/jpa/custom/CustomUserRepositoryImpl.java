package com.dhlee.jpa.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.dhlee.jpa.rest.interceptors.GzipRequestInterceptor;
import com.dhlee.jpa.rest.interceptors.LoggingInterceptor;

import jakarta.annotation.PostConstruct;

@Repository
public class CustomUserRepositoryImpl implements UserRepositoryCustom {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserRepositoryImpl.class);

    private RestTemplate restTemplate;

    private final String serviceUrl = "http://localhost:8080/users";
    
    public RestTemplate restTemplate() {
    	logger.warn(">> init restTemplate configuration.");
    	
    	 // Connection configuration with timeout settings
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(2))  // 연결 타임아웃 2초
                .build();

        // Connection manager with the connection configuration
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);  // 최대 연결 수
        connectionManager.setDefaultMaxPerRoute(20);  // 각 경로당 최대 연결 수
        connectionManager.setDefaultConnectionConfig(connectionConfig);

        // Request configuration with timeout settings
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(2))  // 연결 요청 타임아웃 2초
                .setResponseTimeout(Timeout.ofSeconds(2))  // 읽기 타임아웃 2초
                .build();

        CloseableHttpClient httpClient = HttpClients.custom() 
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new GzipRequestInterceptor());
		interceptors.add(new LoggingInterceptor());
		restTemplate.setInterceptors(interceptors);
		
        return restTemplate;
    }

    @PostConstruct
    private void init() {
        restTemplate = restTemplate();
    }
    
    @Override
    public List<CustomUser> listAll() {
        // API 호출
    	CustomUser[] users = restTemplate.getForObject(serviceUrl, CustomUser[].class);
        // 배열을 리스트로 변환하여 반환
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

    // 기타 메서드 구현
}
