package mx.uam.tsis.ejemplobackend.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

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
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlumnoControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@BeforeEach
	public void prepare()
	{
		
	}
	
	@Test
	public void testCreate201()
	{
		  Alumno alumno = new Alumno();
		  alumno.setCarrera("Computación");
		  alumno.setMatricula(123);
		  alumno.setNombre("susan");
		  
		// Creo el encabezado
			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		  
		// Creo la petición con el alumno como body y el encabezado
		   HttpEntity <Alumno> request = new HttpEntity <> (alumno, headers);
		
		   ResponseEntity <Alumno> responseEntity = restTemplate.exchange("/alumnos", HttpMethod.POST, request, Alumno.class);//habla con endpoint
		   
		   log.info("Me regresó:"+responseEntity.getBody());
		   
		// Corroboro que el endpoint me regresa el estatus esperado
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
			
			// Corroboro que en la base de datos se guardó el alumno
			Optional <Alumno> optAlumno = alumnoRepository.findById(123);
			assertEquals(alumno,optAlumno.get());

	}
	
	@Test
	public void testCreate400()
	{
		
	}

}
