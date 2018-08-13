package br.com.kauedb.demo.kartdemo;

import br.com.kauedb.demo.kartdemo.factory.RaceFactory;
import br.com.kauedb.demo.kartdemo.model.Lap;
import br.com.kauedb.demo.kartdemo.model.Position;
import br.com.kauedb.demo.kartdemo.model.Race;
import org.junit.Before;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Esse test foi feito para os seguintes dados:
 * <p>
 * Hora                               Piloto             Nº Volta   Tempo Volta       Velocidade média da volta
 * 23:49:08.277      038 – F.MASSA                           1		1:02.852                        44,275
 * 23:49:10.858      033 – R.BARRICHELLO                     1		1:04.352                        43,243
 * 23:49:11.075      002 – K.RAIKKONEN                       1      1:04.108                        43,408
 * 23:49:12.667      023 – M.WEBBER                          1		1:04.414                        43,202
 * 23:49:30.976      015 – F.ALONSO                          1		1:18.456			            35,47
 * 23:50:11.447      038 – F.MASSA                           2		1:03.170                        44,053
 * 23:50:14.860      033 – R.BARRICHELLO                     2		1:04.002                        43,48
 * 23:50:15.057      002 – K.RAIKKONEN                       2      1:03.982                        43,493
 * 23:50:17.472      023 – M.WEBBER                          2		1:04.805                        42,941
 * 23:50:37.987      015 – F.ALONSO                          2		1:07.011			            41,528
 * 23:51:14.216      038 – F.MASSA                           3		1:02.769                        44,334
 * 23:51:18.576      033 – R.BARRICHELLO		             3		1:03.716                        43,675
 * 23:51:19.044      002 – K.RAIKKONEN                       3		1:03.987                        43,49
 * 23:51:21.759      023 – M.WEBBER                          3		1:04.287                        43,287
 * 23:51:46.691      015 – F.ALONSO                          3		1:08.704			            40,504
 * 23:52:01.796      011 – S.VETTEL                          1		3:31.315			            13,169
 * 23:52:17.003      038 – F.MASS                            4		1:02.787                        44,321
 * 23:52:22.586      033 – R.BARRICHELLO		             4		1:04.010                        43,474
 * 23:52:22.120      002 – K.RAIKKONEN                       4		1:03.076                        44,118
 * 23:52:25.975      023 – M.WEBBER                          4		1:04.216                        43,335
 * 23:53:06.741      015 – F.ALONSO                          4		1:20.050			            34,763
 * 23:53:39.660      011 – S.VETTEL                          2		1:37.864			            28,435
 * 23:54:57.757      011 – S.VETTEL                          3		1:18.097			            35,633
 */
public class RaceTest {

    private Race race;

    @Before
    public void setup() throws Exception {
        this.race = new RaceFactory().createRace("race.log");
    }


    @Test
    public void shouldCreateRace() {
        assertNotNull(race);
        assertFalse(race.getLaps().isEmpty());
        assertEquals(23, race.getLaps().size());
    }

    @Test
    public void shouldGetRaceTotalTime() {
        final long totalTime = race.getDurationIn(ChronoUnit.MINUTES);

        assertEquals(5, totalTime);
    }

