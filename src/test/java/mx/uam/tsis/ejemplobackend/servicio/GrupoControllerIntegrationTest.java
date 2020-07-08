package mx.uam.tsis.ejemplobackend.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@BeforeEach
	public void prepare()
	{

		  Grupo grupo=new Grupo();
		  grupo.setId(1);
		  grupo.setClave("TST2020");
		  grupoRepository.save(grupo);
		  
		  Alumno alumno = new Alumno();
		  alumno.setCarrera("Computación");
		  alumno.setMatricula(123);
		  alumno.setNombre("susan");
		alumnoRepository.save(alumno);
	}
	
	@Test
	public void testCreateaddAStudentToGroup201()
	{
		  
		// Creo el encabezado
			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		  
		// Creo la petición con el alumno como body y el encabezado
		   HttpEntity <Grupo> request = new HttpEntity <> (headers);
		
		   ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/1/alumnos?matricula=123", HttpMethod.POST, request, Grupo.class);//habla con endpoint
		   
		   log.info("Me regresó:"+responseEntity.getBody());
		   
		// Corroboro que el endpoint me regresa el estatus esperado
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
			
			
	}
	
	@Test
	public void testCreateaddAStudentToGroup400()
	{
		  
		// Creo el encabezado
			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		  
		// Creo la petición con el alumno como body y el encabezado
		   HttpEntity <Grupo> request = new HttpEntity <> (headers);
		
		   ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/12/alumnos?matricula=123", HttpMethod.POST, request, Grupo.class);//habla con endpoint
		   
		   log.info("Me regresó:"+responseEntity.getBody());
		   
		// Corroboro que el endpoint me regresa el estatus esperado
			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
			
			
	}

}
