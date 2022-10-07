package com.prajwal.catFacts.model;
/*
{"id":38,"type":"general","setup":"I'm reading a book about anti-gravity...","punchline":"It's impossible to put down"}
 */
public class Joke {
    private int id;
    private String type;
    private String setup;
    private String punchline;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }
}
