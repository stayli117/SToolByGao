package net.people.stoolui.datas.impl;


import com.transfar.smarttda.core.DataAgent;

import net.people.stoolui.datas.IDataStore;

/**
 * Created by wulei
 * Data: 2016/12/26.
 */

public class DataStore implements IDataStore {
//    private DataStore(){
//
//    }
//
//    private static class DataStoreInstance{
//        public static final DataStore Instance = new DataStore();
//    }
//
//    public static DataStore getInstance(){
//        return DataStoreInstance.Instance;
//    }

    public static DataAgent getProxy(){
        return DataAgent.getInstance();
    }
}
