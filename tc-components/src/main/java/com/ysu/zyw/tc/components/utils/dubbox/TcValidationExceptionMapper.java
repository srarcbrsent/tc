package com.ysu.zyw.tc.components.utils.dubbox;

import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.ysu.zyw.tc.model.mw.TcExtra;
import com.ysu.zyw.tc.model.mw.TcR;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

public class TcValidationExceptionMapper extends RpcExceptionMapper {

    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        String descriptions = cve.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        TcExtra tcExtra = new TcExtra(9999, descriptions);
        return Response
                .status(Response.Status.OK)
                .entity(determineResponse().setExtra(tcExtra))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // generic type with jackson serialization, do not cause any problem,
    // if use other serialization, may cause generic type cast failed.
    private <T> TcR<T> determineResponse() {
        return new TcR<>();
    }

}
