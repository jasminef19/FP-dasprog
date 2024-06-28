import java.util.ArrayList;

class SnakeAndLadder {
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int boardSize;
    private int status;
    private int playerInTurn;

    public SnakeAndLadder(int boardSize) {
        this.boardSize = boardSize;
        this.players = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.status = 0;
    }

    public void initiateGame() {
        int[][] ladders = new int[][]{{2, 23}, {8, 34}, {20, 77}, {32, 68}, {41, 79}, {74, 88}, {82, 100}, {85, 95}};
        this.addLadders(ladders);
        int[][] snakes = new int[][]{{29, 9}, {38, 15}, {47, 5}, {53, 33}, {62, 37}, {86, 54}, {92, 70}, {97, 25}};
        this.addSnakes(snakes);
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPlayerInTurn(int p) {
        this.playerInTurn = p;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void addSnake(Snake s) {
        this.snakes.add(s);
    }

    public void addSnakes(int[][] s) {
        for (int[] snakePos : s) {
            Snake snake = new Snake(snakePos[0], snakePos[1]);
            this.snakes.add(snake);
        }
    }

    public void addLadder(Ladder l) {
        this.ladders.add(l);
    }

    public void addLadders(int[][] l) {
        for (int[] ladderPos : l) {
            Ladder ladder = new Ladder(ladderPos[0], ladderPos[1]);
            this.ladders.add(ladder);
        }
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public int getStatus() {
        return this.status;
    }

    public int getPlayerInTurn() {
        return this.playerInTurn;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Snake> getSnakes() {
        return this.snakes;
    }

    public ArrayList<Ladder> getLadders() {
        return this.ladders;
    }

    public int getTurn() {
        if (this.status == 0) {
            double r = Math.random();
            return r < 0.5 ? 0 : 1;
        } else {
            return this.playerInTurn == 0 ? 1 : 0;
        }
    }

    public void movePlayer(Player p, int x) {
        boolean extraTurn = false;

        // Periksa jika pemain mendapat dadu bernilai 6
        if (x == 6) {
            extraTurn = true;
        }

        int effectiveRoll = x;
        if (p.getLadderCount() >= 2) {
            effectiveRoll = Math.max(1, x - 2); // apply ladder bonus
        }

        p.moveAround(effectiveRoll);
        System.out.println("The new position of " + p.getUserName() + " is: " + p.getPosition());

        for (Ladder l : this.ladders) {
            if (p.getPosition() == l.getFromPosition()) {
                p.setPosition(l.getToPosition());
                System.out.println(p.getUserName() + " got ladder from " + l.getFromPosition() + " climb to " + l.getToPosition());
                p.setLadderCount(p.getLadderCount() + 1);
            }
        }

        for (Snake s : this.snakes) {
            if (p.getPosition() == s.getFromPosition()) {
                // Jika pemain terkena ular untuk pertama kali
                if (!p.isHitSnakeFirstTime()) {
                    p.setPosition(0);
                    p.setHitSnakeFirstTime(true);
                    System.out.println(p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to start!");
                } else {
                    p.setPosition(s.getToPosition());
                    System.out.println(p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to " + s.getToPosition());
                }
            }
        }

        //Fitur
        int birthDate = p.getBirthDate();
        int specialPosition = birthDate * 24 % 100;
        if (p.getPosition() % specialPosition == 0) {
            System.out.println(p.getUserName() + " landed on a multiple of " + specialPosition + " and gets to roll again!");
            int extraRoll = p.rollDice();
            System.out.println(p.getUserName() + " rolls an extra dice and gets " + extraRoll);
            p.moveAround(extraRoll);
            System.out.println(p.getUserName() + " new position after extra roll is " + p.getPosition());
        }

        System.out.println(p.getUserName() + " new position is " + p.getPosition());

        if (p.getPosition() >= 100) {
            this.status = 2;
            System.out.println("Game Over! " + p.getUserName() + " wins the game!");
        } else if (extraTurn) {
            System.out.println(p.getUserName() + " rolled a 6 and gets another turn!");
            int extraRoll = p.rollDice();
            System.out.println(p.getUserName() + " rolls again and gets " + extraRoll);
            movePlayer(p, extraRoll);
        } else if (p.getLadderCount() >= 2) {
            p.setLadderCount(0);
            int reducedRoll = p.rollDice() - 2;
            if (reducedRoll < 1) {
                reducedRoll = 1;
            }
            System.out.println(p.getUserName() + " had ladder 2 times, next turn the dice is reduced by 2. " + p.getUserName() + " rolls a " + reducedRoll);
            movePlayer(p, reducedRoll);
        }
    }
}