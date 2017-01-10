package com.krestone.savealife.data.entities.responses.map;


import java.util.ArrayList;
import java.util.List;

public class RoutesEntity {

    List<RawRout> routes;

    public RoutesEntity() {
        routes = new ArrayList<>();
    }

    public List<RawRout> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RawRout> routes) {
        this.routes = routes;
    }
}
