package com.example.barakjacob.intereview;

/**
 * A class that represents a certain topic in computer science
 * consists of the category in computer science of that Topic, the title/subcategory and the description
 */
public class Topic {
    private String category;
    private String title;
    private String description;

    /**
     * Constructs a Topic object with a certain category, title, and description
     * @param cat the category (data structures, sorts, etc)
     * @param tit the title (hash tables, trees)
     * @param des the description of each topic
     */
    public Topic(String cat, String tit, String des){
        this.category = cat;
        this.title = tit;
        this.description = des;
    }

    /**
     * Gets the title of a certain topic
     * @return the title of that Topic
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Gets the description of a certain topic
     * @return the description of that Topic
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Gets the category of a certain topic
     * @return the category of that Topic
     */
    public String getCategory(){
        return this.category;
    }
}
