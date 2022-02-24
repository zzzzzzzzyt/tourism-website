package cn.itcast.travel.service;

import java.util.List;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 根据uid查询我的收藏
     * @param uid
     * @return
     */
    List selectMyFavoriteByUid(int uid);
}
