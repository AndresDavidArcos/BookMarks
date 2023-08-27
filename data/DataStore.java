package data;

import constants.BookGenre;
import constants.Gender;
import entities.Bookmark;
import entities.User;
import entities.UserBookmark;
import managers.BookmarkManager;
import managers.UserManager;
import utils.IOUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final String absolutePath = new File("").getAbsolutePath()+File.separator+"data";
    private static final String fileSeparator = File.separator;

    private static List<User> users = new ArrayList<>();
    public static List<User> getUsers() {
        return users;
    }

    private static List<List<Bookmark>>bookmarks = new ArrayList<>();
    public static List<List<Bookmark>> getBookmarks() {
        return bookmarks;
    }

    private static List<UserBookmark> userBookmarks = new ArrayList<>();

    public static void loadData() {
        loadUsers();
        loadWebLinks();
        loadMovies();
        loadBooks();
    }

    private static void loadUsers() {
        List<String> data = new ArrayList<>();
        IOUtil.read(data, absolutePath+fileSeparator+"User");
        for (String row : data) {
            String[] values = row.split("\t");

            Gender gender = Gender.MALE;
            if (values[5].equals("f")) {
                gender = Gender.FEMALE;
            } else if (values[5].equals("t")) {
                gender = Gender.TRANSGENDER;
            }

            User user = UserManager.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3], values[4], gender, values[6]);
            users.add(user);
        }
    }

    private static void loadWebLinks() {
        List<String> data = new ArrayList<>();
        IOUtil.read(data, absolutePath+fileSeparator+"WebLink");
        List<Bookmark> bookmarkList = new ArrayList<>();
        for (String row : data) {
            String[] values = row.split("\t");
            Bookmark bookmark = BookmarkManager.getInstance().createWebLink(Long.parseLong(values[0]), values[1], values[2], values[3]/*, values[4]*/);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }

    private static void loadMovies() {
        List<String> data = new ArrayList<>();
        IOUtil.read(data, absolutePath+fileSeparator+"Movie");
        List<Bookmark> bookmarkList = new ArrayList<>();
        for (String row : data) {
            String[] values = row.split("\t");
            String[] cast = values[3].split(",");
            String[] directors = values[4].split(",");
            Bookmark bookmark = BookmarkManager.getInstance().createMovie(Long.parseLong(values[0]), values[1], "", Integer.parseInt(values[2]), cast, directors, values[5], Double.parseDouble(values[6])/*, values[7]*/);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);

    }

    private static void loadBooks() {
        List<String> data = new ArrayList<>();
        IOUtil.read(data, absolutePath+fileSeparator+"Book");
        List<Bookmark> bookmarkList = new ArrayList<>();
        for (String row : data) {
            String[] values = row.split("\t");
            String[] authors = values[4].split(",");
            Bookmark bookmark = BookmarkManager.getInstance().createBook(Long.parseLong(values[0]), values[1], Integer.parseInt(values[2]), values[3], authors, BookGenre.valueOf(values[5]), Double.parseDouble(values[6])/*, values[7]*/);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);

    }

    public static void add(UserBookmark userBookmark) {
        userBookmarks.add(userBookmark);

    }
}
