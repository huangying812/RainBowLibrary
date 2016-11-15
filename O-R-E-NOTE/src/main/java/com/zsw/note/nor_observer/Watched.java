package com.zsw.note.nor_observer;

/**
 * Create on 2016/11/15.
 * github  https://github.com/HarkBen
 * Description:
 * --比如是运钞任务---上层抽象被观察者--
 * -需要保存观察者，和逐个通知观察者
 * ---
 * author Ben
 * Last_Update - 2016/11/15
 */
public interface Watched {

    void addWatcher(Watcher watcher);
    void removeWatcher(Watcher watcher);

    void notifyAllWatcher(String msg);

}
