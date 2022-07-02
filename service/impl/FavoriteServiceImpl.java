package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteDao dao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = dao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null; //因为要求的就是个布尔值 如果对象有值代表已经收藏过了
    }

    @Override
    public void add(String rid, int uid) {
        dao.add(Integer.parseInt(rid), uid);
    }

    @Override
    public List selectMyFavoriteByUid(int uid) {
        return dao.selectMyFavoriteByUid(uid);
    }
}
