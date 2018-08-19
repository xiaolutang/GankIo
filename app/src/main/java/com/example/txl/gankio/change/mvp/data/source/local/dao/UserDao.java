package com.example.txl.gankio.change.mvp.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.txl.gankio.change.mvp.data.User;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/31
 * description：
 */
@Dao
public interface UserDao {

    @Query( "select * from tb_user" )
    List<User> getUsers();

    @Query( "select * from tb_user where user_name = :userName" )
    User getUserByName(String userName);

    @Query( "select * from tb_user where user_name = :userName and user_password = :password" )
    User LoginUserByName(String userName, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    int updateUser(User user);

    @Query("DELETE FROM tb_user WHERE user_name = :userName")
    int deleteUserByName(String userName);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM tb_user")
    void deleteUsers();
}
