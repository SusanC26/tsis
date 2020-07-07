package mx.uam.tsis.ejemplobackend.negocios;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
public class AlumnoService {
  
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de
	 * lo contrario 
	 */
	public Alumno create(Alumno nuevoAlumno)
	{
		//regla de negocio no se puede crear mas de un alumno con la misma matricula
	  Optional<Alumno> alumnoOpt= alumnoRepository.findById(nuevoAlumno.getMatricula());
	
	  if(!alumnoOpt.isPresent())
	  {
		 return alumnoRepository.save(nuevoAlumno);
	  }else
	  {
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
	
	
	public Alumno retieve(Integer matricula)
	{
	  
		Optional<Alumno>  alumnofind =alumnoRepository.findById(matricula);
		
		if(alumnofind.isPresent())
		{
			 return alumnofind.get();
		}else
			return null;
	}
	
	
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
