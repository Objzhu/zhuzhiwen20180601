package bie.com.yuekao;

import java.util.List;


public class News {
    public String stat;
    public List<Data> data;
    public class Data {
        public String topic;
        public String source;
        public List<IMG> miniimg;
        public class IMG {
            public String src;
        }
    }
}
