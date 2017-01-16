package com.krestone.savealife.data.sync.states;


public enum InAppContact {
    TRUE,
    FALSE,
    UNSPECIFIED;

    public static InAppContact fromInteger(int x) {
        switch (x) {
            case 0:
                return TRUE;
            case 1:
                return FALSE;
            case 2:
                return UNSPECIFIED;
        }
        return null;
    }
}
