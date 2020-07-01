package mx.uam.tsis.ejemplobackend.presentacion;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador web
 * 
 * @author humbertocervantes
 *
 */
@Controller
@Slf4j
public class MainController {
	Logger logger =Logger.getLogger("MainControll");
	
	@GetMapping("/")
	public String index() {
		
		//log.info("Se invocó el método index()");
		logger.log( Level.INFO, "metodo index()");
		
		return "index";
	}

	@RequestMapping("/ejemplo")
	@ResponseBody
	public String ejemplo() {
		
	
		return "esto es un ejemplo";
	}

}
