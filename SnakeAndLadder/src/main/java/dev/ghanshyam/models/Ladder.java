package dev.ghanshyam.models;

import java.util.Objects;

public class Ladder {
    int start;
    int end;

    public Ladder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ladder ladder = (Ladder) o;
        return start == ladder.start && end == ladder.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Ladder{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
