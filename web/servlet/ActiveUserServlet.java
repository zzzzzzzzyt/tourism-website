package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        String code = request.getParameter("code");
        //激活
        if (code!=null) {
            //调用service完成激活
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            //判定标记
            String msg = null;
            if (flag){
                //激活成功
                msg = "激活成功，请<a href = 'login.html'>登录</a>";
            }else {
                //激活失败
                msg = "激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
