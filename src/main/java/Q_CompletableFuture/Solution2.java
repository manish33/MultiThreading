package Q_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Simple, runnable demo: thenApply vs thenCompose.
 * Scenario: First load a UserInfo asynchronously, then (depending on that) load a UserRating asynchronously.
 * Both loaders return CompletableFuture, so choosing thenApply vs thenCompose matters.
 */
public class Solution2 {

    // Tiny model types
    static class UserInfo {
        final int id;
        UserInfo(int id) { this.id = id; }
        @Override public String toString() { return "UserInfo{" + id + '}'; }
    }
    static class UserRating {
        final int score;
        UserRating(int score) { this.score = score; }
        @Override public String toString() { return "UserRating{" + score + '}'; }
    }

    // Async loaders (mocked)
    static CompletableFuture<UserInfo> getUserInfo(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(150);
            return new UserInfo(userId);
        });
    }
    static CompletableFuture<UserRating> getUserRating(UserInfo info) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(120);
            // pretend rating depends on user id
            return new UserRating(100 + info.id);
        });
    }

    public static void main(String[] args) {
        // Step 1: start the first async call
        CompletableFuture<UserInfo> userInfo = getUserInfo(24);

        // Using thenApply: returns a nested future (CompletableFuture<CompletableFuture<UserRating>>)
        CompletableFuture<CompletableFuture<UserRating>> nested = userInfo.thenApply(info -> getUserRating(info));
        // To get the rating value, you'd need an extra join/get:
        UserRating ratingViaApply = nested.join().join(); // double-join because of nesting
        System.out.println("thenApply produced nested future -> " + ratingViaApply);

        // Using thenCompose: flattens the nested future to CompletableFuture<UserRating>
        CompletableFuture<UserRating> flat = userInfo.thenCompose(info -> getUserRating(info));
        UserRating ratingViaCompose = flat.join(); // single join
        System.out.println("thenCompose flattened future -> " + ratingViaCompose);

        // Summary (easy terms):
        // - thenApply: use when your function returns a plain value (e.g., mapping). If it returns a future,
        //   you get a nested future and need extra steps.
        // - thenCompose: use when your function returns a CompletableFuture. It "flatMaps" (flattens) the extra level,
        //   so you can continue the chain naturally with a single future.
    }

    private static void sleep(long ms) {
        try { TimeUnit.MILLISECONDS.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
