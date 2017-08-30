package android.enlight.com.common.retrofitnetwork.modle;

import java.util.List;

/**
 * Created by zyc on 2017/8/30.
 */

public class MovieData {
    /**
     * total : 1
     * list : [{"id":"215700","title":"下落不明","img_shu":"http://img.xiankan.com/e47abdde8cd247042db8.jpg?x-oss-process=style/shutu","playcount":"5477","duration":"01:01:59"}]
     * maxPage : 1
     * curPage : 1
     */

    private String total;
    private String maxPage;
    private String curPage;
    private List<ListBean> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(String maxPage) {
        this.maxPage = maxPage;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 215700
         * title : 下落不明
         * img_shu : http://img.xiankan.com/e47abdde8cd247042db8.jpg?x-oss-process=style/shutu
         * playcount : 5477
         * duration : 01:01:59
         */

        private String id;
        private String title;
        private String img_shu;
        private String playcount;
        private String duration;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_shu() {
            return img_shu;
        }

        public void setImg_shu(String img_shu) {
            this.img_shu = img_shu;
        }

        public String getPlaycount() {
            return playcount;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
