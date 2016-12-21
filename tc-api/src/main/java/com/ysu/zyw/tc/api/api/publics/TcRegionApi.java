package com.ysu.zyw.tc.api.api.publics;

import com.ysu.zyw.tc.model.api.o.publics.ToCity;
import com.ysu.zyw.tc.model.api.o.publics.ToProvince;
import com.ysu.zyw.tc.model.api.o.publics.ToRegion;
import com.ysu.zyw.tc.model.mw.TcP;
import com.ysu.zyw.tc.model.mw.TcR;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/regions")
public interface TcRegionApi {

    @GET
    @Path(value = "/find_provinces/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToProvince>> findProvinces(
            @PathParam(value = "id") String countryId
    );

    @GET
    @Path(value = "/find_cities/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToCity>> findCities(
            @PathParam(value = "id") String provinceId
    );

    @GET
    @Path(value = "/find_regions/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcP<List<ToRegion>> findRegions(
            @PathParam(value = "id") String cityId
    );

    @GET
    @Path(value = "/find_region/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<ToRegion> findRegion(
            @PathParam(value = "id") String regionId
    );

}
