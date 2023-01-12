package cn.darkflame.meta.i18n.model;

import java.io.Serializable;

/**
 * i18n_info
 * @author 
 */
public class I18nInfo extends I18nInfoKey implements Serializable {
    private String info;

    private static final long serialVersionUID = 1L;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        I18nInfo other = (I18nInfo) that;
        return (this.getResourceFile() == null ? other.getResourceFile() == null : this.getResourceFile().equals(other.getResourceFile()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getInfo() == null ? other.getInfo() == null : this.getInfo().equals(other.getInfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getResourceFile() == null) ? 0 : getResourceFile().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getInfo() == null) ? 0 : getInfo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", info=").append(info);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}