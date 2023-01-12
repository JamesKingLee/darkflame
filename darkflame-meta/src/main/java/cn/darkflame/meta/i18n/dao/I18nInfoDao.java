package cn.darkflame.meta.i18n.dao;

import cn.darkflame.meta.i18n.model.I18nInfo;
import cn.darkflame.meta.i18n.model.I18nInfoKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface I18nInfoDao {
    int deleteByPrimaryKey(I18nInfoKey key);

    int insert(I18nInfo record);

    int insertSelective(I18nInfo record);

    I18nInfo selectByPrimaryKey(I18nInfoKey key);

    int updateByPrimaryKeySelective(I18nInfo record);

    int updateByPrimaryKey(I18nInfo record);
}