package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;
import mx.uam.tsis.ejemplobackend.negocios.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocios.GrupoService;

@ExtendWith(MockitoExtension.class)
public class GrupoServiceTest {
	
	@Mock
	private GrupoRepository grupoRepositoryMock;
	
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	/**
	 * Pruba para dar de alta a un alumno en un grupo con exito
	 */
	@Test
	public void testSuccesfulAddStudentToGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  
	  
	  Alumno alumno = new Alumno();
	  alumno.setCarrera("Computación");
	  alumno.setMatricula(123);
	  alumno.setNombre("susan");
	  
	// Stubbing para el alumnoService
	  when(alumnoServiceMock.retieve(123)).thenReturn(alumno);
	// Stubbing para el grupoRepository
	  when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
	  
	  
	  boolean result= grupoService.addStudentToGroup(1,123);
	  
	  assertEquals(true,result);
	  
	  assertEquals(grupo.getAlumnos().get(0),alumno);
	}
	
	/**
	 * Prueba sin exito para dar de alta un alumno a grupo
	 */
	@Test
	public void testUnsuccesfulAddStudentToGroup (){
		
		  Alumno alumno = new Alumno();
		  alumno.setCarrera("Computación");
		  alumno.setMatricula(123);
		  alumno.setNombre("susan");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.retieve(123)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		
		boolean result = grupoService.addStudentToGroup(1,123);
		
		assertEquals(false,result);
		
		
	}
	
	/**
	 * Prueba para agregar un grupo con exito
	 */
	@Test
	public void testSuccesCreateGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  
	  when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
	  
	  when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
	  
	  grupo=grupoService.create(grupo);
	  
	 assertNotNull(grupo);
	}
	
	/**
	 * prueba sin exito para agregar un grupo
	 */
	@Test
	public void testUnSuccesCreateGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  
	  when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.of(grupo));
	  
	  grupo= grupoService.create(grupo);  
	  assertEquals(null,grupo);
	}
	
	/**
	 * prueba para recuperar un grupo por id
	 */
	@Test
	public void testSuccesRetieveGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  	  
	  when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.of(grupo));
	  grupo = grupoService.retieve(grupo.getId());
	  
	  assertNotNull(grupo);
	}
	
	/**
	 * prueba sin exito para recuperar grupo por id
	 */
	@Test
	public void testUnSuccesRetieveGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  	  
	  when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
	  grupo = grupoService.retieve(grupo.getId());
	  
      assertEquals(null,grupo);
	}
	
	/**
	 * prueba para eliminar un grupo
	 */
	@Test
	public void testSuccesDeleteGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  	  
	  when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.of(grupo));
	  boolean result = grupoService.delete(grupo.getId());
	  
	  assertEquals(true,result);
      
	}
	
	/**
	 * prueba sin exito para eliminar un grupo
	 */
	@Test
	public void testUnSuccesDeleteGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  	  
	  when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
	  boolean result = grupoService.delete(grupo.getId());
	  
	  assertEquals(false,result);
      
	}
	
	/**
	 * prueba para actualizar un grupo
	 */
	@Test
	public void testSuccesUpdateGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  
	 
	  when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
	 
	  Grupo grupo2=new Grupo();
	  grupo2.setId(1);
	  grupo2.setClave("TSIS2");
	
	  when(grupoRepositoryMock.save(grupo2)).thenReturn(grupo2);
	  
	  grupo2=grupoService.updategrupo(grupo.getId(),grupo2);
		 
	  assertNotNull(grupo2);
		

	}
	
	/**
	 * prueba sin exito para actualizar un grupo
	 */
	@Test
	public void testUnSuccesUpdateGroup()
	{
	  Grupo grupo=new Grupo();
	  grupo.setId(1);
	  grupo.setClave("TST2020");
	  
	 
	  when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(null));
	 
	  Grupo grupo2=new Grupo();
	  grupo2.setId(1);
	  grupo2.setClave("TSIS2");
	  
		 grupo2=grupoService.updategrupo(grupo.getId(),grupo2);
		  
		 assertEquals(null,grupo2);

	}

	
	

}
