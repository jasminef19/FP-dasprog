public class Player {
    private String userName;
    private int position;
    private int birthDate;
    private int ladderCount;
    private boolean hitSnakeFirstTime;

    public Player(String userName, int birthDate) {
        this.userName = userName;
        this.position = 0;
        this.birthDate = birthDate;
        this.hitSnakeFirstTime = false;
        this.ladderCount = 0;
    }

    // getters dan setters untuk hitSnakeFirstTime
    public boolean isHitSnakeFirstTime() {
        return hitSnakeFirstTime;
    }

    public void setHitSnakeFirstTime(boolean hitSnakeFirstTime) {
        this.hitSnakeFirstTime = hitSnakeFirstTime;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLadderCount (int ladderCount) {
        this.ladderCount = ladderCount;
    }


    public String getUserName() {
        return this.userName;
    }

    public int getPosition() {
        return this.position;
    }

    public int getBirthDate() {
        return this.birthDate;
    }

    public int getLadderCount() {
        return this.ladderCount;
    }


    public int rollDice() {
        int roll = (int) (Math.random() * 6) + 1;
        if (ladderCount >= 2) {
            roll = Math.max(1, roll - 2); // subtract 2, but ensure the roll is at least 1
        }
        return roll;
    }

    public void moveAround(int x) {
        if (this.position + x > 100) {
            this.position = 100 - (this.position + x) % 100;
        } else {
            this.position += x;
        }

    }

    public int getCurrentPosition() {
        return getPosition();
    }
}