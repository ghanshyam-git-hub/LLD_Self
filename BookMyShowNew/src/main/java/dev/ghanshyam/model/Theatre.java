package dev.ghanshyam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Theatre {
   private int theatre_id;
   @Setter
   List<Screen> screenList;
}
