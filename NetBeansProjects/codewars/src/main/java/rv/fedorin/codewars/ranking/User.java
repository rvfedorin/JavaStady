/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars.ranking;

/**
 *
 * @author R. V. Fedorin
 */
public class User {

    public int rank;
    public int progress;

    public User() {
        rank = -8;
        progress = 0;
    }

    public void incProgress(int finishedLevel) throws IllegalArgumentException {
        if (finishedLevel < -8 || finishedLevel > 8 || finishedLevel == 0) {
            throw new IllegalArgumentException();
        }
        if (rank == 8) {
            return;
        }

        if (finishedLevel == rank) {
            progress += 3;
        } else {
            int diff = getDif(finishedLevel);
            increaseProgress(diff);
        }

    }

    private int getDif(int finishedLevel) {
        int diff = 0;
        if (finishedLevel > rank) {
            if (finishedLevel > 0 && rank < 0) {
                diff = finishedLevel;
                finishedLevel = -1;
            }
            diff += Math.abs(Math.abs(finishedLevel) - Math.abs(rank));
            diff = 0 - diff; // to show that finishedLevel more than rank
        } else {
            if (finishedLevel < 0 && rank > 0) {
                diff = Math.abs(finishedLevel) + rank - 1;
            } else {
                diff = Math.abs(rank) - Math.abs(finishedLevel);
            }
        }
        System.out.println("[] diff " + diff);
        return diff;
    }

    private void increaseProgress(int diff) {
        int absDiff = Math.abs(diff);
        if (diff < 0) {
            progress += 10 * (absDiff * absDiff);
        } else if (diff == 1) {
            progress += 1;
        }
        clalculateRank();
    }

    private void clalculateRank() {
        while (progress >= 100 && rank != 8) {
            progress -= 100;
            rank++;
            if (rank == 0) {
                rank++;
            }
        }
        if(rank == 8) {
            progress = 0;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.incProgress(2);
        System.out.println("user.rank= " + user.rank); // -2
        System.out.println("user.progress= " + user.progress);

//        System.out.println("user.rank= " + user.rank); // => -8
//        System.out.println("user.progress= " + user.progress); // => 0
//        user.incProgress(-7);
//        System.out.println("user.progress= " + user.progress); // => 10
//        user.incProgress(-5); // will add 90 progress
//        System.out.println("user.rank= " + user.rank);  // => -7 // rank was upgraded to -7
//        System.out.println("user.progress= " + user.progress); // => 0 // progress is now zero
    }
}
