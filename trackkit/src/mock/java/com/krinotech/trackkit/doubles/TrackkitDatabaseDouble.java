package com.krinotech.trackkit.doubles;

import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.contract.TrackkitRoomDatabase;

public class TrackkitDatabaseDouble implements TrackkitRoomDatabase {

    @Override
    public TrackkitDao trackkitDao() {
        return new TrackkitDaoDouble();
    }
}
