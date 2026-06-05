package com.sanctuary.entity.rest;

import com.almasb.fxgl.entity.Entity;

public final class RestPointProperties {

    private static final String REST_POINT_DATA_KEY = "restPointData";

    private RestPointProperties() {
    }

    public static void setData(Entity entity, RestPointData data) {
        entity.setProperty(REST_POINT_DATA_KEY, data);
    }

    public static RestPointData getData(Entity entity) {
        return entity.getObject(REST_POINT_DATA_KEY);
    }
}
