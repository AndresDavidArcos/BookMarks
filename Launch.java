import data.DataStore;
import entities.Bookmark;
import entities.User;
import managers.BookmarkManager;
import managers.UserManager;

public class Launch {
    private static User[] users = UserManager.getInstance().getUsers();
    private static Bookmark[][] bookmarks = BookmarkManager.getInstance().getBookmarks();

    private static void loadData(){
        DataStore.loadData();
        users =  UserManager.getInstance().getUsers();
        bookmarks = BookmarkManager.getInstance().getBookmarks();

    }

    private static void printUserData(){
        for(User user : users){
            System.out.println(user);
        }
    }

    private static void printBookmarksData(){
        for(Bookmark[] bookMarkSet: bookmarks){
            for(Bookmark bookmark : bookMarkSet){
                System.out.println(bookmark);
            }
        }
    }

    private static void start(){
        System.out.println("2. Browsing ...");
        for(User user : users){
            View.browse(user, bookmarks);
        }
    }

    public static void main(String[] args) {
        loadData();
//        printUserData();
//        printBookmarksData();
        start();
    }
}