    // A partir de um input de um arquivo de log do formato acima, montar o resultado da corrida com as seguintes informações:
    // Posição Chegada, Código Piloto, Nome Piloto, Qtde Voltas Completadas e Tempo Total de Prova.
    @Test
    public void shouldGetPositionAndPilotCodeAndPilotNameAndCompletedLapsAndRaceTotalTime() {

        final List<Position> positions = race.getPositions();

        assertNotNull(positions);
        assertFalse(positions.isEmpty());
        assertEquals(6, positions.size());

        assertEquals(1, positions.get(0).getNumber());
        assertEquals("038", positions.get(0).getPilot().getCode());
        assertEquals("F.MASS", positions.get(0).getPilot().getName());
        assertEquals(4, positions.get(0).getLaps());
        assertEquals(349, positions.get(0).getTotalRaceTimeInSeconds());

        assertEquals(2, positions.get(1).getNumber());
        assertEquals("002", positions.get(1).getPilot().getCode());
        assertEquals("K.RAIKKONEN", positions.get(1).getPilot().getName());
        assertEquals(4, positions.get(1).getLaps());
        assertEquals(349, positions.get(1).getTotalRaceTimeInSeconds());

        assertEquals(3, positions.get(2).getNumber());
        assertEquals("033", positions.get(2).getPilot().getCode());
        assertEquals("R.BARRICHELLO", positions.get(2).getPilot().getName());
        assertEquals(4, positions.get(2).getLaps());
        assertEquals(349, positions.get(2).getTotalRaceTimeInSeconds());

        assertEquals(4, positions.get(3).getNumber());
        assertEquals("023", positions.get(3).getPilot().getCode());
        assertEquals("M.WEBBER", positions.get(3).getPilot().getName());
        assertEquals(4, positions.get(3).getLaps());
        assertEquals(349, positions.get(3).getTotalRaceTimeInSeconds());

        assertEquals(5, positions.get(4).getNumber());
        assertEquals("015", positions.get(4).getPilot().getCode());
        assertEquals("F.ALONSO", positions.get(4).getPilot().getName());
        assertEquals(4, positions.get(4).getLaps());
        assertEquals(349, positions.get(4).getTotalRaceTimeInSeconds());

        assertEquals(6, positions.get(5).getNumber());
        assertEquals("011", positions.get(5).getPilot().getCode());
        assertEquals("S.VETTEL", positions.get(5).getPilot().getName());
        assertEquals(3, positions.get(5).getLaps());
        assertEquals(349, positions.get(5).getTotalRaceTimeInSeconds());
    }

    // Descobrir a melhor volta de cada piloto
    @Test
    public void shouldGetPilotBestLap() {

        final Lap pilot038BestLap = race.getPilotBestLap("038");

        assertEquals(62, pilot038BestLap.getTime().getSeconds());
        assertEquals("038", pilot038BestLap.getPilot().getCode());
        assertEquals("F.MASSA", pilot038BestLap.getPilot().getName());
        assertEquals(3, pilot038BestLap.getNumber());

        final Lap pilot011BestLap = race.getPilotBestLap("011");

        assertEquals(78, pilot011BestLap.getTime().getSeconds());
        assertEquals("011", pilot011BestLap.getPilot().getCode());
        assertEquals("S.VETTEL", pilot011BestLap.getPilot().getName());
        assertEquals(3, pilot011BestLap.getNumber());
    }

    // Descobrir a melhor volta da corrida
    @Test
    public void shouldGetRaceBestLap() {
        final Lap bestLap = race.getBestLap();

        assertEquals(62, bestLap.getTime().getSeconds());
        assertEquals("038", bestLap.getPilot().getCode());
        assertEquals("F.MASSA", bestLap.getPilot().getName());
        assertEquals(3, bestLap.getNumber());
    }

    // Calcular a velocidade média de cada piloto durante toda corrida
    @Test
    public void shouldGetPilotRaceAverageVelocity() {
        assertEquals(44.24575, race.getPilotAverageVelocity("038"), 0);

        assertEquals(43.468, race.getPilotAverageVelocity("033"), 0);

        assertEquals(25.745666666666676, race.getPilotAverageVelocity("011"), 0);
    }

    // Descobrir quanto tempo cada piloto chegou após o vencedor
    @Test
    public void shouldGetTimeAfterChampion() {

        assertEquals(0, race.getTimeAfterChampionInMillis("038"));

        assertEquals(5117, race.getTimeAfterChampionInMillis("002"));

        assertEquals(5583, race.getTimeAfterChampionInMillis("033"));

        assertEquals(8972, race.getTimeAfterChampionInMillis("023"));

        assertEquals(49738, race.getTimeAfterChampionInMillis("015"));

        assertEquals(160754, race.getTimeAfterChampionInMillis("011"));

    }
}