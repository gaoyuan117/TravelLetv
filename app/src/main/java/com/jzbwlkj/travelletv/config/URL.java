package com.jzbwlkj.travelletv.config;

/**
 * Created by admin on 2017/2/21.
 */

public class URL {
    public static String TOKEN = "";

    public static String BASE_URL = "http://lxls.jzbwlkj.com/";//基地址
    public static String NATIVE_BASE_URL = "http://192.168.1.33/player/";//本地网
    public static String NET_BASE_URL = "http://lxls.jzbwlkj.com/";//互联网
    public static String IMG = "http://lxls.jzbwlkj.com/";

    /**
     * 上传图片
     */
    public static String UPLOAD_IMAGE = "api/File/uploadPicture";

    /**
     * 登录
     */
    public static final String GET_VERIFICATION_CODE = "api//Public/verify";//获取验证码
    public static final String REGISTER = "api//User/regist";//注册
    public static final String LOGIN = "api//User/login";//登陆
    public static final String RESET_PASSWORD = "api//User/resetPassword";//找回密码
    public static final String FAST_LOGIN = "api/User/thirdPartylogin";//第三方登录
    public static final String AUTHENTICATION = "api/User/identify";//实名认证

    /**
     * 首页
     */
    public static final String GET_TABLAYOUT = "api/VideoCategory/category";//首页TabLayout
    public static final String BANNER = "api/VideoCategory/getLunbo";//首页轮播图
    public static final String HOME_NEW_VIDEO = "api/Movie/getNewMovie";//首页推荐
    public static final String SEARCH = "api/Movie/search";//搜索


    /**
     * 导航
     */
    public static final String NAVIGATION = "api/VideoCategory/index";//导航分类
    public static final String ARTICLELIST = "api/Article/articleList";//文章列表
    public static final String ARTICLECONTENT = "api/Article/articleContent";//文章内容
    public static final String VIDEOLIST = "api/Movie/categoryList";//视频分类
    public static final String BUY_ARTICLE = "api/Article/buyArticle";//购买视频



    /**
     * 我的
     */
    public static final String MY_BASE_INFO = "api/User/BaseInfo";//个人信息
    public static final String UPLOAD_AVATAR = "api/User/updateAvatar";//上传头像
    public static final String ABOUT_US = "api/Setup/aboutUs";//关于我们
    public static final String PROTOCOL_GUIDE = "api/Setup/getGuide";//协议和指南
    public static final String COMPLAIN = "api/Advise/addadvise";//投诉和建议
    public static final String VIP_BUY = "api/Member/buyVipPage";//VIP购买
    public static final String BUY_VIDEO = "api/Movie/purchasedMovies";//本地以购买视频
    public static final String RECHARGE_VIP = "api/Member/buyVip";//购买会员
    public static final String GET_ORDER = "api/Pay/rechargeOrder";//获取订单编号

    /**
     * 播放页
     */
    public static final String GET_PLAY = "api/Movie/playMovie";//投诉和建议
    public static final String ADD_COLLECT = "api/Collect/addCollect";//添加收藏
    public static final String GET_COLLECT = "api/Collect/collectList";//获取收藏列表
    public static final String BUY_PLAY = "api/Movie/buyMovie";//购买视频

}

