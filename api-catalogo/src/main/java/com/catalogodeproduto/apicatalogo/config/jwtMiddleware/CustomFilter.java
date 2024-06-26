package com.catalogodeproduto.apicatalogo.config.jwtMiddleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class CustomFilter extends HttpServletRequestWrapper {
      private final Map<String, String> headerMap = new HashMap<>();
      public CustomFilter(HttpServletRequest request) {
            super(request);
      }

      @Override
      public String getHeader(String name) {
            String header = super.getHeader(name);
            return (header != null) ? header : super.getParameter(name);
      }

      @Override
      public Enumeration<String> getHeaders(String name) {
            Set<String> set = new HashSet<>();
            Optional.ofNullable(headerMap.get(name)).ifPresent(set::add);
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaders(name);
            while (e.hasMoreElements()) {
                  String n = e.nextElement();
                  set.add(n);
            }
            Optional.ofNullable(headerMap.get(name)).ifPresent(set::add);
            return Collections.enumeration(set);
      }

      public void addHeader(String name, String value) {
            headerMap.put(name, value);
      }
}
