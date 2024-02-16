package GDNTiendas.GDNTIENDAS.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tiendas")
@Data
public class Tiendas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int nro_tienda;
    private String ip_tienda;
    private String tipo;
    private String provincia;
    private String direccion;
}