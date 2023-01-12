package cn.darkflame.meta.i18n.controller;

import cn.darkflame.meta.i18n.service.I18nService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/i18n")
public class I18nController {

    @Resource
    private I18nService i18nService;

    @RequestMapping("/getSupportLanguageList")
    public Map<String, String> getSupportLanguageList() {
        return i18nService.getSupportLanguageList();
    }
}
