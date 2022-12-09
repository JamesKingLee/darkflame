package cn.darkflame.meta.code.model;

import cn.darkflame.common.constant.KeywordConstant;

public class Param {

    private String type;

    private String name;

    public Param() {
    }

    public Param(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDynArgs() {
        int len = type.length();
        int endIdx = Math.max(len - 3, 0);
        int end = len - 1;
        for (; end >= endIdx; end--) {
            if (!KeywordConstant.FULL_STOP.equals(String.valueOf(type.charAt(end)))) {
                break;
            }
        }
        return end == endIdx - 1;
    }
}
