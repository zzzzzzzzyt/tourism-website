package cn.itcast.travel.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/exitServlet")
public class ExitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        // request.getSession().removeAttribute("user");或者移除属性也是一样的
        request.getSession().invalidate();
        //跳转到登录界面
        response.sendRedirect(request.getContextPath()+"/login.html");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
