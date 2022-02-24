package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";因为有些情况下cid不存在就无法查询无法查询
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if (cid!=0){
            sb.append(" and cid = ? ") ;//String对象的不可变性，如果需要对字符串进行大量的修改、添加字符、删除字符等操作尽量不要使用String对象，因为这样会频繁的创建新的对象导致程序的执行效率下降
            params.add(cid);//添加问号对应条件的值
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");//mysql中模糊查询  就是必须得%查询字段%
        }

        sql = sb.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());//第二个参数是需要的返回值的类型  第三个传入的是个可变参数 传了个数组进去
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid = ? limit ? , ?";// limit 第一个参数是起始位置 第二个参数是查询多少长度
        //1.定义一个sql模板
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid!=0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()!=0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(pageSize);

        sql = sb.toString(); //这一步千万要记得 不然就还是用原来老的sql了
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
