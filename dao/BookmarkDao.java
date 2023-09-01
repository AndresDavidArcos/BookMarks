package dao;

import data.DataStore;
import entities.Bookmark;
import entities.UserBookmark;
import entities.WebLink;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDao {
    public List<List<Bookmark>> getBookmarks(){
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        DataStore.add(userBookmark);
    }

    public List<WebLink> getAllWebLinks(){
        List<WebLink> result = new ArrayList<>();
        List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
        List<Bookmark> allWebLinks = bookmarks.get(0);
        for(Bookmark weblink :  allWebLinks){
            result.add((WebLink) weblink);
        }
        return result;
    }

    public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus){
        List<WebLink> result = new ArrayList<>();
        List<WebLink> allweblinks = getAllWebLinks();
        for (WebLink webLink : allweblinks){
            if(webLink.getDownloadStatus().equals(downloadStatus)){
                result.add(webLink);
            }
        }
        return result;
    }

}
