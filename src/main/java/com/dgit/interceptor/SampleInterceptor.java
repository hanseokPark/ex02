package com.dgit.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SampleInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("pre Handle.................");
		
		HandlerMethod method = (HandlerMethod) handler;		
		Method methodObj = method.getMethod();
		
		System.out.println("Bean : " + method.getBean());
		System.out.println("Method : " + methodObj);
		
		
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("post Handle.................");
		
		
		 Object result = modelAndView.getModel().get("result");
		
		 if(result != null){
			 request.getSession().setAttribute("sessionResult", result);
			 response.sendRedirect("doA");
		 }
		
		
	}
	

}
































