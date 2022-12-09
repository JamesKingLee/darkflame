package cn.darkflame.meta.exception.service;

import cn.darkflame.model.meta.exception.ExceptionClassDO;

import java.io.IOException;

/**
 * @author james
 */
public interface IExceptionService {

    void createException(ExceptionClassDO exceptionClass) throws IOException;

}
