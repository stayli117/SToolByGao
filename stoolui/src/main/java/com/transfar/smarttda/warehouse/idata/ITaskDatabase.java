package com.transfar.smarttda.warehouse.idata;

import com.transfar.smarttda.bean.TaskDB;

import java.util.List;

/**
 * Created by stayli on 2017/3/30.
 */

public interface ITaskDatabase {
    /**
     * 插入一个缓存任务
     * @param taskDB
     * @return
     */
    boolean insertTask(TaskDB taskDB);

    /**
     * 删除一个缓存任务
     * @param id
     * @return
     */
    boolean deleteTask(int id);

    /**
     * 清空所有的缓存任务
     * @return
     */
    boolean deleteTasks();

    /**
     * 删除指定类型的任务
     * @return
     */
    boolean deleteTasks(String type);

    /**
     * 获取所有的缓存任务
     * @return
     */
    List<TaskDB> getTaskList();

    /**
     * 获取所有的指定类型的任务缓存任务
     * @return
     */
    List<TaskDB> getTaskListByType(String type);
}
