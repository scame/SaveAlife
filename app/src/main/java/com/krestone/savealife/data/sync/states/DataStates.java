package com.krestone.savealife.data.sync.states;


public enum DataStates {
    UP_TO_DATE,
    NEW,
    REMOVED;

    public static DataStates fromInteger(int x) {
        switch (x) {
            case 0:
                return UP_TO_DATE;
            case 1:
                return NEW;
            case 2:
                return REMOVED;
        }
        return null;
    }
}
