package dev.ghanshyam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class Screen {
    int screen_id;
    private List<Show>showList;
}
