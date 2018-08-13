package br.com.kauedb.demo.kartdemo.model;

import lombok.Builder;
import lombok.Value;

/**
 * Classe responsável pelo dados do piloto da corrida.
 */
@Value
@Builder
public class Pilot {
    String code;
    String name;
}
