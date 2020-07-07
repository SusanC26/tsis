package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;
//grupo es la identidad, integer es el tipo de la llave primaria
public interface GrupoRepository extends CrudRepository <Grupo,Integer>{

}
