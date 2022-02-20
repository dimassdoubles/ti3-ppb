package com.dimassdoubles.wordlistsqlineractive;

public class WordItem {
    private int Id;
    private String Word;

    public WordItem(int id, String word) {
        Id = id;
        Word = word;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }
}
