package com.home.atm6;

public class Function {
    String name;
    int icon;

    // Alt+Insert
    public Function(String name) {
        this.name = name;
    }

    public Function(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
