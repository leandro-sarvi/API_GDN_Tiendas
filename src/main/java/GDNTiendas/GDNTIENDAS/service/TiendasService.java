package GDNTiendas.GDNTIENDAS.service;

import GDNTiendas.GDNTIENDAS.dto.NroIpTienda;
import GDNTiendas.GDNTIENDAS.dto.TiendasDTO;
import GDNTiendas.GDNTIENDAS.exceptions.TiendaBadRequestException;
import GDNTiendas.GDNTIENDAS.exceptions.ResourceNotFoundException;
import GDNTiendas.GDNTIENDAS.mapper.Impl.Mapper;
import GDNTiendas.GDNTIENDAS.mapper.Impl.MapperNroIpTiendaImpl;
import GDNTiendas.GDNTIENDAS.persistence.entity.Tiendas;
import GDNTiendas.GDNTIENDAS.persistence.repository.TiendasRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiendasService {
    private final TiendasRepository tiendasRepository;
    private Mapper mapper;
    private MapperNroIpTiendaImpl mapperNroIpTiendas;
    public TiendasService(TiendasRepository tiendasRepository, Mapper mapper, MapperNroIpTiendaImpl mapperNroIpTiendas){
        this.tiendasRepository = tiendasRepository;
        this.mapper = mapper;
        this.mapperNroIpTiendas = mapperNroIpTiendas;
    }
    public Optional<Tiendas> findById(Long id){
        Optional<Tiendas> tienda = this.tiendasRepository.findById(id);
        if(tienda.isEmpty()){
            throw  new ResourceNotFoundException("Tienda not found");
        }
        return tienda;
    }
    public List<Tiendas> findAll(){
        List<Tiendas> tiendasList = this.tiendasRepository.findAll();
        return tiendasList;
    }
    public Tiendas findByNroTienda(int nro_tienda){
        Tiendas tienda = this.tiendasRepository.findByNroTienda(nro_tienda);
        if(tienda==null){
            throw new ResourceNotFoundException("Tienda not found");
        }
        return tienda;
    }
    public Tiendas createTienda(TiendasDTO tiendasDTO){
        Tiendas tienda = this.tiendasRepository.save(this.mapper.map(tiendasDTO));
        return tienda;
    }
    public void deleteTiendabyId(Long id){
        Optional<Tiendas> tiendas = this.tiendasRepository.findById(id);
        if(tiendas.isEmpty()){
            throw new ResourceNotFoundException("Tienda not found");
        }
        this.tiendasRepository.deleteById(id);
    }
    @Transactional
    public void deleteByNroTienda(int nro_tienda){
        Tiendas tienda = this.tiendasRepository.findByNroTienda(nro_tienda);
        if(tienda==null){
            throw new ResourceNotFoundException("Tienda not found");
        }
        this.tiendasRepository.deleteByNroTienda(nro_tienda);
    }
    public Tiendas updateTiendaById(TiendasDTO tiendasDTO, Long id){
        Optional<Tiendas> tienda = this.tiendasRepository.findById(id);
        if(tienda.isEmpty()){
            throw new ResourceNotFoundException("Tienda not found");
        }
        if (tiendasDTO == null || tiendasDTO.getIp_tienda().isBlank() || tiendasDTO.getNro_tienda() == 0 ||
                tiendasDTO.getTipo().isBlank() || tiendasDTO.getProvincia().isBlank() || tiendasDTO.getDireccion().isBlank()) {
            throw new TiendaBadRequestException("La solicitud contiene campos vacíos o nulos");
        }
        Tiendas tiendaExistente = tienda.get();
        tiendaExistente.setNro_tienda(tiendasDTO.getNro_tienda());
        tiendaExistente.setIp_tienda(tiendasDTO.getIp_tienda());
        tiendaExistente.setTipo(tiendasDTO.getTipo());
        tiendaExistente.setProvincia(tiendasDTO.getProvincia());
        tiendaExistente.setDireccion(tiendasDTO.getDireccion());
        return this.tiendasRepository.save(tiendaExistente);
    }
    public List<NroIpTienda> listStoreNumberIp(){
        List<Tiendas> tiendasList = this.tiendasRepository.findAll();
        List<NroIpTienda> nroIpTiendaList = this.mapperNroIpTiendas.mapNroIpTienda(tiendasList);
        return nroIpTiendaList;
    }

}
