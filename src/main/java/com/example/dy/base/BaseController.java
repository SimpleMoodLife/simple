package com.example.dy.base;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dy
 * @Date: 2019/1/4 23:05
 * @Description:
 */
public class BaseController {

    /**
     * 把浏览器参数转化放到Map集合中
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    protected Map<String, Object> getParam(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String method = request.getMethod();
        Enumeration<?> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if(key!=null){
                if (key instanceof String) {
                    String value = request.getParameter(key.toString());
                    if("GET".equals(method)){//前台encodeURIComponent('我们');转码后到后台还是ISO-8859-1，所以还需要转码
                        try {
                            value =new String(value.getBytes("ISO-8859-1"),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    paramMap.put(key.toString(), value);
                }
            }
        }
        return paramMap;
    }

    //将数据刷新写回web端
    protected void flushResponse(HttpServletResponse response, String responseContent) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("GBK");
            // 针对ajax中页面编码为GBK的情况，一定要加上以下两句
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=UTF-8");
            writer = response.getWriter();
            if (responseContent==null || "".equals(responseContent) || "null".equals(responseContent)) {
                writer.write("");
            } else {
                writer.write(responseContent);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * 获取session对象
     * @return
     */
    protected HttpSession getSession(HttpServletRequest request){
        HttpSession session =request.getSession();
        return session;
    }
}
