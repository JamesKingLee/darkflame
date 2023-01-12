package cn.darkflame.meta.i18n.service.impl;

import cn.darkflame.meta.i18n.dao.I18nLocInfoDao;
import cn.darkflame.meta.i18n.model.I18nLocInfo;
import cn.darkflame.meta.i18n.service.I18nService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("i18nService")
public class I18nServiceImpl implements I18nService {

    @Resource
    private I18nLocInfoDao i18nLocInfoDao;

    @Override
    public Map<String, String> getSupportLanguageList() {
        List<I18nLocInfo> i18nLocInfos = i18nLocInfoDao.selectAll();
        Map<String, String> result = new HashMap<>();
        i18nLocInfos.forEach(i18nLocInfo -> {
            result.put(i18nLocInfo.getCode(), i18nLocInfo.getName());
        });
        return result;
    }
}
