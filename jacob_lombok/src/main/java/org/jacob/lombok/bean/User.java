package org.jacob.lombok.bean;

import lombok.Data;

import javax.swing.plaf.synth.SynthSpinnerUI;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
