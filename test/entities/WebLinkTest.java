package entities;

import managers.BookmarkManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebLinkTest {
    @Test
    public void testIsKidFriendlyEligible(){
        // Test 1: porn in url -- false
        WebLink webLink =  BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html", "http://www.javaworld.com");
        boolean isKidFriendiEligible = webLink.isKidFriendlyEligible();
        assertEquals(false, isKidFriendiEligible, "porn in url -- false");
        // Test 2: porn in title -- false
         webLink =  BookmarkManager.getInstance().createWebLink(2000, "Taming porn, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com");
         isKidFriendiEligible = webLink.isKidFriendlyEligible();
        assertEquals(false, isKidFriendiEligible, "porn in title -- false");
        // Test 3: adult in host -- false
        webLink =  BookmarkManager.getInstance().createWebLink(2000, "Taming tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.adult.com");
        isKidFriendiEligible = webLink.isKidFriendlyEligible();
        assertEquals(false, isKidFriendiEligible, "adult in host -- false");
        // Test 4: adult in url, but not in host part -- true
        webLink =  BookmarkManager.getInstance().createWebLink(2000, "Taming tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html", "http://www.javaworld.com");
        isKidFriendiEligible = webLink.isKidFriendlyEligible();
        assertEquals(true, isKidFriendiEligible, "adult in url, but not in host part -- true");
        // Test 5: adult in title only -- true
        webLink =  BookmarkManager.getInstance().createWebLink(2000, "Taming adult, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com");
        isKidFriendiEligible = webLink.isKidFriendlyEligible();
        assertEquals(true, isKidFriendiEligible, "adult in url, but not in host part -- true");
    }
}