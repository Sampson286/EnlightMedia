package com.enlight.android.common.retrofitnetwork.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2017/8/30.
 */

public class IndexData {


    /**
     * list : {"app_ew_home_focus":[{"0":"actor","1":"http://img.xiankan.com/585ac9ab420f3f806f22.png","2":"37","type":"actor","img":"http://img.xiankan.com/585ac9ab420f3f806f22.png","id":"37"},{"0":"actor","1":"http://img.xiankan.com/a62e5a7ae5fe542381a6.png","2":"25","type":"actor","img":"http://img.xiankan.com/a62e5a7ae5fe542381a6.png","id":"25"},{"0":"news","1":"http://img2.zol.com.cn/product/147_120x80/767/cedAdcdVZP3bM.jpg","2":"19","type":"news","img":"http://img2.zol.com.cn/product/147_120x80/767/cedAdcdVZP3bM.jpg","id":"19"},{"0":"movie","1":"http://img.xiankan.com/4cbd58ed2fb321d92ec9.jpg","2":"215700","type":"movie","img":"http://img.xiankan.com/4cbd58ed2fb321d92ec9.jpg","id":"215700"},{"0":"short","1":"http://img.xiankan.com/4a101f1a3aaa04f40aa9.jpg","2":"215710","type":"short","img":"http://img.xiankan.com/4a101f1a3aaa04f40aa9.jpg","id":"215710"}],"app_ew_home_offer":[{"0":"5803","id":"5803","img":"http://img.xiankan.com/02031b5da81296365ff5.jpg","title":"光线传媒：中国证监会关于核准公司首次公开发行股票并在创业板上市的批复","subtitle":"光线传媒：中国证监会关于核准公司首次公开发行股票并在创业板上市的批复"}],"app_ew_home_new":[{"0":"215699","id":"215699","img":"http://img.xiankan.com/553f25bb440aa653d4d7.jpg?x-oss-process=style/shutu","title":"异能者之撒旦之眼","subtitle":"超能力战队拯救世界"},{"0":"215698","id":"215698","img":"http://img.xiankan.com/ce14449ba5c51453c0b9.jpg?x-oss-process=style/shutu","title":"一拍成名","subtitle":"丑女无敌大翻身"},{"0":"215645","id":"215645","img":"http://img.xiankan.com/7ef262b6cb2831df3a7a.jpg?x-oss-process=style/shutu","title":"辣条侦探","subtitle":"逗比侦探冒充警察破奇案"}],"app_ew_home_trailer":[{"0":"215710","id":"215710","img":"http://img.xiankan.com/4a101f1a3aaa04f40aa9.jpg?x-oss-process=style/hengtu","title":"吹梦巨人","subtitle":"《吹梦巨人》斯皮尔伯格16年新作"},{"0":"215707","id":"215707","img":"http://img.xiankan.com/acc8938dac036c6c117b.jpg?x-oss-process=style/hengtu","title":"zkp的测试短片","subtitle":"zkp的测试短片"},{"0":"215696","id":"215696","img":"http://img.xiankan.com/7ad2c9234991ad100ffe.jpg?x-oss-process=style/hengtu","title":"搜索测试","subtitle":"搜索测试"}],"app_ew_home_news":[{"0":"19","id":"19","app_img":"http://img.xiankan.com/4b6a504b6dd13ce7aef5.jpg?x-oss-process=style/hengtu","title":"清新的性感 捕捉环境里最佳的人物肖像","app_add_date":"2015.10.21","comment_count":"1","browser":"1000","news_type":"3","app_imgs":["http://img.xiankan.com/b976a169700ec7d2fac9.png","http://img.xiankan.com/95e8f154be16f611fe6e.png","http://img.xiankan.com/e1876f749b312e68090c.jpg","http://img.xiankan.com/56ecc779ed7d34255a5f.jpg","http://img.xiankan.com/1362a577a3d9141240cc.jpg"]},{"0":"843","id":"843","app_img":"http://image170-c.poco.cn/mypoco/myphoto/20121218/16/5542013120121218161500072_640.jpg?x-oss-process=style/hengtu","title":"这张照片是怎么拍的？","app_add_date":"2015.10.28","comment_count":"0","browser":"0","news_type":"1","app_imgs":["http://image170-c.poco.cn/mypoco/myphoto/20121218/16/5542013120121218161500072_640.jpg"]},{"0":"5811","id":"5811","app_img":"http://img.xiankan.com/46f8e3881a415ac5cff2.jpg?x-oss-process=style/hengtu","title":"xindeceshi ","app_add_date":"2017.08.10","comment_count":"0","browser":"0","news_type":"1","app_imgs":[""]}],"app_ew_home_actor":[{"0":"http://img.xiankan.com/585ac9ab420f3f806f22.png","1":"这是谁呢?","2":"37","img":"http://img.xiankan.com/585ac9ab420f3f806f22.png","title":"这是谁呢?","id":"37"},{"0":"http://img.xiankan.com/a62e5a7ae5fe542381a6.png","1":"你猜猜","2":"25","img":"http://img.xiankan.com/a62e5a7ae5fe542381a6.png","title":"你猜猜","id":"25"},{"0":"http://img.xiankan.com/585ac9ab420f3f806f22.png","1":"文章的照片","2":"31","img":"http://img.xiankan.com/585ac9ab420f3f806f22.png","title":"文章的照片","id":"31"}]}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        private List<AppEwHomeFocusBean> app_ew_home_focus;
        private List<AppEwHomeOfferBean> app_ew_home_offer;
        private List<AppEwHomeNewBean> app_ew_home_new;
        private List<AppEwHomeTrailerBean> app_ew_home_trailer;
        private List<AppEwHomeNewsBean> app_ew_home_news;
        private List<AppEwHomeActorBean> app_ew_home_actor;

        public List<AppEwHomeFocusBean> getApp_ew_home_focus() {
            return app_ew_home_focus;
        }

        public void setApp_ew_home_focus(List<AppEwHomeFocusBean> app_ew_home_focus) {
            this.app_ew_home_focus = app_ew_home_focus;
        }

        public List<AppEwHomeOfferBean> getApp_ew_home_offer() {
            return app_ew_home_offer;
        }

        public void setApp_ew_home_offer(List<AppEwHomeOfferBean> app_ew_home_offer) {
            this.app_ew_home_offer = app_ew_home_offer;
        }

        public List<AppEwHomeNewBean> getApp_ew_home_new() {
            return app_ew_home_new;
        }

        public void setApp_ew_home_new(List<AppEwHomeNewBean> app_ew_home_new) {
            this.app_ew_home_new = app_ew_home_new;
        }

        public List<AppEwHomeTrailerBean> getApp_ew_home_trailer() {
            return app_ew_home_trailer;
        }

        public void setApp_ew_home_trailer(List<AppEwHomeTrailerBean> app_ew_home_trailer) {
            this.app_ew_home_trailer = app_ew_home_trailer;
        }

        public List<AppEwHomeNewsBean> getApp_ew_home_news() {
            return app_ew_home_news;
        }

        public void setApp_ew_home_news(List<AppEwHomeNewsBean> app_ew_home_news) {
            this.app_ew_home_news = app_ew_home_news;
        }

        public List<AppEwHomeActorBean> getApp_ew_home_actor() {
            return app_ew_home_actor;
        }

        public void setApp_ew_home_actor(List<AppEwHomeActorBean> app_ew_home_actor) {
            this.app_ew_home_actor = app_ew_home_actor;
        }

        public static class AppEwHomeFocusBean {
            /**
             * 0 : actor
             * 1 : http://img.xiankan.com/585ac9ab420f3f806f22.png
             * 2 : 37
             * type : actor
             * img : http://img.xiankan.com/585ac9ab420f3f806f22.png
             * id : 37
             */

