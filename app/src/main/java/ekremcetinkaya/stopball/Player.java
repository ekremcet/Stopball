package ekremcetinkaya.stopball;

public class Player {
    int score;
    int yellowCard;
    int redCard;

    public void Player(){
        this.score = 0;
        this.yellowCard = 0;
        this.redCard = 0;
    }

    public void score(){
        this.score++;
    }

    public void showYellow(){
        this.yellowCard++;
        if(this.yellowCard == 2){
            yellowCard = 0;
            redCard++;
        }
    }

    public void showRed(){
        this.redCard++;
    }

    public void reset(){
        this.score = 0;
        this.yellowCard = 0;
    }
}
