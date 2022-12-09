package cn.darkflame.meta.db.model;


/**
 * @author james
 */
public class TableBaseInfo {

    private Integer id;

    private String tableName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
