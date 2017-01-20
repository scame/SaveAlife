package com.krestone.savealife.data.entities.responses.map;


import com.krestone.savealife.presentation.models.RouteModel;

public class HelpIntentWrapper {

    private HelpIntentState helpIntentState;

    private RouteModel routeModel;

    public HelpIntentWrapper(HelpIntentState helpIntentState, RouteModel routeModel) {
        this.helpIntentState = helpIntentState;
        this.routeModel = routeModel;
    }

    public HelpIntentWrapper() { }

    public HelpIntentState getHelpIntentState() {
        return helpIntentState;
    }

    public void setHelpIntentState(HelpIntentState helpIntentState) {
        this.helpIntentState = helpIntentState;
    }

    public RouteModel getRouteModel() {
        return routeModel;
    }

    public void setRouteModel(RouteModel routeModel) {
        this.routeModel = routeModel;
    }
}
