// Función para formatear el texto del nombre del servicio
function formatServiceName(name) {
    // Convertir a minúsculas y reemplazar espacios con guiones
    return name.toLowerCase().replace(/\s+/g, '-').normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Obtener todos los elementos de imagen y actualizar sus rutas
document.querySelectorAll('[id^="img-"]').forEach(function(img) {
    var serviceName = img.getAttribute('id').substring(4); // Obtener el nombre del servicio
    var formattedServiceName = formatServiceName(serviceName); // Formatear el nombre del servicio
    img.setAttribute('src', '/static/images/servicios/' + formattedServiceName + '.png'); // Actualizar la ruta de la imagen
});
