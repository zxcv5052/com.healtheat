package com.healthteat.common.error;

import com.healthteat.common.domain.TemplateResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public TemplateResult exceptionHandler(Exception e)
    {
        TemplateResult result;
        final String errMsg = e.getMessage();
        if(errMsg == null) result = TemplateResult.ERROR(TemplateResult.SERVER_ERROR_MESSAGE);
        else result = TemplateResult.ERROR(errMsg);

        return result;
    }

}
