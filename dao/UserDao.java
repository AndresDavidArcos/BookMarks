package dao;

import data.DataStore;
import entities.User;

import java.util.List;

public class UserDao {
    public List<User> getUsers(){
        return DataStore.getUsers();
    }
}
