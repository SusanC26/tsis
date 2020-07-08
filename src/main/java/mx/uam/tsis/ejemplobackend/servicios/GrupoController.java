package mx.uam.tsis.ejemplobackend.servicios;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;
import mx.uam.tsis.ejemplobackend.negocios.GrupoService;

@RestController
@Slf4j
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	
	@ApiOperation(
			value = "Crear grupo",
			notes = "Permite crear un nuevo grupo"
			) // Documentacion del api
	@PostMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Grupo nuevoGrupo) { // Validaciones
				
		log.info("Recibí llamada a create con "+nuevoGrupo); // Logging
		
		Grupo grupo = grupoService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear alumno");
		}
	

	}
	

	@ApiOperation(
			value = "Recupera todos los grupos",
			notes = "Permite recuperar todos los grupos existentes"
			) 
	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Grupo> result = grupoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera un Grupo por id",
	  notes="Recupera un grupo por medio de un id existente para ver su información"
	)
	@GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") Integer id) {
		
		
		
		Grupo grupo= grupoService.retieve(id);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("grupo con id: "+id+" no existe");
		}
		
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Actualiza Grupo",
	  notes="Actualiza los datos del Grupo por medio de su id"
	)
	@PutMapping(path = "grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("id") Integer id,@RequestBody Grupo actualizaGrupo)
	{
 		Grupo grupo= grupoService.updategrupo(id,actualizaGrupo);
 		if(grupo!=null)
 		{
 	      return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
 		}
 		else
 		{
 			return  ResponseEntity.status(HttpStatus.CONFLICT).build();
 		}
	}
	
	/**
	 * 
	 * POST /grupos/{id}/alumnos?matricula=1234
	 * 
	 * PROBAR ESTE!!!
	 * 
	 * @return
	 */
	@PostMapping(path = "/grupos/{id}/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addStudentToGroup(
			@PathVariable("id") Integer id,
			@RequestParam("matricula") Integer matricula) {
		
		boolean result = grupoService.addStudentToGroup(id, matricula);
		
		if(result!=false) {
			return ResponseEntity.status(HttpStatus.CREATED).build(); 
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		}
		
	
	}
	
	  @DeleteMapping(path = "grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseEntity <?> delete(@PathVariable("id") Integer id)
	   {
		   boolean grupoeliminado=grupoService.delete(id);
			
		   if(grupoeliminado ==true)
	         return ResponseEntity.status(HttpStatus.OK).build();
		   else
			   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede eliminar grupo,no existe");
	   }
}