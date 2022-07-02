package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据线路的id查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param parseInt
     * @param uid
     */
    void add(int parseInt, int uid);

    /**
     * 查找图片
     * @param uid
     * @return
     */
    List selectMyFavoriteByUid(int uid);
}
