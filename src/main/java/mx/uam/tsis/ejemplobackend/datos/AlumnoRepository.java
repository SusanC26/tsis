package mx.uam.tsis.ejemplobackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
/**
 * 
 * @author susan~
 *se encarga de almacenar y recuperar datos 
 */

public interface AlumnoRepository extends CrudRepository<Alumno,Integer>{//identidad y llave primeria
	
   
}
