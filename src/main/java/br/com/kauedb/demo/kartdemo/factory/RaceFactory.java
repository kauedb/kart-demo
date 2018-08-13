package br.com.kauedb.demo.kartdemo.factory;

import br.com.kauedb.demo.kartdemo.model.Lap;
import br.com.kauedb.demo.kartdemo.model.Pilot;
import br.com.kauedb.demo.kartdemo.model.Race;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável por criar Race a partir de um arquivo de log.
 */
@Component
@Slf4j
public class RaceFactory {

    /**
     * Expressão regular de cada linha do log da corrida
     */
    private final Pattern lapPattern = Pattern.compile("(\\d\\d:\\d\\d:\\d\\d.\\d{3}).+(\\d{3}) – (\\S+).+(\\d).+(\\d):(\\d\\d).(\\d{3}).+(\\d\\d,\\d{2,3})");

    /**
     * Formato do decimal usado no Brasil
     */
    private final DecimalFormat decimalFormatBR = new DecimalFormat();

    /**
     * Formato da hora do log
     */
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public RaceFactory() {
        final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatBR.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    /**
     * A partir de um arquivo de log de corrida cria um objeto corrida.
     *
     * @param raceLogFile Nome do arquivo de log da corrida
     * @return uma corrida Race
     * @throws ParseException Se não for possível tranformar o valor decimal em double
     */
    public Race createRace(String raceLogFile) throws ParseException {
        final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(raceLogFile);

        final Scanner scanner = new Scanner(resourceAsStream);

        final List<Lap> laps = new ArrayList<>();
        while (scanner.hasNextLine()) {

            final String line = scanner.nextLine();
            final Matcher matcher = lapPattern.matcher(line);
            if (matcher.find()) {

                final Lap lap = Lap.builder()
                        .hour(LocalTime.parse(matcher.group(1), dateTimeFormatter))
                        .pilot(Pilot.builder()
                                .code(matcher.group(2))
                                .name(matcher.group(3))
                                .build())
                        .number(Integer.valueOf(matcher.group(4)))
                        .time(Duration.ofMinutes(Integer.valueOf(matcher.group(5)))
                                .plus(Duration.ofSeconds(Integer.valueOf(matcher.group(6))))
                                .plus(Duration.ofMillis(Integer.valueOf(matcher.group(7)))))
                        .averageVelocity(decimalFormatBR.parse(matcher.group(8)).doubleValue())
                        .build();

                log.debug("lap={}", lap);

                laps.add(lap);
            }


        }

        return Race.builder().laps(laps).build();
    }

}
