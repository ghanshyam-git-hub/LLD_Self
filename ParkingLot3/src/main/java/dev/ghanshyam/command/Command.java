package dev.ghanshyam.command;

import dev.ghanshyam.model.Vehicle;

public interface Command<T,U> {
     T execute(U u);
}
