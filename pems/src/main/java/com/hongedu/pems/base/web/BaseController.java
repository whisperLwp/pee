package com.hongedu.pems.base.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hongedu.pems.base.exception.BaseException;


/**
 * 自定义controller基类
 * @author zyb
 *
 */
public class BaseController {
    @ExceptionHandler  
    public String exp(HttpServletRequest request, Exception ex) throws Exception{  
          throw new BaseException(ex);
    }
}
