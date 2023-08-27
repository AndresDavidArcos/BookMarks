package dao;

import data.DataStore;
import entities.Bookmark;
import entities.UserBookmark;

import java.util.List;

public class BookmarkDao {
    public List<List<Bookmark>> getBookmarks(){
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        DataStore.add(userBookmark);
    }
}
