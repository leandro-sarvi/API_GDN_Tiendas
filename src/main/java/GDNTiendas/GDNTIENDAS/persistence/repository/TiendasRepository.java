package GDNTiendas.GDNTIENDAS.persistence.repository;

import GDNTiendas.GDNTIENDAS.dto.NroIpTienda;
import GDNTiendas.GDNTIENDAS.persistence.entity.Tiendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TiendasRepository extends JpaRepository<Tiendas, Long> {
    @Query(value = "SELECT * FROM tiendas WHERE nro_tienda=:nro_tienda",nativeQuery = true)
    public Tiendas findByNroTienda(@Param("nro_tienda") int nro_tienda);
    @Modifying
    @Query(value = "DELETE FROM tiendas WHERE nro_tienda=:nro_tienda",nativeQuery = true)
    public void deleteByNroTienda(@Param("nro_tienda") int nro_tienda);
}
