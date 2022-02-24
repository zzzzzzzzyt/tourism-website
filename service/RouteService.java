package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    public Route findOne(String rid);
}
