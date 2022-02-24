package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class  UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象是否存在
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为null
        if (u!=null){
            //用户名存在 注册失败
            return false;
        }else {
            //2.不存在的话 储存用户名
            //2.1设置激活码，唯一字符串
            user.setCode(UuidUtil.getUuid());
            //2.2设置激活状态
            user.setStatus("N");
            userDao.save(user);

            //3.发送邮件 邮件正文

            String content = "<a href='http://localhost/travel_war_exploded/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";

            MailUtils.sendMail(user.getEmail(),content,"激活邮件");
            return true;
        }
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        User user  = userDao.findByCode(code);
        if (user!=null){
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        User u = userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        return u;
    }


}
