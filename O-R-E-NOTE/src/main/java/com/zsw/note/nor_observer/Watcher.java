package com.zsw.note.nor_observer;

/**
 * Create on 2016/11/15.
 * github  https://github.com/HarkBen
 * Description:
 * --密切监视运钞车的人---上层抽象观察者------
 * author Ben
 * Last_Update - 2016/11/15
 */
public interface Watcher {

    void notify(String msg);

}
