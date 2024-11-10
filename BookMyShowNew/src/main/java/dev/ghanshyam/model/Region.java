package dev.ghanshyam.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
public class Region {
    private String regionName;
    private List<Theatre>theatreList;
}
