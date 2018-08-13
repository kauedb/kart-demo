package br.com.kauedb.demo.kartdemo.shell;

import br.com.kauedb.demo.kartdemo.factory.RaceFactory;
import br.com.kauedb.demo.kartdemo.model.Lap;
import br.com.kauedb.demo.kartdemo.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ShellComponent
public class RaceShell {

    private final RaceFactory raceFactory;

    @Autowired
    public RaceShell(RaceFactory raceFactory) {
        this.raceFactory = raceFactory;
    }

    @ShellMethod("Criar corrida a partir do log")
    public String race(@ShellOption(defaultValue = "race.log") String log) throws ParseException {
        return raceFactory.createRace(log).toString();
    }

    @ShellMethod("Mostrar as posições dos pilotos da corrida")
    public List<Position> positions(@ShellOption(defaultValue = "race.log") String log) throws ParseException {
        return raceFactory.createRace(log).getPositions();
    }

    @ShellMethod(value = "Mostrar a melhor volta de um piloto da corrida", key = "best lap")
    public Lap bestLap(@ShellOption(defaultValue = "race.log") String log, @ShellOption(defaultValue = "") String code) throws ParseException {

        if ("".equals(code))
            return raceFactory.createRace(log).getBestLap();

        return raceFactory.createRace(log).getPilotBestLap(code);
    }

    @ShellMethod(value = "Mostrar a duração da corrida")
    public long duration(@ShellOption(defaultValue = "race.log") String log, @ShellOption(defaultValue = "MINUTES") ChronoUnit in) throws ParseException {
        return raceFactory.createRace(log).getDurationIn(in);
    }

    @ShellMethod(value = "Mostrar a velocidade média do piloto", key = "pilot velocity")
    public Double pilotAverageVelocity(@ShellOption(defaultValue = "race.log") String log, @ShellOption String code) throws ParseException {
        return raceFactory.createRace(log).getPilotAverageVelocity(code);
    }

    @ShellMethod(value = "Mostrar o tempo que o piloto chegou após o vencedor", key = "champion diff")
    public long pilotTimeAfterChampion(@ShellOption(defaultValue = "race.log") String log, @ShellOption String code) throws ParseException {
        return raceFactory.createRace(log).getTimeAfterChampionInMillis(code);
    }
}
