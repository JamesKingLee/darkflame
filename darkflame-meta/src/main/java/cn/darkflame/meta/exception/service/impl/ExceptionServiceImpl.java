package cn.darkflame.meta.exception.service.impl;

import cn.darkflame.meta.common.ExceptionClassGenerator;
import cn.darkflame.model.meta.exception.ExceptionClassDO;
import cn.darkflame.meta.exception.service.IExceptionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author james
 */
@Service("exceptionService")
public class ExceptionServiceImpl implements IExceptionService {

    @Resource
    private ExceptionClassGenerator exceptionClassGen;

    @Override
    public void createException(ExceptionClassDO exceptionClass) throws IOException {
        exceptionClassGen.generateExceptionClass(exceptionClass);
    }
}
