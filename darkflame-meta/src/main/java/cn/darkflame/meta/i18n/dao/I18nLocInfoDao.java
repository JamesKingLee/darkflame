package cn.darkflame.meta.i18n.dao;

import cn.darkflame.meta.i18n.model.I18nLocInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface I18nLocInfoDao {
    int deleteByPrimaryKey(String code);

    int insert(I18nLocInfo record);

    int insertSelective(I18nLocInfo record);

    List<I18nLocInfo> selectAll();

    I18nLocInfo selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(I18nLocInfo record);

    int updateByPrimaryKey(I18nLocInfo record);
}