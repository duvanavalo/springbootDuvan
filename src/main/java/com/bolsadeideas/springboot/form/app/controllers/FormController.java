package com.bolsadeideas.springboot.form.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")                               //lo que se coloque aca solo se guardara por una sesion http 
public class FormController 
{
	@Autowired
	private UsuarioValidador validador;
	
	@GetMapping ("/form")
	public String form(Model model)										//para capturar se usa el RequestParam 
	{		
		Usuario usuario = new Usuario();
		usuario.setNombre("Duvan");
		usuario.setApellido("Avalo");
		usuario.setIdentificador("123.456.789-K");
		model.addAttribute("titulo", "Formulario Usuarios");
		model.addAttribute("usuario", usuario);
		return "form";
	}
																								//ModelAttribute es para cambiar el nombre que se pasa a la vista
	@PostMapping("/form")																		//valid para validar los datos 
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status)     //bindingresult  contiene el resultado de la validacion en caso de error... y su restriccion tiene que estar justo despues del objeto que se vlida 
	{																					//todo lo que son datos no es necesario inyectarlos		
		validador.validate(usuario, result);
		model.addAttribute("titulo", "Resultado form");
		
		if(result.hasErrors())
		{
			//Map<String, String> errores = new HashMap<>();
			//result.getFieldErrors().forEach(err ->{
				//errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));			
			//});
			
			//model.addAttribute("error", errores);
			return "form";
		}		
			
		model.addAttribute("usuario", usuario);
		status.setComplete();
		
		return "resultado";
	}	

}
