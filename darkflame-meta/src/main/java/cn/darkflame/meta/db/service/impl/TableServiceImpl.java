package cn.darkflame.meta.db.service.impl;

import cn.darkflame.meta.db.dao.TableBaseInfoDao;
import cn.darkflame.meta.db.service.ITableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author james
 */
@Service("tableService")
public class TableServiceImpl implements ITableService {

    @Resource
    private TableBaseInfoDao tableBaseInfoDao;

    @Override
    public String createTbl(String tblName, Map<String, String> colInfo) {
        tableBaseInfoDao.createTbl(tblName, colInfo);
        return "success";
    }
}
