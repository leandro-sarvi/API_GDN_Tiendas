package GDNTiendas.GDNTIENDAS.mapper.Impl;

import GDNTiendas.GDNTIENDAS.dto.NroIpTienda;
import GDNTiendas.GDNTIENDAS.mapper.IMapperNroIpTienda;
import GDNTiendas.GDNTIENDAS.persistence.entity.Tiendas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperNroIpTiendaImpl implements IMapperNroIpTienda<List<Tiendas>, NroIpTienda> {


    @Override
    public List<NroIpTienda> mapNroIpTienda(List<Tiendas> in) {
        List<NroIpTienda> nroIpTiendaList = new ArrayList<>();
        in.forEach(tiendas -> {
            NroIpTienda nroIpTienda = new NroIpTienda(tiendas.getNro_tienda(), tiendas.getIp_tienda());
            nroIpTiendaList.add(nroIpTienda);
        });
        return nroIpTiendaList;
    }
}
