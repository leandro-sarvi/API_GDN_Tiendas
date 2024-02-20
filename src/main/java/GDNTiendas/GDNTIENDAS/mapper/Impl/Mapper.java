package GDNTiendas.GDNTIENDAS.mapper.Impl;

import GDNTiendas.GDNTIENDAS.dto.TiendasDTO;
import GDNTiendas.GDNTIENDAS.mapper.IMapper;
import GDNTiendas.GDNTIENDAS.persistence.entity.Tiendas;
import org.springframework.stereotype.Component;

@Component
public class Mapper implements IMapper<TiendasDTO,Tiendas> {
    @Override
    public Tiendas map(TiendasDTO in) {
        Tiendas tiendas = new Tiendas();
        tiendas.setNro_tienda(in.getNro_tienda());
        tiendas.setIp_tienda(in.getIp_tienda());
        tiendas.setTipo(in.getTipo());
        tiendas.setProvincia(in.getProvincia());
        tiendas.setDireccion(in.getDireccion());
        return tiendas;
    }
}
