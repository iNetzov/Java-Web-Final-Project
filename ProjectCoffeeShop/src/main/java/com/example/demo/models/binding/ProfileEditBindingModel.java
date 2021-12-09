package com.example.demo.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfileEditBindingModel {
    private String fullName;

    public ProfileEditBindingModel() {
    }

    @NotNull
    @Size(min = 3,max = 20)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
