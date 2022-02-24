package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("bassServlet的service方法被执行了...");

        //完成方法的分发
        //1.获取请求路径
        String uri = req.getRequestURI();// /user/add
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        //3.获取方法对象Method
        try {
            //忽略访问权限修饰符，获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.执行方法
    }

    /**
     * 直接将传入的对象序列化为json，并且写回客户端
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json，返回
    @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);

    }
}
