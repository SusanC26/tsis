package mx.uam.tsis.ejemplobackend.servicios;



import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocios.AlumnoService;

/**
 * Controlador para el API rest
 * 
 * @author susan
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	//Logger logger =Logger.getLogger("AlumnoController");
	
	
	@ApiOperation
	(//documentacion del api
	  value = "Crear un nuevo Alumno",
	  notes="Permite crear un nuevo Alumno, tomar en cuenta que cada alumno debe tener una matricula única"
	)	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Alumno nuevoAlumno) { // Validaciones
				
		//log.info("Recibí llamada a create con "+nuevoAlumno); // Logging
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear alumno");
		}
	

	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera todos los Alumnos",
	  notes="Recupera todos los alumnos que se crearon"
    )
    @GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Recupera un Alumno por matricula",
	  notes="Recupera un alumno por medio de una matricula existente para ver su información"
	)
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		
		///log.info("Buscando al alumno con matricula "+matricula);
		//logger.log( Level.INFO, "alumno con matricula"+matricula);
		
		Alumno alumno= alumnoService.retieve(matricula);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("alumno con matricula: "+matricula+" no existe");
		}
		
		
	}
	
	@ApiOperation
	(//documentacion del api
	  value = "Actualiza Alumno",
	  notes="Actualiza los datos del alumno por medio de su matricula"
	)
	@PutMapping(path = "alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("matricula") Integer matricula,@RequestBody Alumno actualizaAlumno)
	{
 		Alumno alumno= alumnoService.updatealumno(matricula, actualizaAlumno);
 		if(alumno!=null)
 		{
 	      return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
 		}
 		else
 		{
 			return  ResponseEntity.status(HttpStatus.CONFLICT).build();
 		}
	}
		
	
	@ApiOperation
	(//documentacion del api
	  value = "Elimina Alumno",
	  notes="Elimina un Alumno por medio de su matricula"
	)	
    @DeleteMapping(path = "alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula)
   {
	   boolean alumnoeliminado=alumnoService.delete(matricula);
		
	   if(alumnoeliminado ==true)
         return ResponseEntity.status(HttpStatus.OK).build();
	   else
		   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede eliminar alumno, la matricula no existe");
   }
	
	
}
