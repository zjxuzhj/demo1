package com.zhj.demo1.model;

public class LoveHealing {

    /**
     * chat_count : 4
     * chat_name : 别让我看见你
     * detail : [{"ans_sex":"1","content":"别让我看见你","id":4,"lovewords_id":2},{"ans_sex":"2","content":"为什么啊？","id":5,"lovewords_id":2},{"ans_sex":"1","content":"不然我见你一次，就喜欢你一次。","id":6,"lovewords_id":2},{"ans_sex":"2","content":"真棒","id":318,"lovewords_id":2}]
     * id : 2
     * quiz_sex : 1
     * search_type : 1
     */

    public String chat_count;
    public String chat_name;
    public int id;
    public int is_collect;
    public String quiz_sex;
    public String search_type;
    public String content;
    public int type;
    public boolean isShow = false;
    public String objectId;
    public String createdAt;
    public String updatedAt;

    public LoveHealing() {
    }

    public LoveHealing(int type, String chat_name) {
        this.type = type;
        this.chat_name = chat_name;
    }

    public LoveHealing(int type, String chat_count, String chat_name, int id, String quiz_sex, String search_type) {
        this.type = type;
        this.chat_count = chat_count;
        this.chat_name = chat_name;
        this.id = id;
        this.quiz_sex = quiz_sex;
        this.search_type = search_type;
    }

    @Override
    public String toString() {
        return "LoveHealing{" +
                "type=" + type +
                ", chat_count='" + chat_count + '\'' +
                ", chat_name='" + chat_name + '\'' +
                ", id=" + id +
                ", quiz_sex='" + quiz_sex + '\'' +
                ", search_type='" + search_type + '\'' +
                '}';
    }

}
