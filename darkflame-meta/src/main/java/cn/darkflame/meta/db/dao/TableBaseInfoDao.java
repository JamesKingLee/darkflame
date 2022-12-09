package cn.darkflame.meta.db.dao;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author james
 */
@Component
public interface TableBaseInfoDao {

    /**
     * 创建表接口方法
     *
     * @param tblName
     * @param colInfo
     * @see
     */
    void createTbl(String tblName, Map<String, String> colInfo);

}
