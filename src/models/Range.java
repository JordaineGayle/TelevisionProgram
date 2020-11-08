package models;

public class Range
{
    int Min;
    int Max;

    public Range() {
    }

    public Range(int min, int max) {
        Min = min;
        Max = max;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        Max = max;
    }
}
