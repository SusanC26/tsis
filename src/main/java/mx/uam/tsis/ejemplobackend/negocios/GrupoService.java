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
	
	
	/**
	 * Metodo que permite crear nuevo Grupo en el sistema
	 * @param nuevoGrupo
	 * @return si no existe regresa el grupo nuevo, si existe regresa null
	 */
	public Grupo create(Grupo nuevoGrupo) {
		
		log.info("recibi para crear al grupo"+nuevoGrupo);
		Optional<Grupo> grupo = grupoRepository.findById(nuevoGrupo.getId());
		
	   if(!grupo.isPresent())
	   {
		 Grupo gruponuevo= grupoRepository.save(nuevoGrupo);
		  
		  log.info("regresando el grupo creado "+nuevoGrupo);
		  return gruponuevo;
	   }
	   else {
		   log.info("no se creo el grupo, ya existe");
		   return null;
	   }
	}
	
	
	/**
	 * 
	 * @return todos los grupos
	 */
	public Iterable <Grupo> retrieveAll() {
		return grupoRepository.findAll();
	}
	
	/**
	 * Metodo para recuperar un Grupo por id
	 * @param id
	 * @return el grupo si lo encuntra, null si no
	 */
	public Grupo retieve(Integer id)
	{
	   log.info("recibi del grupo el id "+id );
		Optional<Grupo>  grupofind =grupoRepository.findById(id);
		
		if(grupofind.isPresent())
		{
			
			Grupo grupo = grupofind.get();
			log.info("grupo encontrado como "+grupo);
			 return grupo;
		}else
			log.info("no se encontro el id del grupo");
			return null;
	}
	
	/**
	 * Metodo para actualizar datos del grupo
	 * @param id
	 * @param actualizaGrupo
	 * @return grupo con datos actualizados si se encontro, si no null
	 */
	public Grupo updategrupo(Integer id, Grupo actualizaGrupo)
	{
	     log.info("Actualizar el grupo con id "+id);
		 Optional<Grupo>  grupofind =grupoRepository.findById(id);
		if(grupofind.isPresent())
		{
			Grupo grupo = new Grupo();
			
			grupo.setClave(actualizaGrupo.getClave());
			grupo.setAlumnos(actualizaGrupo.getAlumnos());
		    
		    Grupo actualizado=grupoRepository.save(actualizaGrupo);
		    
		    log.info("el grupo se actualizao "+actualizado);
		
		return actualizado;
		}
		else
		{
			log.info("no se actualizo el grupo");
			return null;
		}
	}
	
	/**
	 * Metodo para eliminar grupo por id en el sistema
	 * @param id
	 * @return true si se elimino el grupo correctamente,false si no
	 */
	public boolean delete(Integer id)
	{ 
		log.info("recibi el id "+id+" para eliminar");
		 Optional<Grupo>  grupofind =grupoRepository.findById(id);
		
		 if(grupofind.isPresent())
		 {
		  grupoRepository.deleteById(id);
		  log.info("se elimino el grupo con id "+id);
		  return true;
		 }
		 else
			 log.info("no se elimino el grupo, no se encontro");
			 return false;
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
		  log.info("no se encontro grupo o alumno");
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
