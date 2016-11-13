package com.ysu.zyw.tc.components.utils.dubbox;

import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.model.validator.TcValidator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TcValidationExceptionMapper extends RpcExceptionMapper {

    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        TcValidator.TcVerifyFailure tcVerifyFailure = new TcValidator.TcVerifyFailure();
        cve.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .forEach(tcVerifyFailure::add);
        return Response
                .status(Response.Status.OK)
                .entity(determineResponse().setExtra(tcVerifyFailure))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // generic type with jackson serialization, do not cause any problem, if use other serialization, may failed.
    private <T, E> TcR<T, E> determineResponse() {
        return new TcR<>();
    }

}
