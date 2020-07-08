package mx.uam.tsis.ejemplobackend.negocios;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
@Slf4j
public class AlumnoService {
  
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * Método que permite crear nuevos alumnos
	 * 
	 * @param nuevoAlumno el alumno que se desea crear en el sistema
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		// Regla de negocio: No se puede crear más de un alumno con la misma matricula
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		
		if(!alumnoOpt.isPresent()) {

			log.info("Voy a guardar a alumno "+nuevoAlumno);
			
			Alumno returnAlumno =  alumnoRepository.save(nuevoAlumno);
			
			log.info("Voy a regresar a alumno "+returnAlumno);
			
			return returnAlumno;
			
		} else {
			
			return null;
			
		}
		
	}
	
	
	/**
	 * 
	 * @return todos los alumnos
	 */
	public Iterable<Alumno> retrieveAll()
	{
		return alumnoRepository.findAll();
	}
	
	/**
	 * Metodo para recuperar un alumno por matricula
	 * @param matricula
	 * @return el alumno si existe, null si no existe
	 */
	public Alumno retieve(Integer matricula)
	{
	  
		Optional<Alumno>  alumnofind =alumnoRepository.findById(matricula);
		
		if(alumnofind.isPresent())
		{
			 return alumnofind.get();
		}else
			return null;
	}
	
	/**
	 * Metodo para actulizar datos del alumno
	 * @param matricula
	 * @param actualizaAlumno
	 * @return el alumno con datos actualizado si existe, si no null
	 */
	public Alumno updatealumno(Integer matricula, Alumno actualizaAlumno)
	{
		 Optional<Alumno>  alumnofind =alumnoRepository.findById(matricula);
		if(alumnofind.isPresent())
		{
			Alumno alum = new Alumno();
			
			alum.setNombre(actualizaAlumno.getNombre());
			alum.setCarrera(actualizaAlumno.getCarrera());
		    
		    Alumno actualizadoAlumno= alumnoRepository.save(actualizaAlumno);
		
		
		return actualizadoAlumno;
		}
		else
		{
			return null;
		}
	}
	
	
	/**
	 * Metodo para eliminar un alumno del sistema por medio de matricula
	 * @param matricula
	 * @return true si se elimino correctamente, false si no
	 */
	public boolean delete(Integer matricula)
	{ 
		 Optional<Alumno>  alumnofind =alumnoRepository.findById(matricula);
		
		 if(alumnofind.isPresent())
		 {
		  alumnoRepository.deleteById(matricula);
		  return true;
		 }
		 else
			 return false;
	}
}
