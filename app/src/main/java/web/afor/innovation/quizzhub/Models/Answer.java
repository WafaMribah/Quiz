package web.afor.innovation.quizzhub.Models;

/**
 * Created by wafa on 7/27/16.
 */
public class Answer {
    private String response;
    private boolean isValidAnswer;
    boolean clicked ;

    public Answer(String response, boolean isValidAnswer,boolean clicked) {
        this.response = response;
        this.isValidAnswer = isValidAnswer;
        this.clicked=clicked;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isValidAnswer() {
        return isValidAnswer;
    }

    public void setValidAnswer(boolean validAnswer) {
        isValidAnswer = validAnswer;
    }
}
