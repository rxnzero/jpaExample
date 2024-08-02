package com.dhlee.jpa.rest.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dhlee.jpa.custom.CustomUserRepositoryImpl;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class GzipFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(GzipFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	logger.warn("init GzipFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	logger.warn("doFilter called");
    	if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            if (acceptsGzipEncoding(httpRequest)) {
                httpResponse.addHeader("Content-Encoding", "gzip");
                GzipResponseWrapper gzipResponseWrapper = new GzipResponseWrapper(httpResponse);
                chain.doFilter(request, gzipResponseWrapper);
                gzipResponseWrapper.finish();
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean acceptsGzipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains("gzip");
    }
}
