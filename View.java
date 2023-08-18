import constants.KidFriendlyStatus;
import constants.UserType;
import controllers.BookmarkController;
import data.DataStore;
import entities.Bookmark;
import entities.User;
import partner.Shareable;

public class View {

    public static void browse(User user, Bookmark[][] bookmarks){
        System.out.println("\n"+user.getEmail()+" is browsing");
        int bookmarkCount = 0;
        for (Bookmark[] bookmarkList : bookmarks){
            for (Bookmark bookmark : bookmarkList){
                //Bookmarking
                if(bookmarkCount < DataStore.USER_BOOKMARK_LIMIT){
                    boolean isBookmarked = getBookmarkDecision(bookmark);
                    if(isBookmarked){
                        bookmarkCount++;
                        BookmarkController.getInstance().saveUserBookmark(user, bookmark);
                        System.out.println("New item bookmarked: "+bookmark);
                    }
                    //Mark as kid-friendly
                    if(user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)){
                        if(bookmark.isKidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)){
                            String kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                            if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)){
                                BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
                            }
                        }
                        //SHAREABLE
                        if(bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) && bookmark instanceof Shareable){
                            boolean isShared = getShareDecision();
                            if(isShared){
                                BookmarkController.getInstance().share(user, bookmark);
                            }
                        }
                    }
                }
            }
        }


    }

    private static boolean getShareDecision() {
        return Math.random() < 0.5 ? true : false;
    }

    private static String getKidFriendlyStatusDecision(Bookmark bookmark) {
        int desicion = (int) (Math.random() * 3);
        String status;
        switch (desicion){
            case 0: status =  KidFriendlyStatus.APPROVED;
            break;
            case 1: status =  KidFriendlyStatus.REJECTED;
            break;
            default: status =  KidFriendlyStatus.UNKNOWN;
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
