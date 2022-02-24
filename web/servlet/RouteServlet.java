package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");//不管有没有查找出来 都行 因为底下已经有万一没查找出来的 法子了

        //接受rname 线路名称
        String rname = request.getParameter("rname");
        /*
        假如使用的是tomcat7 则会出现乱码的情况 应改为
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        */

        //2.处理参数
        int cid = 0;//类别的id
        if (cidStr!=null && cidStr.length()!=0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;
        if (currentPageStr!=null && currentPageStr.length()!=0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr!=null && pageSizeStr.length()!=0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }

        //3.调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid,currentPage,pageSize,rname);

        //4.将pageBean对象序列化为json返回
        writeValue(pb,response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //获取rid
        String rid = request.getParameter("rid");
        //获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid ;//用户的id
        if (user==null){
            uid = 0;//用户尚未登录
        }else {
            //用户已经登录
            uid = user.getUid();
        };
        //调用service方法进行查询
        Boolean flag = favoriteService.isFavorite(rid,uid);

        //将flag写回客户端
        writeValue(flag,response);



    }

    /**
     * 添加收藏的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.获取线路rid
        String rid = request.getParameter("rid");
        //2.获取用户
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return;
        }else {
            uid = user.getUid();
        }

        //3.调用servicce添加
        favoriteService.add(rid,uid);

    }

    public void findMyFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return;
        }else {
            uid = user.getUid();
        }
        List list = favoriteService.selectMyFavoriteByUid(uid);
        //调用service查询 然后显示
        writeValue(list,response);
    }


}
