package br.com.kauedb.demo.kartdemo.model;

import com.google.common.collect.Streams;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Classe principal responsável por todos os dados da corrida.
 */
@Value
@Builder
@Slf4j
public class Race {

    List<Lap> laps;
    Lap defaultLap = Lap.builder().build();

    /**
     * Recuperar a duração da corrida.
     *
     * @param chronoUnit Unidade de tempo para calcular a duração da corrida
     * @return duração da corrida
     */
    public long getDurationIn(ChronoUnit chronoUnit) {

        val min = laps.parallelStream().min(comparing(Lap::getHour))
                .orElse(Lap.builder().build()).getHour();
        log.debug("min={}", min);

        val max = laps.parallelStream().max(comparing(Lap::getHour))
                .orElse(Lap.builder().build()).getHour();
        log.debug("max={}", max);

        return chronoUnit.between(min, max);
    }

    /**
     * Recuperar as posições de chegada do corredores.
     *
     * @return as posições dos corredores em uma lista
     */
    public List<Position> getPositions() {
        val pilotsLastLap = laps.parallelStream()
                .collect(groupingBy(lap -> lap.getPilot().getCode(), maxBy(comparing(Lap::getNumber))));
        log.debug("pilotsLastLap={}", pilotsLastLap);

        val lastLap = pilotsLastLap.values();
        log.debug("lastLap={}", lastLap);

        val championTime = lastLap.parallelStream().min(comparing(lap -> lap.orElse(defaultLap).getHour()))
                .orElse(Optional.of(defaultLap)).orElse(defaultLap).getHour();
        log.debug("championTime={}", championTime);

        val pilotsLastLapPosition = lastLap.parallelStream()
                .sorted(comparing(lap -> lap.orElse(defaultLap).getHour()));
        log.debug("pilotsLastLapPosition={}", pilotsLastLapPosition);

        return Streams.mapWithIndex(pilotsLastLapPosition, (from, index) ->
                Position.builder()
                        .number(Long.valueOf(index + 1).intValue())
                        .pilot(from.orElse(defaultLap).getPilot())
                        .laps(from.orElse(defaultLap).getNumber())
                        .totalRaceTimeInSeconds(getDurationIn(ChronoUnit.SECONDS))
                        .championTimeDiffInMillis(ChronoUnit.MILLIS.between(championTime, from.orElse(defaultLap).getHour()))
                        .build()
        ).collect(toList());
    }

    /**
     * Recuperar a melhor volta da corrida.
     *
     * @return a melhor volta da corrida
     */
    public Lap getBestLap() {
        return laps.parallelStream()
                .min(comparing(Lap::getTime))
                .orElse(defaultLap);
    }

    /**
     * Recuperar a melhor volta de um piloto dessa corrida.
     *
     * @param code Código do piloto
     * @return a melhor volta de um piloto dessa corrida
     */
    public Lap getPilotBestLap(String code) {
        return laps.parallelStream()
                .collect(groupingBy(lap -> lap.getPilot().getCode(), minBy(comparing(Lap::getTime))))
                .get(code).orElse(defaultLap);
    }

    /**
     * Recuperar a velocidade média de um piloto dessa corrida.
     *
     * @param code Código do piloto
     * @return a velocidade média de um piloto dessa corrida
     */
    public Double getPilotAverageVelocity(String code) {
        return laps.parallelStream()
                .collect(groupingBy(lap -> lap.getPilot().getCode(), averagingDouble(Lap::getAverageVelocity)))
                .get(code);
    }

    /**
     * Recuperar a diferença de tempo entre o piloto e o campeão.
     *
     * @param code Código do piloto
     * @return a diferença de tempo entre o piloto e o campeão
     */
    public long getTimeAfterChampionInMillis(String code) {
        return getPositions().parallelStream()
                .collect(groupingBy(position -> position.getPilot().getCode()))
                .get(code).iterator().next().getChampionTimeDiffInMillis();
    }
}
