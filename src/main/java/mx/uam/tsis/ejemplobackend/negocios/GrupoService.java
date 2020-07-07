package mx.uam.tsis.ejemplobackend.negocios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;


@Service
@Slf4j
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	public Grupo create(Grupo nuevo) {
		
		// Validar reglas de negocio previas a la creación
		
		return grupoRepository.save(nuevo);
	}
	
	public Iterable <Grupo> retrieveAll() {
		return grupoRepository.findAll();
	}
	
	/**
	 * Metodo que permite agregar un alumno a un grupo
	 * @param groupId id del grupo
	 * @param matricula 
	 * @return true si se agrego correcto, false si no
	 */
	public boolean addStudentToGroup(Integer groupId,Integer matricula)
	{
		log.info("agregando alumno con matricula "+matricula+" al grupo "+groupId);
	  //1.recupera al alumno
	  Alumno alumno=alumnoService.retieve(matricula);	
	  
	  //2.recuperar el grupo
	  Optional <Grupo> grupoOp = grupoRepository.findById(groupId);
	  
	  //3.verificamos si el alumno y grupo existe
	  if(!grupoOp.isPresent() || alumno==null)
	  {
		  log.info("no se encontro grupo o aliumno");
		  return false;
	  }
	  //4.agrega el alumno al grupo
	  Grupo grupo =grupoOp.get();
	  
	  grupo.addAlumno(alumno);
	  
	  //5.persiste el cambio
	  grupoRepository.save(grupo);
	  return true;
	}

}
