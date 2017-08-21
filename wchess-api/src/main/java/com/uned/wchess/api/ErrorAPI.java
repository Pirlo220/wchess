package com.uned.wchess.api;

import java.util.HashMap;
import java.util.Map;

public class ErrorAPI {
	private String codigo;
	private String mensaje;
	private Map<String, String> datos;

	public ErrorAPI() {
		this.datos = new HashMap<String, String>();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Map<String, String> getDatos() {
		return datos;
	}

	public void setDatos(Map<String, String> datos) {
		this.datos = datos;
	}

	public void put(String key, String value) {
		datos.put(key, value);
	}

}
