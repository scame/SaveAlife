package com.krestone.savealife.presentation.presenters;



public interface Presenter<T> {

    void setView(T view);

    void destroy();
}
