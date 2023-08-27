import constants.KidFriendlyStatus;
import controllers.BookmarkController;
import entities.Bookmark;
import entities.User;
import partner.Shareable;

import java.util.List;

import static constants.KidFriendlyStatus.*;
import static constants.UserType.*;

public class View {

    public static void browse(User user, List<List<Bookmark>> bookmarks){
        System.out.println("\n"+user.getEmail()+" is browsing");
        for (List<Bookmark> bookmarkList : bookmarks){
            for (Bookmark bookmark : bookmarkList){
                //Bookmarking
                    boolean isBookmarked = getBookmarkDecision(bookmark);
                    if(isBookmarked){
                        BookmarkController.getInstance().saveUserBookmark(user, bookmark);
                        System.out.println("New item bookmarked: "+bookmark);
                    }
                    //Mark as kid-friendly
                    if(user.getUserType().equals(EDITOR) || user.getUserType().equals(CHIEF_EDITOR)){
                        if(bookmark.isKidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(UNKNOWN)){
                            KidFriendlyStatus kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                            if(!kidFriendlyStatus.equals(UNKNOWN)){
                                BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
                            }
                        }
                        //SHAREABLE
                        if(bookmark.getKidFriendlyStatus().equals(APPROVED) && bookmark instanceof Shareable){
                            boolean isShared = getShareDecision();
                            if(isShared){
                                BookmarkController.getInstance().share(user, bookmark);
                            }
                        }
                    }

            }
        }


    }

    private static boolean getShareDecision() {
        return Math.random() < 0.5 ? true : false;
    }

    private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
        int desicion = (int) (Math.random() * 3);
        KidFriendlyStatus status;
        switch (desicion){
            case 0: status =  APPROVED;
            break;
            case 1: status =  REJECTED;
            break;
            default: status =  UNKNOWN;
        }
        return status;
    }

    private static boolean getBookmarkDecision(Bookmark bookmark){
        return Math.random() < 0.5 ? true : false;
    }

//    public static void bookmark(User user, Bookmark[][] bookmarks){
//        System.out.println("\n"+user.getEmail()+" is bookmarking");
//        for (int i = 0; i< DataStore.USER_BOOKMARK_LIMIT; i++){
//            int typeOffSet = (int)(Math.random()*DataStore.BOOKMARK_TYPES_COUNT);
//            int bookmarkOffSet = (int)(Math.random()*DataStore.BOOKMARK_COUNT_PER_TYPE);
//            Bookmark bookmark = bookmarks[typeOffSet][bookmarkOffSet];
//            BookmarkController.getInstance().saveUserBookmark(user, bookmark);
//            System.out.println(bookmark);
//        }
//    }

}
