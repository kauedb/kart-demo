package br.com.kauedb.demo.kartdemo.model;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Volta da corrida.
 */
@Value
@Builder
public class Lap {
    LocalTime hour;
    Pilot pilot;
    int number;
    Duration time;
    double averageVelocity;
}
