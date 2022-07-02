package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Myfavorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ? ";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite WHERE rid = ?";

        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql, rid, new Date(), uid);
    }

    @Override
    public List selectMyFavoriteByUid(int uid) {

        String sql = "select * from tab_favorite where uid = ? ";
        List<Myfavorite> myfavorites = template.query(sql, new BeanPropertyRowMapper<>(Myfavorite.class), uid);
        List<Route> list = new ArrayList<>();
        for (Myfavorite myfavorite : myfavorites) {
            int rid = myfavorite.getRid();
            String newsql = "select * from tab_route where rid = ? ";
            List<Route> routes = template.query(newsql, new BeanPropertyRowMapper<>(Route.class), rid);
            for (Route smallroute : routes) {
                list.add(smallroute);
            }

        }
        return list;
    }
}