            @SerializedName("0")
            private String _$0;
            @SerializedName("1")
            private String _$1;
            @SerializedName("2")
            private String _$2;
            private String type;
            private String img;
            private String id;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }

            public String get_$1() {
                return _$1;
            }

            public void set_$1(String _$1) {
                this._$1 = _$1;
            }

            public String get_$2() {
                return _$2;
            }

            public void set_$2(String _$2) {
                this._$2 = _$2;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class AppEwHomeOfferBean {
            /**
             * 0 : 5803
             * id : 5803
             * img : http://img.xiankan.com/02031b5da81296365ff5.jpg
             * title : 光线传媒：中国证监会关于核准公司首次公开发行股票并在创业板上市的批复
             * subtitle : 光线传媒：中国证监会关于核准公司首次公开发行股票并在创业板上市的批复
             */

            @SerializedName("0")
            private String _$0;
            private String id;
            private String img;
            private String title;
            private String subtitle;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }
        }

        public static class AppEwHomeNewBean {
            /**
             * 0 : 215699
             * id : 215699
             * img : http://img.xiankan.com/553f25bb440aa653d4d7.jpg?x-oss-process=style/shutu
             * title : 异能者之撒旦之眼
             * subtitle : 超能力战队拯救世界
             */

            @SerializedName("0")
            private String _$0;
            private String id;
            private String img;
            private String title;
            private String subtitle;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }
        }

        public static class AppEwHomeTrailerBean {
            /**
             * 0 : 215710
             * id : 215710
             * img : http://img.xiankan.com/4a101f1a3aaa04f40aa9.jpg?x-oss-process=style/hengtu
             * title : 吹梦巨人
             * subtitle : 《吹梦巨人》斯皮尔伯格16年新作
             */

            @SerializedName("0")
            private String _$0;
            private String id;
            private String img;
            private String title;
            private String subtitle;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }
        }

        public static class AppEwHomeNewsBean {
            /**
             * 0 : 19
             * id : 19
             * app_img : http://img.xiankan.com/4b6a504b6dd13ce7aef5.jpg?x-oss-process=style/hengtu
             * title : 清新的性感 捕捉环境里最佳的人物肖像
             * app_add_date : 2015.10.21
             * comment_count : 1
             * browser : 1000
             * news_type : 3
             * app_imgs : ["http://img.xiankan.com/b976a169700ec7d2fac9.png","http://img.xiankan.com/95e8f154be16f611fe6e.png","http://img.xiankan.com/e1876f749b312e68090c.jpg","http://img.xiankan.com/56ecc779ed7d34255a5f.jpg","http://img.xiankan.com/1362a577a3d9141240cc.jpg"]
             */

            @SerializedName("0")
            private String _$0;
            private String id;
            private String app_img;
            private String title;
            private String app_add_date;
            private String comment_count;
            private String browser;
            private String news_type;
            private List<String> app_imgs;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getApp_img() {
                return app_img;
            }

            public void setApp_img(String app_img) {
                this.app_img = app_img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getApp_add_date() {
                return app_add_date;
            }

            public void setApp_add_date(String app_add_date) {
                this.app_add_date = app_add_date;
            }

            public String getComment_count() {
                return comment_count;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public String getBrowser() {
                return browser;
            }

            public void setBrowser(String browser) {
                this.browser = browser;
            }

            public String getNews_type() {
                return news_type;
            }

            public void setNews_type(String news_type) {
                this.news_type = news_type;
            }

            public List<String> getApp_imgs() {
                return app_imgs;
            }

            public void setApp_imgs(List<String> app_imgs) {
                this.app_imgs = app_imgs;
            }
        }

        public static class AppEwHomeActorBean {
            /**
             * 0 : http://img.xiankan.com/585ac9ab420f3f806f22.png
             * 1 : 这是谁呢?
             * 2 : 37
             * img : http://img.xiankan.com/585ac9ab420f3f806f22.png
             * title : 这是谁呢?
             * id : 37
             */

            @SerializedName("0")
            private String _$0;
            @SerializedName("1")
            private String _$1;
            @SerializedName("2")
            private String _$2;
            private String img;
            private String title;
            private String id;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }

            public String get_$1() {
                return _$1;
            }

            public void set_$1(String _$1) {
                this._$1 = _$1;
            }

            public String get_$2() {
                return _$2;
            }

            public void set_$2(String _$2) {
                this._$2 = _$2;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
