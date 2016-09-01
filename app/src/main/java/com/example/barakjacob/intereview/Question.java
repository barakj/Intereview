package com.example.barakjacob.intereview;

/**
 * A class that represents a certain multiple choice Question
 * Consists of the actual question, the possible answers to that question, and the answer to that question
 */
public class Question {
    private String question;
    private String[] possibleAnswers;
    private String answer;

    /**
     * Constructs a Question object with a certain question, possible answers array, and answer
     * @param theQuestion the actual question
     * @param possible an array of the possible answers to that question
     * @param theAnswer the answer to that question(an index)
     */
    public  Question(String theQuestion,String[]possible,String theAnswer){
        this.question = theQuestion;
        this.possibleAnswers = possible;
        this.answer = theAnswer;
    }

    /**
     * Gets the question of a certain Question object
     * @return the question
     */
    public String getQuestion(){
        return this.question;
    }

    /**
     * Gets the possible answers to a certain question
     * @return an array of possible answers
     */
    public String[] getPossibleAnswers(){
        return this.possibleAnswers;
    }

    /**
     * Gets the answer of a certain topic
     * @return the answer
     */
    public String getAnswer(){
        return this.answer;
    }
}
