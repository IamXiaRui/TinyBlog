package com.tinyblog.base;

/**
 * @author xiarui
 * @date 2016/10/10 17:00
 * @desc View 层的基类
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

}
