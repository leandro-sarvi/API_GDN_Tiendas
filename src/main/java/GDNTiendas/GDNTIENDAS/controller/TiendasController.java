package GDNTiendas.GDNTIENDAS.controller;

import GDNTiendas.GDNTIENDAS.dto.NroIpTienda;
import GDNTiendas.GDNTIENDAS.dto.TiendasDTO;
import GDNTiendas.GDNTIENDAS.persistence.entity.Tiendas;
import GDNTiendas.GDNTIENDAS.service.TiendasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TiendasController {
    private final TiendasService tiendasService;
    public TiendasController(TiendasService tiendasService){
        this.tiendasService = tiendasService;
    }
    @GetMapping("/tiendabyid/{id}")
    public ResponseEntity<Optional<Tiendas>> getFindById(@PathVariable("id") Long id){
        Optional<Tiendas> tienda = this.tiendasService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(tienda);
    }
    @GetMapping("/tiendas")
    public ResponseEntity<List<Tiendas>> getFindAll(){
        List<Tiendas> tiendasList = this.tiendasService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tiendasList);
    }
    @GetMapping("/listnrotiendaip")
    public ResponseEntity<List<NroIpTienda>> listStoreNumberIp(){
        List<NroIpTienda> nroIpTiendaList = this.tiendasService.listStoreNumberIp();
        return ResponseEntity.status(HttpStatus.OK).body(nroIpTiendaList);
    }
    @GetMapping("/tienda/{nro_tienda}")
    public ResponseEntity<Tiendas> getFindByNroTienda(@PathVariable("nro_tienda") int nro_tienda){
        Tiendas tienda = this.tiendasService.findByNroTienda(nro_tienda);
        return  ResponseEntity.status(HttpStatus.OK).body(tienda);
    }
    @PostMapping("/tienda")
    public ResponseEntity<Tiendas> createTienda(@RequestBody TiendasDTO tiendasDTO){
        Tiendas tiendas = this.tiendasService.createTienda(tiendasDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(tiendas);
    }
    @DeleteMapping("/tienda/{id}")
    public ResponseEntity<Void> deleteTiendaById(@PathVariable("id") Long id){
        this.tiendasService.deleteTiendabyId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/deletetienda/{nro_tienda}")
    public ResponseEntity<Void> deleteByNroTienda(@PathVariable("nro_tienda") int nro_tienda){
        this.tiendasService.deleteByNroTienda(nro_tienda);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/updatetienda/{id}")
    public ResponseEntity<Tiendas> updateTiendaById(@RequestBody TiendasDTO tiendasDTO, @PathVariable Long id){
        Tiendas tienda = this.tiendasService.updateTiendaById(tiendasDTO,id);
        return ResponseEntity.status(HttpStatus.OK).body(tienda);
    }
}
