package cn.darkflame.meta.db.service;

import java.util.Map;

/**
 * @author james
 */
public interface ITableService {

    /**
     * 创建表方法
     *
     * @param tblName 表名
     * @param colInfo 列信息
     * @return 创建结果
     */
    String createTbl(String tblName, Map<String, String> colInfo);

}
