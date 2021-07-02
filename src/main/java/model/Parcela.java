package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "valor", nullable = false)
    private Double valorParcela;

    @Column(nullable = false)
    private LocalDate data;

    private LocalDate dateVencimento;

}
