package com.trello;

public class Data {

    private Board board;
    private Card card;
    private List listAfter;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    
    public List getListAfter() {
        return listAfter;
    }

    public void setListAfter(List listAfter) {
        this.listAfter = listAfter;
    }

}