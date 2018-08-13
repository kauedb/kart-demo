package br.com.kauedb.demo.kartdemo.model;

import lombok.Builder;
import lombok.Value;

/**
 * Classe responsável pelo dados da posição de cada piloto na corrida.
 */
@Value
@Builder
public class Position {
    int number;
    Pilot pilot;
    int laps;
    long totalRaceTimeInSeconds;
    long championTimeDiffInMillis;
}
