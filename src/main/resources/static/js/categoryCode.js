const cartButton = document.querySelector('.cart');
const cartContent = document.querySelector('.cart-content');
const closeButton = document.querySelector('#close-button');
let isCartOpen = false; // rastrear el estado del contenido del carrito

// alternar la visibilidad del contenido del carrito
function toggleCartContent() {
    if (!isCartOpen) { // Si el carrito está cerrado, abrirlo
        
        cartContent.classList.add('show');
        cartContent.classList.remove('hide');
    

        isCartOpen = true;
    } else { // si el carrito esta abierto, cerrarlo
        cartContent.classList.add('hide');
        cartContent.classList.remove('show');
        isCartOpen = false;
    }
}

//controlar el evento click del boton del carrito
cartButton.addEventListener('click', toggleCartContent);

// controlar el evento click del boton de cerrar
closeButton.addEventListener('click', function() {
    cartContent.classList.remove('show');
    cartContent.classList.add('hide');
    isCartOpen = false;
});


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


document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.getElementById('searchInput');
    const services = document.querySelectorAll('.cajas'); // obtener todos los elementos de servicio

    searchInput.addEventListener('input', function() {
        const searchTerm = searchInput.value.trim().toLowerCase(); // obtener el término de búsqueda y convertirlo a minúsculas
        
        // iterar sobre cada elemento de servicio y mostrar u ocultar según el término de búsqueda
        services.forEach(function(service) {
            const serviceName = service.querySelector('.service-type-text').textContent.toLowerCase();
            if (serviceName.includes(searchTerm)) {
                service.style.display = 'block'; // mostrar el servicio si coincide con el término de búsqueda
            } else {
                service.style.display = 'none'; // ocultar el servicio si no coincide con el término de búsqueda
            }
        });
    });
});


