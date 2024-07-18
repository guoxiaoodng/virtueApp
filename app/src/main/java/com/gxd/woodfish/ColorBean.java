package com.gxd.woodfish;

public class ColorBean {
    private String colorName;
    private int colorTint;

    public ColorBean(String colorName, int colorTint) {
        this.colorName = colorName;
        this.colorTint = colorTint;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getColorTint() {
        return colorTint;
    }

    public void setColorTint(int colorTint) {
        this.colorTint = colorTint;
    }
}
