package wangjin.com.beijingnews.domain;

import java.util.List;

/**
 * Created by wangjin on 2018/9/18.
 * 手动写json的解析应用
 */

public class NewsCenterPagerBean2 {
    private List<DetailPagerData> data;
    private List extend;
    private int retcode;

    public void setData(List<DetailPagerData> data) {
        this.data = data;
    }

    public void setExtend(List extend) {
        this.extend = extend;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public List<DetailPagerData> getData() {
        return data;
    }

    public List getExtend() {
        return extend;
    }

    public int getRetcode() {
        return retcode;
    }

    @Override
    public String toString() {
        return "NewsCenterPagerBean2{" +
                "data=" + data +
                ", extend=" + extend +
                ", retcode=" + retcode +
                '}';
    }

    public static class DetailPagerData {
        private int id;
        private String title;
        private int type;
        private String url;
        private String url1;
        private String dayurl;
        private String excurl;
        private String weekurl;
        private List<ChildrenData> children;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public void setDayurl(String dayurl) {
            this.dayurl = dayurl;
        }

        public void setExcurl(String excurl) {
            this.excurl = excurl;
        }

        public void setWeekurl(String weekurl) {
            this.weekurl = weekurl;
        }

        public void setChildren(List<ChildrenData> children) {
            this.children = children;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public String getUrl1() {
            return url1;
        }

        public String getDayurl() {
            return dayurl;
        }

        public String getExcurl() {
            return excurl;
        }

        public String getWeekurl() {
            return weekurl;
        }

        public List<ChildrenData> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return "DetailPagerData{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    ", url1='" + url1 + '\'' +
                    ", dayurl='" + dayurl + '\'' +
                    ", excurl='" + excurl + '\'' +
                    ", weekurl='" + weekurl + '\'' +
                    ", children=" + children +
                    '}';
        }

        public static class ChildrenData{
            private int id;
            private String title;
            private int type;
            private String url;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public int getType() {
                return type;
            }

            public String getUrl() {
                return url;
            }

            @Override
            public String toString() {
                return "ChildrenData{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", type=" + type +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }

}
