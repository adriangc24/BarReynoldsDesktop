package com.example.barreynoldsapp1;

public class Producto {
	 private int id;
	    private String nombre;
	    private float precio;
	    private String descripcion;
	    private int categoria,cantidad;
	    
		public Producto(int id, String nombre, float precio, String descripcion, int categoria) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.precio = precio;
			this.descripcion = descripcion;
			this.categoria = categoria;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public float getPrecio() {
			return precio;
		}
		public void setPrecio(float precio) {
			this.precio = precio;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public int getCategoria() {
			return categoria;
		}
		public void setCategoria(int categoria) {
			this.categoria = categoria;
		}
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
	    
}
