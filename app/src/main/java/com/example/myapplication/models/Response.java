package com.example.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Response {
    @SerializedName("pets")
    @Expose
    private List<Pet> pets = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Response() {
    }

    /**
     *
     * @param pets
     */
    public Response(List<Pet> pets) {
        super();
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
